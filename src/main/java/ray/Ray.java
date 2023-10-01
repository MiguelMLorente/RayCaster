package ray;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import io.vavr.control.Option;
import ray.geometry.Line;
import ray.geometry.Point;
import ray.geometry.Vector;
import ray.geometry.elements.GeometricElement;
import ray.geometry.elements.Plane;
import ray.geometry.elements.SphereShell;

public class Ray extends Line {
    public Ray(Vector directorVector, Point point) {
        super(directorVector, point);
    }

    public Option<Tuple2<GeometricElement, Point>> castRay(Set<GeometricElement> elements) {
        assert !elements.isEmpty();

        Option<Set<Tuple2<GeometricElement, Point>>> list = Option.of(elements
            .map(this::castRay)
            .filter(Option::isDefined)
            .map(Option::get)
        );

        if (list.isDefined() && !list.get().isEmpty()) {
            return list.map(elementsList -> elementsList.reduce((a, b) -> getClosestPoint(a._2(), b._2()) == a._2() ? a : b));
        } else {
            return Option.none();
        }
    }

    private Option<Tuple2<GeometricElement, Point>> castRay(GeometricElement element) {
        if (element instanceof Plane) {
            Plane plane = (Plane) element;
            return plane
                .intersect(this)
                .map(Either::getLeft) // Ignoring the scenario where line is contained in the plane
                .map(point -> point != null ? point : this.getPoint())
                .filter(this::isForwardPoint)
                .map(point -> new Tuple2<>(plane, point));
        } else if (element instanceof SphereShell) {
            SphereShell sphereShell = (SphereShell) element;
            return Option.of(sphereShell.intersect(this))
                .filter(points -> !points.isEmpty())
                .map(points -> points
                    .filter(this::isForwardPoint)
                    .reduce(this::getClosestPoint)
                )
                .map(point -> new Tuple2<>(sphereShell, point));
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean isForwardPoint(Point point) {
        return new Vector(this.getPoint(), point).dot(this.getDirectorVector()) >= 0;
    }

    private Point getClosestPoint(Point... points) {
        return List.of(points).reduce((a, b) -> distanceToOrigin(a) <= distanceToOrigin(b) ? a : b);
    }

    private double distanceToOrigin(Point point) {
        return new Vector(this.getPoint(), point).module();
    }
}
