package ray;

import io.vavr.collection.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ray.geometry.Line;
import ray.geometry.Point;
import ray.geometry.Vector;
import ray.geometry.elements.SphereShell;

@Test
public class SphereShellTest {
    private static final Point ORIGIN = Point.ORIGIN;
    private static final Point POINT_1 = new Point(0, 1, 0);
    private static final Point POINT_2 = new Point(0, -1, 0);
    private static final Point POINT_3 = new Point(1, 0, 0);

    @DataProvider(name = "testIntersectionWithLine")
    public Object[][] data() {
        return new Object[][]{
            {ORIGIN, 1, ORIGIN, Vector.VERTICAL, 2, List.of(POINT_1, POINT_2)},
            {ORIGIN, 1, POINT_3, Vector.VERTICAL, 1, List.of(POINT_3)},
            {ORIGIN, 0.5, POINT_3, Vector.VERTICAL, 0, List.empty()},
        };
    }

    @Test(dataProvider = "testIntersectionWithLine")
    public void testIntersectionWithLine(
        Point center,
        double radius,
        Point linePoint,
        Vector lineDirector,
        int expectedIntersectionPointsCount,
        List<Point> expectedIntersectionPoints
    ) {
        SphereShell sphereShell = new SphereShell(center, radius);
        Line line = new Line(lineDirector, linePoint);

        List<Point> result = sphereShell.intersect(line);

        assert result.length() == expectedIntersectionPointsCount;
        result.zip(expectedIntersectionPoints)
            .forEach(pointsTuple -> {
                assert pointsTuple._1().equals(pointsTuple._2());
            });
    }
}
