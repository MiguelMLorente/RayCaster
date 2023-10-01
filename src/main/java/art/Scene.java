package art;

import art.elements.PhysicalElement;
import art.elements.Sphere;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.Getter;
import ray.geometry.Point;

@Getter
public class Scene {
    private final Set<PhysicalElement> elements = HashSet.of(
        new Sphere(new Point(0, 0, -10), 1, Color.RED),
        new Sphere(new Point(2, 3, -10), 1.3, Color.BLUE),
        new Sphere(new Point(1, -2, -10), 0.5, Color.GREEN),
        new Sphere(new Point(-1, 1, -10), 0.7, Color.BLUE)
    );
}
