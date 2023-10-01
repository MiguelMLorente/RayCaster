package ray.geometry;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Point {
    public static final Point ORIGIN = new Point(0, 0, 0);
    public static final Point FAR_DEEP = new Point(0, 0, -100);

    protected double x;
    protected double y;
    protected double z;

    public Point translate(Vector vector) {
        return new Point(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }

    public boolean equals(Point other) {
        return this.getX() == other.getX()
            && this.getY() == other.getY()
            && this.getZ() == other.getZ();
    }

    @Override
    public String toString() {
        return String.format("[x = %f; y = %f; z = %f]", x, y, z);
    }
}
