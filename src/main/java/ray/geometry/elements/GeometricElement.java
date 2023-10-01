package ray.geometry.elements;

import ray.geometry.Line;
import ray.geometry.Point;

public abstract class GeometricElement<T> {
    static double THRESHOLD = 0.00001;
    public abstract T intersect(Line line);

    public abstract double distanceToPoint(Point point);

    public boolean containsPoint(Point point) {
        return this.distanceToPoint(point) <= THRESHOLD;
    }
}
