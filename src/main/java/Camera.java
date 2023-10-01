import art.Scene;
import art.elements.Background;
import art.elements.PhysicalElement;
import io.vavr.Tuple2;
import io.vavr.collection.Set;
import ray.Ray;
import ray.geometry.Point;
import ray.geometry.Vector;

import java.awt.*;

public class Camera {
    private final Point focalPoint = Point.ORIGIN;
    private static final double VIEW_ANGLE = 15 * 3.141593 / 180;

    private final double angleStep;
    private final double horizontalSteps;
    private final double verticalSteps;


    Camera(Dimension screenSize) {
        this.horizontalSteps = screenSize.width;
        this.verticalSteps = screenSize.height;
        this.angleStep = VIEW_ANGLE * 2 / horizontalSteps;
    }

    public PhysicalElement observePosition(
        int horizontalPosition,
        int verticalPosition,
        Scene scene
    ) {
        double azimuth = angleStep * (horizontalPosition - horizontalSteps / 2);
        double altitude = angleStep * (verticalPosition - verticalSteps / 2);

        Ray ray = new Ray(new Vector(
            Math.sin(azimuth),
            Math.sin(altitude),
            -1
        ), focalPoint);

        Set<PhysicalElement> observedElement = scene
            .getElements()
            .filter(physicalElement ->
                ray.castRay(scene
                        .getElements()
                        .map(PhysicalElement::getElement)
                    ).map(Tuple2::_1)
                    .contains(physicalElement.getElement())
            );
        if (observedElement.length() > 0) return observedElement.get();
        else return Background.getBackground();
    }
}
