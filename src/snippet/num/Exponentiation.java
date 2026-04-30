/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2026.4.30
 */
package snippet.num;

/**
 * A snippet for numerical operations on powers, exponentials, and logarithms.
 */
public final class Exponentiation {

    private Exponentiation() {
        // Cannot be instantiated
        throw new AssertionError();
    }

    /**
     * Calculate log-summation-exp:
     * log_e [e^{x_1} + e^{x_2} + ... ].
     * 
     * Return -inf if x.length = 0.
     * 
     * @param x x_1, x_2, ...
     * @return log_e [e^{x_1} + e^{x_2} + ... ]
     * @throws NullPointerException arg is null
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
     * Calculate log-multiply-abs:
     * log_e [|x_1||x_2| ... ].
     * 
     * Return 0 if x.length = 0.
     *
     * @param x x_1, x_2, ...
     * @return log_e [|x_1||x_2| ... ]
     * @throws NullPointerException arg is null
     */
    public static double logMultiplyAbs(double... x) {

        final double MIN_ABS_THRESHOLD = 1E-150;
        final double MAX_ABS_THRESHOLD = 1E+150;

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

        return logMultiplyAbs + Math.log(leftover);
    }

    /**
     * Calculate x^n (the nth power of x).
     * Return 1 if n = 0.
     * 
     * @param x x
     * @param n n
     * @return x^n
     */
    public static double pow(double x, int n) {

        // n to positive.
        // If n = -2^{31}, -n is interpreted as 2^{31}.
        // Overflow of 1/x is acceptable.
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        // By analyzing bit-pattern of n, x^n is expressed as the product of x^(2^k).
        // It works correctly when -n = 2^{31}.
        int np = n;
        double xp = x;
        double value = 1d;
        while (np != 0) {
            if ((np & 1) == 1) {
                value *= xp;
            }
            xp = xp * xp;

            // To handle a case of -n = 2^{31},
            // unsigned bit shift is used.
            np >>>= 1;
        }
        return value;
    }
}
