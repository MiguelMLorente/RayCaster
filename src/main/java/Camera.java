import art.Scene;
import art.elements.Background;
import art.elements.PhysicalElement;
import io.vavr.Tuple2;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import ray.Ray;
import ray.geometry.Point;
import ray.geometry.Vector;

import java.awt.*;

public class Camera {
    private final Point focalPoint = Point.ORIGIN;
    private final double viewAngle = 15 * 3.141593 / 180;

    private final double azimuthStep;
    private final double altitudeStep;


    Camera(Dimension screenSize) {
        this.azimuthStep = viewAngle * 2 / screenSize.width;
        this.altitudeStep = viewAngle * 2 / screenSize.height;
    }

    public PhysicalElement observePosition(
        int horizontalPosition,
        int verticalPosition,
        Scene scene
    ) {
        double azimuth = -viewAngle + azimuthStep * horizontalPosition;
        double altitude = -viewAngle + altitudeStep * verticalPosition;

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
