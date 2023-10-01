package art.elements;

import art.Color;
import ray.geometry.Point;
import ray.geometry.elements.SphereShell;

public class Sphere extends PhysicalElement<SphereShell> {
    public Sphere(Point center, double radius, Color color) {
        super(new SphereShell(center, radius), color);
    }
}
