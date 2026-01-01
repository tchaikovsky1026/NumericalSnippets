/*
 * This is free and unencumbered software released into the public domain.
 * See the UNLICENSE file for details.
 */

/*
 * 2025.12.30
 */
package snippet.num;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static snippet.num.Trigonometry.*;

import java.util.stream.DoubleStream;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link Trigonometry} test.
 */
@RunWith(Enclosed.class)
final class TrigonometryTest {

    @RunWith(Theories.class)
    public static class trigPi_parametric {

        @DataPoints
        public static double[] xs;

        @BeforeClass
        public static void before_prepareX() {
            double xMin = -30d;
            double xMax = 30d;
            double deltaX = 1d / 64;

            xs = DoubleStream.iterate(xMin, x -> x + deltaX)
                    .limit(100_000)
                    .filter(x -> x <= xMax)
                    .toArray();
        }

        @Theory
        public void test_sinpi(double x) {
            // compare Java API
            assertThat(sinpi(x), is(closeTo(Math.sin(Math.PI * x), 1E-12)));
        }

        @Theory
        public void test_cospi(double x) {
            // compare Java API
            assertThat(cospi(x), is(closeTo(Math.cos(Math.PI * x), 1E-12)));
        }

        @Theory
        public void test_tanpi(double x) {
            if (Math.abs(Math.IEEEremainder(x, 1d)) == 0.5) {
                assertThat(Double.isInfinite(tanpi(x)), is(true));
            } else {
                // compare Java API
                double expected = Math.tan(Math.PI * x);
                assertThat(tanpi(x), is(closeTo(expected, 1E-12 * (1 + Math.abs(expected)))));
            }
        }
    }

    @RunWith(Theories.class)
    public static class asinpi_and_acospi_compareToJavaApi {

        @DataPoints
        public static double[] xs;

        @BeforeClass
        public static void before_prepareX() {
            double xMin = -1d;
            double xMax = 1d;
            double deltaX = 1d / 64;

            xs = DoubleStream.iterate(xMin, x -> x + deltaX)
                    .limit(10_000)
                    .filter(x -> x <= xMax)
                    .toArray();
        }

        @Theory
        public void test_asinpi(double x) {
            // compare to Java API
            assertThat(asinpi(x), is(closeTo(Math.asin(x) / Math.PI, 1E-12)));
        }

        @Theory
        public void test_acospi(double x) {
            // compare Java API
            assertThat(acospi(x), is(closeTo(Math.acos(x) / Math.PI, 1E-12)));
        }
    }

    @RunWith(Theories.class)
    public static class asinpi_special {

        @DataPoints
        public static double[][] xs_and_expected = {
                { -1d, -0.5d },
                { 0d, 0d },
                { 1d, 0.5d },
                { Double.NaN, Double.NaN }
        };

        @Theory
        public void test_asinpi_at_special(double[] pair) {
            double x = pair[0];
            double expected = pair[1];

            assertThat(asinpi(x) + 0d, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class acospi_special {

        @DataPoints
        public static double[][] xs_and_expected = {
                { -1d, 1d },
                { 0d, 0.5d },
                { 1d, 0d },
                { Double.NaN, Double.NaN }
        };

        @Theory
        public void test_acospi_at_special(double[] pair) {
            double x = pair[0];
            double expected = pair[1];

            assertThat(acospi(x) + 0d, is(expected));
        }
    }

    @RunWith(Theories.class)
    public static class atanpi_compareToJavaApi {

        @DataPoints
        public static double[] xs;

        @BeforeClass
        public static void before_prepareX() {
            double xMin = -10d;
            double xMax = 10d;
            double deltaX = 1d / 64;

            xs = DoubleStream.iterate(xMin, x -> x + deltaX)
                    .limit(10_000)
                    .filter(x -> x <= xMax)
                    .toArray();
        }

        @Theory
        public void test_atanpi(double x) {
            // compare to Java API
            double expected = Math.atan(x) / Math.PI;
            assertThat(atanpi(x), is(closeTo(expected, 1E-200 + 1E-12 * Math.abs(expected))));
        }
    }

    @RunWith(Theories.class)
    public static class atanpi_special {

        @DataPoints
        public static double[][] xs_and_expected = {
                { Double.NEGATIVE_INFINITY, -0.5d },
                { -1d, -0.25d },
                { 0d, 0d },
                { 1d, 0.25d },
                { Double.POSITIVE_INFINITY, 0.5d },
                { Double.NaN, Double.NaN }
        };

        @Theory
        public void test_atanpi_at_special(double[] pair) {
            double x = pair[0];
            double expected = pair[1];

            assertThat(atanpi(x) + 0d, is(expected));
        }
    }
}
