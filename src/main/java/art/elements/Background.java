package art.elements;

import art.Color;
import lombok.Getter;
import ray.geometry.Point;
import ray.geometry.Vector;
import ray.geometry.elements.Plane;


public class Background extends PhysicalElement<Plane> {
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GREY;
    private static final Plane BACKGROUND_PLANE = new Plane(Vector.HORIZONTAL, Vector.VERTICAL, Point.FAR_DEEP);
    @Getter
    private static final Background background = new Background();

    private Background() {
        super(BACKGROUND_PLANE, BACKGROUND_COLOR);
    }
}
