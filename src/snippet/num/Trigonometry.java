/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2026.4.30
 */
package snippet.num;

/**
 * A snippet for numerical operations on trigonometric functions.
 */
public final class Trigonometry {

    private Trigonometry() {
        // Cannot be instantiated
        throw new AssertionError();
    }

    /**
     * Calculate sin(pi * x).
     * 
     * @param x x
     * @return sin(pi * x)
     */
    public static double sinpi(double x) {

        if (!Double.isFinite(x)) {
            return Double.NaN;
        }

        // x to -2 < x < 2
        x %= 2d;

        // -3 <= int(2x) <= 3
        switch ((int) (2d * x)) {
            case -3:
                return Math.sin(Math.PI * (2d + x));
            case -2:
            case -1:
                return -Math.sin(Math.PI * (1d + x));
            case 0:
                return Math.sin(Math.PI * x);
            case 1:
            case 2:
                return -Math.sin(Math.PI * (x - 1d));
            case 3:
                return Math.sin(Math.PI * (x - 2d));
            default:
                throw new AssertionError("unreachable");
        }
    }

    /**
     * Calculate cos(pi * x).
     * 
     * @param x x
     * @return cos(pi * x)
     */
    public static double cospi(double x) {

        if (!Double.isFinite(x)) {
            return Double.NaN;
        }

        // use cos(t) = cos(-t)
        // x to 0 <= x < 2
        x %= 2d;
        x = Math.abs(x);

        // 0 <= int(x) <= 1
        switch ((int) x) {
            case 0:
                return -Math.sin(Math.PI * (x - 0.5d));
            case 1:
                return Math.sin(Math.PI * (x - 1.5d));
            default:
                throw new AssertionError("unreachable");
        }
    }

    /**
     * Calculate tan(pi * x).
     * 
     * @param x x
     * @return tan(pi * x)
     */
    public static double tanpi(double x) {

        if (!Double.isFinite(x)) {
            return Double.NaN;
        }

        // -1 < x < 1
        x %= 1d;
        // -7 <= int(8x) <= 7
        switch ((int) (8d * x)) {
            case -7:
                return Math.tan(Math.PI * (x + 1d));
            case -6:
            case -5: {
                double tanpz = Math.tan(Math.PI * (x + 0.75d));
                return (1d + tanpz) / (1d - tanpz);
            }
            case -4:
            case -3:
                return -1d / Math.tan(Math.PI * (x + 0.5d));
            case -2:
            case -1: {
                double tanpz = Math.tan(Math.PI * (x + 0.25d));
                return (-1d + tanpz) / (1d + tanpz);
            }
            case 0:
                return Math.tan(Math.PI * x);
            case 1:
            case 2: {
                double tanpz = Math.tan(Math.PI * (x - 0.25d));
                return (1d + tanpz) / (1d - tanpz);
            }
            case 3:
            case 4:
                return -1d / Math.tan(Math.PI * (x - 0.5d));
            case 5:
            case 6: {
                double tanpz = Math.tan(Math.PI * (x - 0.75d));
                return (-1d + tanpz) / (1d + tanpz);
            }
            case 7:
                return Math.tan(Math.PI * (x - 1d));
            default:
                throw new AssertionError("unreachable");
        }
    }

    /**
     * Calculate asin(x) / pi.
     * Return NaN if |x| > 1.
     * A range of returned value y is -1/2 <= y <= 1/2.
     * 
     * @param x x
     * @return asin(x) / pi
     */
    public static double asinpi(double x) {
        if (!(Math.abs(x) <= 1d)) {
            return Double.NaN;
        }

        // -2 <= 2x <= 2
        switch ((int) (2d * x)) {
            case -2:
            case -1:
                return -0.5d + Math.asin(Math.sqrt(1 - x * x)) / Math.PI;
            case 0:
                return Math.asin(x) / Math.PI;
            case 1:
            case 2:
                return 0.5d - Math.asin(Math.sqrt(1 - x * x)) / Math.PI;
            default:
                throw new AssertionError("unreachable");
        }
    }

    /**
     * Calculate acos(x) / pi.
     * Return NaN if |x| > 1.
     * A range of returned value y is 0 <= y <= 1.
     * 
     * @param x x
     * @return acos(x) / pi
     */
    public static double acospi(double x) {
        if (!(Math.abs(x) <= 1d)) {
            return Double.NaN;
        }

        // -2 <= 2x <= 2
        switch ((int) (2d * x)) {
            case -2:
            case -1:
                return 1d - Math.asin(Math.sqrt(1 - x * x)) / Math.PI;
            case 0:
                return 0.5d - Math.asin(x) / Math.PI;
            case 1:
            case 2:
                return Math.asin(Math.sqrt(1 - x * x)) / Math.PI;
            default:
                throw new AssertionError("unreachable");
        }
    }

    /**
     * Calculate atan(x) / pi.
     * A range of returned value y is -1/2 <= y <= 1/2.
     * 
     * @param x x
     * @return atan(x) / pi
     */
    public static double atanpi(double x) {
        if (Math.abs(x) < 1.5d) {
            // -3 < 2x < 3
            switch ((int) (2d * x)) {
                case -2:
                case -1:
                    return -0.25d + Math.atan((1 + x) / (1 - x)) / Math.PI;
                case 0:
                    return Math.atan(x) / Math.PI;
                case 1:
                case 2:
                    return 0.25d + Math.atan((x - 1) / (x + 1)) / Math.PI;
                default:
                    throw new AssertionError("unreachable");
            }
        }

        if (x < 0d) {
            return -0.5d - Math.atan(1d / x) / Math.PI;
        }
        return 0.5d - Math.atan(1d / x) / Math.PI;
    }

    /**
     * Calculate sinc(x) = sin(x) / x.
     * 
     * @param x x
     * @return sinc(x) = sin(x) / x
     */
    public static double sinc(double x) {
        if (Math.abs(x) <= 1E-200) {
            return 1d;
        }
        double sinx = Math.sin(x);
        return Double.isFinite(sinx)
                ? sinx / x
                : Double.isNaN(x) ? Double.NaN : 0d;
    }

    /**
     * Calculate normalized sinc: sinc(pi * x) = sin(pi * x) / (pi * x).
     * 
     * @param x x
     * @return sinc(pi * x) = sin(pi * x) / (pi * x)
     */
    public static double sincpi(double x) {
        if (Math.abs(x) <= 1E-200) {
            return 1d;
        }
        double sinpix = sinpi(x);
        return Double.isFinite(sinpix)
                ? sinpix / (Math.PI * x)
                : Double.isNaN(x) ? Double.NaN : 0d;
    }

    /**
     * Calculate cos(x) - 1.
     * 
     * @param x x
     * @return cos(x) - 1
     */
    public static double cosm1(double x) {
        double absX = Math.abs(x);
        if (absX < (Math.PI * 0.5)) {
            double sinAbsX = Math.sin(absX);
            double sinx2 = sinAbsX * sinAbsX;
            return -sinx2 / (1d + Math.sqrt(1 - sinx2));
        }
        return Math.cos(x) - 1d;
    }

    /**
     * Calculate cos(pi * x) - 1.
     * 
     * @param x x
     * @return cos(x) - 1
     */
    public static double cosm1pi(double x) {
        double absX = Math.abs(x);
        if (absX < 0.5) {
            double sinpiAbsX = sinpi(absX);
            double sinpix2 = sinpiAbsX * sinpiAbsX;
            return -sinpix2 / (1d + Math.sqrt(1 - sinpix2));
        }
        return cospi(x) - 1d;
    }
}
