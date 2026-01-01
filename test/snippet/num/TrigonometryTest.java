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
import org.junit.Test;
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

            // sinpi(x) ~= -sinpi(x+1)
            if (Math.abs(x) < 1d) {
                return;
            }
            double xShift = x < 0 ? x + 1d : x - 1d;
            assertThat(sinpi(xShift), is(closeTo(-sinpi(x), 1E-20)));
        }

        @Theory
        public void test_cospi(double x) {
            // compare Java API
            assertThat(cospi(x), is(closeTo(Math.cos(Math.PI * x), 1E-12)));

            // cospi(x) ~= -cospi(x+1)
            if (Math.abs(x) < 1d) {
                return;
            }
            double xShift = x < 0 ? x + 1d : x - 1d;
            assertThat(cospi(xShift), is(closeTo(-cospi(x), 1E-20)));
        }

        @Theory
        public void test_tanpi(double x) {
            if (Math.abs(Math.IEEEremainder(x, 1d)) == 0.5) {
                assertThat(Double.isInfinite(tanpi(x)), is(true));
                return;
            }

            // compare Java API
            double expected = Math.tan(Math.PI * x);
            assertThat(tanpi(x), is(closeTo(expected, 1E-12 * (1 + Math.abs(expected)))));

            // tanpi(x) ~= tanpi(x+1)
            if (Math.abs(x) < 1d) {
                return;
            }
            double xShift = x < 0 ? x + 1d : x - 1d;
            assertThat(tanpi(xShift), is(closeTo(tanpi(x), 1E-20)));
        }
    }

    @RunWith(Theories.class)
    public static class asinpi_and_acospi_parametric {

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
            // compare Java API
            assertThat(asinpi(x), is(closeTo(Math.asin(x) / Math.PI, 1E-12)));
        }

        @Theory
        public void test_acospi(double x) {
            // compare Java API
            assertThat(acospi(x), is(closeTo(Math.acos(x) / Math.PI, 1E-12)));
        }
    }

    public static class asinpi_and_acospi_special {

        @Test
        public void test_asinpi_at_m1() {
            assertThat(asinpi(-1d), is(-0.5d));
        }

        @Test
        public void test_asinpi_at_0() {
            assertThat(asinpi(0d) + 0d, is(0d));
        }

        @Test
        public void test_asinpi_at_p1() {
            assertThat(asinpi(1d), is(0.5d));
        }

        @Test
        public void test_acospi_at_m1() {
            assertThat(acospi(-1d), is(1d));
        }

        @Test
        public void test_acospi_at_0() {
            assertThat(acospi(0d), is(0.5d));
        }

        @Test
        public void test_acospi_at_p1() {
            assertThat(acospi(1d) + 0d, is(0d));
        }
    }

    @RunWith(Theories.class)
    public static class atanpi_parametric {

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
            // compare Java API
            double expected = Math.atan(x) / Math.PI;
            assertThat(atanpi(x), is(closeTo(expected, 1E-200 + 1E-12 * Math.abs(expected))));
        }
    }

    public static class atanpi_special {

        @Test
        public void test_atanpi_at_minf() {
            assertThat(atanpi(Double.NEGATIVE_INFINITY), is(-0.5d));
        }

        @Test
        public void test_atanpi_at_m1() {
            assertThat(atanpi(-1d), is(-0.25d));
        }

        @Test
        public void test_atanpi_at_0() {
            assertThat(atanpi(0d) + 0d, is(0d));
        }

        @Test
        public void test_atanpi_at_p1() {
            assertThat(atanpi(1d), is(0.25d));
        }

        @Test
        public void test_atanpi_at_pinf() {
            assertThat(atanpi(Double.POSITIVE_INFINITY), is(0.5d));
        }
    }
}
