/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

package snippet.num;

import static java.util.stream.Collectors.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static snippet.num.Exponentiation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private static final double relativeError = 1E-14;

    @RunWith(Theories.class)
    public static class logSumExpのサイズバリエーションテスト {

        @DataPoints
        public static int[] sizes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        @Theory
        public void test_サイズパラメトリック(int size) {

            final int iteration = 20;
            for (int c = 0; c < iteration; c++) {
                double[] arr = new double[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = 500 * (ThreadLocalRandom.current().nextDouble() * 2d - 1d);
                }

                compareAndAssert(logSumExpRef(arr), logSumExp(arr));
            }
        }
    }

    @RunWith(Theories.class)
    public static class logSumExpの特殊値のサイズバリエーションテスト {

        @DataPoints
        public static int[] sizes = { 0, 1, 2, 3, 4 };

        @DataPoints
        public static double[][] data_special = {
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

        @Theory
        public void test_サイズパラメトリック(double[] arrX, int size) {
            double[] xs = Arrays.copyOf(arrX, size);

            final int iteration = 20;
            for (int c = 0; c < iteration; c++) {
                // arr = shaffled xs 
                List<Double> xsList = new ArrayList<>(
                        Arrays.stream(xs)
                                .mapToObj(d -> d)
                                .collect(toList()));
                Collections.shuffle(xsList);
                double[] arr = xsList.stream()
                        .mapToDouble(d -> d)
                        .toArray();

                compareAndAssert(logSumExpRef(arr), logSumExp(arr));
            }
        }
    }

    @RunWith(Theories.class)
    public static class logSumExpのリファレンスのサイズバリエーションテスト {

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

            compareAndAssert(expected, logSumExpRef());
        }

        @Theory
        public void test_サイズ1(double[] arrX) {
            double x1 = arrX[0];

            double expected = x1;
            compareAndAssert(expected, logSumExpRef(x1));
        }

        @Theory
        public void test_サイズ2(double[] arrX) {
            double x1 = arrX[0];
            double x2 = arrX[1];

            double expected = Math.log(Math.exp(x1) + Math.exp(x2));
            compareAndAssert(expected, logSumExpRef(x1, x2));
            compareAndAssert(expected, logSumExpRef(x2, x1));
        }

        @Theory
        public void test_サイズ3(double[] arrX) {
            double x1 = arrX[0];
            double x2 = arrX[1];
            double x3 = arrX[2];

            double expected = Math.log(Math.exp(x1) + Math.exp(x2) + Math.exp(x3));
            compareAndAssert(expected, logSumExpRef(x1, x2, x3));
            compareAndAssert(expected, logSumExpRef(x1, x3, x2));
            compareAndAssert(expected, logSumExpRef(x3, x2, x1));
        }

        @Theory
        public void test_サイズ4(double[] arrX) {
            double x1 = arrX[0];
            double x2 = arrX[1];
            double x3 = arrX[2];
            double x4 = arrX[3];

            double expected = Math.log(
                    Math.exp(x1) + Math.exp(x2) + Math.exp(x3) + Math.exp(x4));
            compareAndAssert(expected, logSumExpRef(x1, x2, x3, x4));
            compareAndAssert(expected, logSumExpRef(x1, x2, x4, x3));
            compareAndAssert(expected, logSumExpRef(x1, x3, x4, x2));
            compareAndAssert(expected, logSumExpRef(x2, x3, x4, x1));
        }
    }

    private static double logSumExpRef(double... x) {

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
