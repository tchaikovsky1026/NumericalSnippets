/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

package snippet.num;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static snippet.num.ArrayNum.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link ArrayNum} test.
 */
@RunWith(Enclosed.class)
final class ArrayNumTest {

    private static final double relativeError = 1E-12;

    @RunWith(Enclosed.class)
    public static class normMax_test {

        @RunWith(Theories.class)
        public static class normMax_size_and_scale_variation {

            @DataPoints
            public static double[] scales = {
                    Double.MIN_NORMAL,
                    1E-100, 1E-20,
                    1d,
                    1E20, 1E100,
                    Double.MAX_VALUE / 1024
            };

            @DataPoints
            public static int[] sizes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            @Theory
            public void test_parametric(int size, double scale) {

                final int iteration = 20;
                for (int c = 0; c < iteration; c++) {
                    double[] arr = new double[size];
                    for (int i = 0; i < size; i++) {
                        arr[i] = scale * (ThreadLocalRandom.current().nextDouble() * 2d - 1d);
                    }

                    compareAndAssert(normMaxRef(arr), normMax(arr));
                }
            }
        }

        private static double normMaxRef(double... x) {
            double max = 0d;
            for (double v : x) {
                double absV = Math.abs(v);
                max = Math.max(max, absV);
            }
            return max;
        }
    }

    @RunWith(Enclosed.class)
    public static class norm1_test {

        @RunWith(Theories.class)
        public static class norm1_size_and_scale_variation {

            @DataPoints
            public static double[] scales = {
                    Double.MIN_NORMAL,
                    1E-100, 1E-20,
                    1d,
                    1E20, 1E100,
                    Double.MAX_VALUE / 1024
            };

            @DataPoints
            public static int[] sizes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            @Theory
            public void test_parametric(int size, double scale) {

                final int iteration = 20;
                for (int c = 0; c < iteration; c++) {
                    double[] arr = new double[size];
                    for (int i = 0; i < size; i++) {
                        arr[i] = scale * (ThreadLocalRandom.current().nextDouble() * 2d - 1d);
                    }

                    compareAndAssert(norm1Ref(arr), norm1(arr));
                }
            }
        }

        private static double norm1Ref(double... x) {
            double sum = 0d;
            for (double v : x) {
                double absV = Math.abs(v);
                sum = sum + absV;
            }
            return sum;
        }
    }

    @RunWith(Enclosed.class)
    public static class norm2_test {

        @RunWith(Theories.class)
        public static class norm2_size_and_scale_variation {

            @DataPoints
            public static double[] scales = {
                    Double.MIN_NORMAL,
                    1E-100, 1E-20,
                    1d,
                    1E20, 1E100,
                    Double.MAX_VALUE / 1024
            };

            @DataPoints
            public static int[] sizes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            @Theory
            public void test_parametric(int size, double scale) {

                final int iteration = 20;
                for (int c = 0; c < iteration; c++) {
                    double[] arr = new double[size];
                    for (int i = 0; i < size; i++) {
                        arr[i] = scale * (ThreadLocalRandom.current().nextDouble() * 2d - 1d);
                    }

                    compareAndAssert(norm2Ref(scale, arr), norm2(arr));
                }
            }
        }

        private static double norm2Ref(double scale, double... x) {
            double sum = 0d;
            for (double v : x) {
                double absV = Math.abs(v) / scale;
                sum = sum + absV * absV;
            }
            return Math.sqrt(sum) * Math.abs(scale);
        }
    }

    /**
     * assertion based on relative error.
     */
    private static void compareAndAssert(double expected, double result) {
        if (Double.isFinite(expected)) {
            assertThat(result, is(closeTo(expected, Math.abs(expected) * relativeError)));
        } else {
            assertThat(result, is(expected));
        }
    }
}
