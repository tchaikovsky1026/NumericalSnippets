/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2026.1.4
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

    /**
     * log-multiply-abs:
     * log_e [|x_1||x_2| ... ]
     * の計算.
     * 
     * x.length = 0 のとき 0.
     *
     * @param x x_1, x_2, ...
     * @return log_e [|x_1||x_2| ... ]
     * @throws NullPointerException null
     */
    public static double logMultiplyAbs(double... x) {

        final double MIN_ABS_THRESHOLD = 1E-100;
        final double MAX_ABS_THRESHOLD = 1E+100;

        double logMultiplyAbs = 0;
        double leftover = 1;
        for (int j = 0, len = x.length; j < len; j++) {
            double v0 = Math.abs(x[j]);
            if (v0 < MIN_ABS_THRESHOLD || v0 > MAX_ABS_THRESHOLD) {
                logMultiplyAbs += Math.log(v0);
                continue;
            }
            leftover *= v0;
            if (leftover > MAX_ABS_THRESHOLD || leftover < MIN_ABS_THRESHOLD) {
                logMultiplyAbs += Math.log(leftover);
                leftover = 1d;
            }
        }

        if (leftover != 1d) {
            logMultiplyAbs += Math.log(leftover);
        }
        return logMultiplyAbs;
    }
}
