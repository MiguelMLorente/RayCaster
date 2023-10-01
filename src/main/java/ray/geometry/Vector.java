package ray.geometry;

public class Vector extends Point {
    private static final double PI = 3.141593;

    public static final Vector HORIZONTAL = new Vector(1, 0, 0);
    public static final Vector VERTICAL = new Vector(0, 1, 0);

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector(Point start, Point end) {
        super(end.x - start.x, end.y - start.y, end.z - start.z);
    }

    public double norm() {
        return this.dot(this);
    }

    public double module() {
        return Math.sqrt(this.norm());
    }

    public Vector normalize() {
        double module = this.module();
        return new Vector(
            this.x / module,
            this.y / module,
            this.z / module
        );
    }

    public Vector add(Vector other) {
        return new Vector(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        );
    }

    public Vector subtract(Vector other) {
        return this.add(other.multiply(-1));
    }

    public Vector multiply(double scale) {
        return new Vector(
            this.x * scale,
            this.y * scale,
            this.z * scale
        );
    }

    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector cross(Vector other) {
        return new Vector(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    public Vector project(Vector direction) {
        double scale = this.dot(direction) / direction.module();
        return direction.multiply(scale);
    }
}
