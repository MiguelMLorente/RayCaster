package ray.geometry.elements;


import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.Getter;
import ray.geometry.Line;
import ray.geometry.Point;
import ray.geometry.Vector;

@Getter
public class Plane extends GeometricElement<Option<Either<Point, Line>>> {
    private final Vector normalVector;
    private final Point point;

    public Plane(Vector normalVector, Point point) {
        if (normalVector.module() == 0) throw new RuntimeException("Plane cannot be constructed with null vector");
        this.normalVector = normalVector;
        this.point = point;
    }

    public Plane(Vector direction1, Vector direction2, Point point) throws RuntimeException {
        Vector normalVector = direction1.cross(direction2).normalize();
        if (normalVector.module() == 0) {
            throw new RuntimeException("Error building plane: director vectors are co-linear");
        }
        this.normalVector = normalVector;
        this.point = point;
    }

    /**
     * Plane vector notation: (P - P0) * n = 0
     *      -> any pair of points in the plane forms a vector contained in the plane
     * Line vector notation: (P - P0) = lambda * d
     *      -> any pair of points in the line forms a vector co-linear to the director vector
     * Denoting:
     *      - P: any point in the plane
     *      - L: any point in the line
     *      - n: plane's normal vector
     *      - d: line's director vector
     *      - R: intersection point
     * R = L + d * [ (P - L) * n / (n * d) ]
     */
    @Override
    public Option<Either<Point, Line>> intersect(Line line) {
        Point P = this.getPoint();
        Point L = line.getPoint();
        Vector n = this.getNormalVector();
        Vector d = line.getDirectorVector();
        try {
            double numerator = (new Vector(L, P)).dot(n);
            double denominator =  n.dot(d);
            // When denominator is 0, the line is parallel to the plane (or contained in it). Return empty Option
            if (denominator == 0) throw new ArithmeticException("Plane and line are parallel in Line::intersect");

            double scale = numerator / denominator;
            Vector result = (new Vector(Point.ORIGIN, L)).add(d.multiply(scale));

            return Option.of(Either.left(result));
        } catch (Exception e) {
            if (this.containsPoint(L)) {
                return Option.of(Either.right(line));
            }
            return Option.none();
        }
    }

    @Override
    public double distanceToPoint(Point point) {
        return new Vector(this.getPoint(), point).project(this.getNormalVector()).module();
    }
}
