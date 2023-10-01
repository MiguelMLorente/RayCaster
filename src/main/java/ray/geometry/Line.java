package ray.geometry;

import lombok.Getter;

@Getter
public class Line {
    private final Vector directorVector;
    private final Point point;

    public Line(Vector directorVector, Point point) {
        if (directorVector.module() == 0) throw new RuntimeException("Plane cannot be constructed with null vector");
        this.directorVector = directorVector;
        this.point = point;
    }
}
