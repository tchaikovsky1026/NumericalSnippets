/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2026.1.2
 */
package snippet.num;

/**
 * べき乗, 指数対数の計算に関わる数値計算スニペット.
 */
public final class Exponentiation {

    private Exponentiation() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * log-summation-exp:
     * 与えられた <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>, {@literal ...} について,
     * log<sub>e</sub>[e<sup><i>x</i><sub>1</sub></sup> +
     * e<sup><i>x</i><sub>2</sub></sup> +
     * &middot;&middot;&middot; ]
     * を計算する.
     * 
     * <p>
     * <i>x</i> のサイズが0のときは和は0と判断し, -&infin;を返す.
     * </p>
     *
     * @param x <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>, ...
     * @return log<sub>e</sub>[e<sup><i>x</i><sub>1</sub></sup> +
     *             e<sup><i>x</i><sub>2</sub></sup> +
     *             &middot;&middot;&middot; ]
     * @throws NullPointerException 引数にnullが含まれる場合
     */
    public static double logSumExp(double... x) {

        if (x.length == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        /*
         * 相対的大きさでexp(-46)未満は0とする.
         */
        final double LOG_SUM_EXP_THRESHOLD = -46;

        /**
         * exp( LOG_SUM_EXP_THRESHOLD )
         */
        final double SUM_EXP_THRESHOLD = 1.0530617357553812378763324449428E-20;

        /*
         * 1周のループで終わらせられるようにする.
         * 逐次的に改善するように修正する (maxXの逐次改善)
         */

        double maxX = x[0];
        int len = x.length;
        int indMax = 0;
        for (int j = 1; j < len; j++) {
            double x_j = x[j];
            if (x_j > maxX) {
                maxX = x_j;
                indMax = j;
            }
        }
        if (!Double.isFinite(maxX)) {
            return maxX;
        }

        /*
         * log(exp(max) + exp(min1) + exp(min2) + ...)
         * = max + log(1 + exp(min1 - max) + exp(min2 - max) + ...)
         * 
         * ただし, |max| >= 1ならば,
         * exp(min1 - max) + exp(min2 - max) + ... < exp(-46)
         * の場合は log(1 + exp(min1 - max) + exp(min2 - max) + ...) は無視できる.
         * 
         * exp(min1 - max) + exp(min2 - max) + ... < exp(-46)
         * でない場合でも,
         * exp(min[] - max) < exp(-46)
         * ならばその項は無視できる.
         */
        if (Math.abs(maxX) <= 1d) {
            double sum = 0;
            for (int j = 0; j < indMax; j++) {
                sum += Math.exp(x[j] - maxX);
            }
            for (int j = indMax + 1; j < len; j++) {
                sum += Math.exp(x[j] - maxX);
            }
            return maxX + Math.log1p(sum);
        }

        double sum = 0;
        for (int j = 0; j < indMax; j++) {
            double xjMinusMax = x[j] - maxX;
            double e1 = xjMinusMax < LOG_SUM_EXP_THRESHOLD ? 0 : Math.exp(xjMinusMax);
            sum += e1;
        }
        for (int j = indMax + 1; j < len; j++) {
            double xjMinusMax = x[j] - maxX;
            double e1 = xjMinusMax < LOG_SUM_EXP_THRESHOLD ? 0 : Math.exp(xjMinusMax);
            sum += e1;
        }
        return sum < SUM_EXP_THRESHOLD
                ? maxX
                : maxX + Math.log1p(sum); //NaNは最悪この行に到達
    }
}
