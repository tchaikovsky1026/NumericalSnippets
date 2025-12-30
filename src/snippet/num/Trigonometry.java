/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2025.12.30
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

        // -1 < x < 1 にマッピング
        x %= 1d;

        // switch-caseは-3から3まで
        switch ((int) (4d * x)) {
            case -3:
                return Math.tan(Math.PI * (x + 1d));
            case -2:
            case -1:
                return -1d / Math.tan(Math.PI * (x + 0.5d));
            case 0:
                return Math.tan(Math.PI * x);
            case 1:
            case 2:
                return -1d / Math.tan(Math.PI * (x - 0.5d));
            case 3:
                return Math.tan(Math.PI * (x - 1d));
            default:
                throw new AssertionError("unreachable");
        }
    }
}
