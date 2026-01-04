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
     * log_e [e^{x_1} + e^{x_2} + ... ]
     * の計算.
     * 
     * x.length = 0 のとき -inf.
     * 
     * @param x x_1, x_2, ...
     * @return log_e [e^{x_1} + e^{x_2} + ... ]
     * @throws NullPointerException null
     */
    public static double logSumExp(double... x) {

        double exponent = Double.NEGATIVE_INFINITY;
        double coeff = 1d;
        for (double v : x) {
            if (v == Double.NEGATIVE_INFINITY) {
                continue;
            }
            if (v == Double.POSITIVE_INFINITY) {
                exponent = Double.POSITIVE_INFINITY;
                continue;
            }

            if (v > exponent) {
                coeff = coeff * Math.exp(exponent - v) + 1d;
                exponent = v;
            } else {
                coeff += Math.exp(v - exponent);
            }
        }

        return exponent + Math.log(coeff);
    }
}
