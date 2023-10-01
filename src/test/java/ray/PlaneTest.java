package ray;

import io.vavr.control.Either;
import io.vavr.control.Option;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ray.geometry.Line;
import ray.geometry.elements.Plane;
import ray.geometry.Point;
import ray.geometry.Vector;

public class PlaneTest {
    private static final Point ORIGIN = Point.ORIGIN;
    private static final Point POINT_1 = new Point(1, 0, 0);
    private static final Point POINT_2 = new Point(2, -5, 4);
    private static final Point POINT_3 = new Point(0, 10, -1);

    private static final Vector VECTOR_1 = new Vector(1, 0, 0);
    private static final Vector VECTOR_2 = new Vector(0, 1, 0);
    private static final Vector VECTOR_3 = new Vector(0, 0, 1);
    private static final Vector VECTOR_4 = new Vector(1, 1, 1);
    private static final Vector VECTOR_5 = new Vector(1, -2, 0);
    private static final Vector VECTOR_6 = new Vector(0, -2, 5);

    @DataProvider(name = "testIntersectionWithLine")
    public Object[][] data() {
        return new Object[][]{
            {ORIGIN, ORIGIN, VECTOR_1, VECTOR_1, true, ORIGIN},
            {ORIGIN, POINT_1, VECTOR_1, VECTOR_1, true, ORIGIN},
            {ORIGIN, ORIGIN, VECTOR_1, VECTOR_2, false, null},
            {ORIGIN, POINT_1, VECTOR_1, VECTOR_2, true, null},
            {ORIGIN, ORIGIN, VECTOR_1, VECTOR_3, false, null},
            {ORIGIN, POINT_1, VECTOR_1, VECTOR_3, true, null},
            {ORIGIN, ORIGIN, VECTOR_1, VECTOR_4, true, ORIGIN},
            {ORIGIN, POINT_1, VECTOR_1, VECTOR_4, true, new Point(0, -1, -1)},
            {ORIGIN, ORIGIN, VECTOR_1, VECTOR_5, true, ORIGIN},
            {ORIGIN, POINT_1, VECTOR_1, VECTOR_5, true, new Point(0, 2, 0)},
            {ORIGIN, ORIGIN, VECTOR_1, VECTOR_6, false, null},
            {ORIGIN, POINT_1, VECTOR_1, VECTOR_6, true, null},
            {POINT_2, POINT_3, VECTOR_5, VECTOR_6, true, new Point(0, -6, 39)},
        };
    }

    @Test(dataProvider = "testIntersectionWithLine")
    public void testIntersectionWithLine(
            Point planePoint,
            Point linePoint,
            Vector planeNormal,
            Vector lineDirector,
            boolean isPointIntersection,
            Point expectedIntersectionPoint
    ) {
        Plane plane = new Plane(planeNormal, planePoint);
        Line line = new Line(lineDirector, linePoint);

        Option<Either<Point, Line>> result = plane.intersect(line);

        if (!isPointIntersection) {
            validateExpectedLineIntersection(result, line);
        } else if (expectedIntersectionPoint == null) {
            assert result.isEmpty();
        } else  {
            validateExpectedPointIntersection(result, expectedIntersectionPoint);
        }
    }

    private void validateExpectedPointIntersection(Option<Either<Point, Line>> result, Point expectedIntersection) {
        assert result.isDefined();
        assert result.get().isLeft();
        assert result.get().getLeft().getX() == expectedIntersection.getX();
        assert result.get().getLeft().getY() == expectedIntersection.getY();
        assert result.get().getLeft().getZ() == expectedIntersection.getZ();
    }

    private void validateExpectedLineIntersection(Option<Either<Point, Line>> result, Line line) {
        assert result.isDefined();
        assert result.get().isRight();
        assert result.get().get() == line;
    }
}
