package ray.geometry.elements;

import io.vavr.collection.List;
import lombok.Getter;
import ray.geometry.Line;
import ray.geometry.Point;
import ray.geometry.Vector;

@Getter
public class SphereShell extends GeometricElement<List<Point>> {
    private final Point center;
    private final double radius;

    public SphereShell(Point center, double radius) {
        if (radius <= 0) {
            throw new RuntimeException("Building a sphere needs a radius grater than 0");
        }
        this.center = center;
        this.radius = radius;
    }

    @Override
    public List<Point> intersect(Line line) {
        Vector centerToPointVector = new Vector(this.getCenter(), line.getPoint());
        Vector centerToLineVector = centerToPointVector.subtract(centerToPointVector.project(line.getDirectorVector()));

        if (centerToLineVector.module() > this.getRadius()) {
            return List.empty();
        } else if (centerToLineVector.module() == this.getRadius()) {
            return List.of(this.getCenter().translate(centerToLineVector));
        } else {
            double size = Math.sqrt(Math.pow(this.getRadius(), 2) - centerToLineVector.norm());
            Vector scaledDirectorVector = line.getDirectorVector().normalize().multiply(size);
            Vector originToCenterVector = new Vector(Point.ORIGIN, this.getCenter());
            return List.of(
                originToCenterVector.add(centerToLineVector).add(scaledDirectorVector),
                originToCenterVector.add(centerToLineVector).subtract(scaledDirectorVector)
            );
        }
    }

    @Override
    public double distanceToPoint(Point point) {
        return Math.abs(new Vector(this.getCenter(), point).module() - this.getRadius());
    }
}
