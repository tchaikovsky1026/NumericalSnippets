/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2026.1.9
 */
package snippet.num;

/**
 * 三角関数の計算に関わる数値計算スニペット.
 */
public final class Trigonometry {

    private Trigonometry() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * sin(pi * x) の計算.
     * 
     * @param x x
     * @return sin(pi * x)
     */
    public static double sinpi(double x) {

        if (!Double.isFinite(x)) {
            return Double.NaN;
        }

        // -2 < x < 2 となる
        x %= 2d;

        // switch-caseは-3から3まで
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
     * cos(pi * x) の計算.
     * 
     * @param x x
     * @return cos(pi * x)
     */
    public static double cospi(double x) {

        if (!Double.isFinite(x)) {
            return Double.NaN;
        }

        // cos(t) = cos(-t) を利用
        // 0 <= x < 2 となる
        x %= 2d;
        x = Math.abs(x);

        // switch-caseは0から1まで
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
     * tan(pi * x) の計算.
     * 
     * @param x x
     * @return tan(pi * x)
     */
    public static double tanpi(double x) {

        if (!Double.isFinite(x)) {
            return Double.NaN;
        }

        x %= 1d;
        // -8 < 8x < 8
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
     * asin(x) / pi の計算.
     * |x| > 1 ならば NaN.
     * 値域は -1/2 以上 1/2 以下.
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
     * acos(x) / pi の計算.
     * |x| > 1 ならば NaN.
     * 値域は 0 以上 1 以下.
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
     * atan(x) / pi の計算.
     * 値域は -1/2 以上 1/2 以下.
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
     * sinc関数の計算: sin(x) / x.
     * 
     * @param x x
     * @return sin(x) / x
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
     * 正規化されたsinc関数の計算: sin(pi * x) / (pi * x).
     * 
     * @param x x
     * @return sin(pi * x) / (pi * x)
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
     * cos(x) - 1 の計算.
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
     * cos(pi * x) - 1 の計算.
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
