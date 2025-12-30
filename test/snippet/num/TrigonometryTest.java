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
    }
}
