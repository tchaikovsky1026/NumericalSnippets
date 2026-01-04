/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

package snippet.num;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static snippet.num.Exponentiation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link Exponentiation} のテスト
 */
@RunWith(Enclosed.class)
final class ExponentiationTest {

    private static final double relativeError = 1E-12;

    @RunWith(Theories.class)
    public static class logSumExpのサイズバリエーションテスト {

        @DataPoints
        public static List<double[]> data;

        @BeforeClass
        public static void before_prepareData() {
            data = new ArrayList<double[]>();

            double[][] data_special = {
                    { -20d, 2d, 4d, 5d },
                    { 1d, 2d, 3d, 4d },
                    {
                            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY
                    },
                    {
                            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY },
                    { Double.NaN, 1, 1, 1 },
                    { 1, Double.NaN, 1, 1 },
                    { 1, 1, Double.NaN, 1 },
                    { 1, 1, 1, Double.NaN }
            };
            for (double[] arr : data_special) {
                data.add(arr);
            }

            final int iteration = 1000;
            for (int c = 0; c < iteration; c++) {
                data.add(
                        new double[] {
                                500 * (ThreadLocalRandom.current().nextDouble() * 2d - 1d),
                                500 * (ThreadLocalRandom.current().nextDouble() * 2d - 1d),
                                500 * (ThreadLocalRandom.current().nextDouble() * 2d - 1d),
                                500 * (ThreadLocalRandom.current().nextDouble() * 2d - 1d)
                        });
            }
        }

        @Theory
        public void test_サイズ0(double[] arrX) {
            double expected = Double.NEGATIVE_INFINITY;

            compareAndAssert(expected, logSumExp());
        }

        @Theory
        public void test_サイズ1(double[] arrX) {
            double x1 = arrX[0];

            double expected = x1;
            compareAndAssert(expected, logSumExp(x1));
        }

        @Theory
        public void test_サイズ2(double[] arrX) {
            double x1 = arrX[0];
            double x2 = arrX[1];

            double expected = Math.log(Math.exp(x1) + Math.exp(x2));
            compareAndAssert(expected, logSumExp(x1, x2));
            compareAndAssert(expected, logSumExp(x2, x1));
        }

        @Theory
        public void test_サイズ3(double[] arrX) {
            double x1 = arrX[0];
            double x2 = arrX[1];
            double x3 = arrX[2];

            double expected = Math.log(Math.exp(x1) + Math.exp(x2) + Math.exp(x3));
            compareAndAssert(expected, logSumExp(x1, x2, x3));
            compareAndAssert(expected, logSumExp(x1, x3, x2));
            compareAndAssert(expected, logSumExp(x3, x2, x1));
        }

        @Theory
        public void test_サイズ4(double[] arrX) {
            double x1 = arrX[0];
            double x2 = arrX[1];
            double x3 = arrX[2];
            double x4 = arrX[3];

            double expected = Math.log(
                    Math.exp(x1) + Math.exp(x2) + Math.exp(x3) + Math.exp(x4));
            compareAndAssert(expected, logSumExp(x1, x2, x3, x4));
            compareAndAssert(expected, logSumExp(x1, x2, x4, x3));
            compareAndAssert(expected, logSumExp(x1, x3, x4, x2));
            compareAndAssert(expected, logSumExp(x2, x3, x4, x1));
        }
    }

    /**
     * assertion based on relative error.
     */
    private static void compareAndAssert(double expected, double result) {
        //数なら範囲比較, Inf,非数の場合はequal比較
        if (Double.isFinite(expected)) {
            assertThat(result, is(closeTo(expected, Math.abs(expected) * relativeError)));
        } else {
            assertThat(result, is(expected));
        }
    }
}
