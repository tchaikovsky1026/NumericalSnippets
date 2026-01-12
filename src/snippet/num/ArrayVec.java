/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2026.1.12
 */
package snippet.num;

/**
 * 配列ベクトルに関する数値計算スニペット.
 */
public final class ArrayVec {

    private ArrayVec() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * max-norm (inf-norm):
     * max(|x_1|, |x_2|, ...)
     * の計算.
     * 
     * @param x x_1, x_2, ...
     * @return max(|x_1|, |x_2|, ...)
     * @throws NullPointerException null
     */
    public static double normMax(double... x) {
        double maxValue = 0d;
        for (double v : x) {
            maxValue = Math.max(maxValue, Math.abs(v));
        }
        return maxValue;
    }

    /**
     * 1-norm:
     * |x_1| + |x_2| + ...
     * の計算.
     * 
     * @param x x_1, x_2, ...
     * @return |x_1| + |x_2| + ...
     * @throws NullPointerException null
     */
    public static double norm1(double... x) {
        double sum = 0d;
        for (double v : x) {
            sum += Math.abs(v);
        }
        return sum;
    }

    /**
     * 2-norm:
     * sqrt(|x_1|^2 + |x_2|^2 + ... )
     * の計算.
     * 
     * @param x x_1, x_2, ...
     * @return sqrt(|x_1|^2 + |x_2|^2 + ... )
     * @throws NullPointerException null
     */
    public static double norm2(double... x) {
        double sum = 0d;
        double scale = Double.MIN_NORMAL; // 2^(-1022)
        for (double v : x) {
            double absV = Math.abs(v);

            if (!Double.isFinite(absV)) {
                return absV;
            }

            // equals Math.max(Double.MIN_NORMAL, Math.scalb(1.0, Math.getExponent(absV)))
            double candidateScale = Math.max(
                    Double.MIN_NORMAL,
                    Double.longBitsToDouble(Double.doubleToRawLongBits(absV) & 0xFFF0_0000_0000_0000L));

            /*
             * scale > candidateScale
             * -> coeff = 1, maintaining scale
             * 
             * scale < candidateScale
             * -> coeff = scale / candidateScale, scale = candidateScales
             */
            double coeff = Math.min(1.0, scale / candidateScale);
            scale = Math.max(scale, candidateScale);

            double scaledAbsV = absV / scale;
            sum = sum * coeff * coeff + scaledAbsV * scaledAbsV;
        }
        return Math.sqrt(sum) * scale;
    }
}
