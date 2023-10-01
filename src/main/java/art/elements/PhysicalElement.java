package art.elements;

import art.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ray.geometry.elements.GeometricElement;

@RequiredArgsConstructor
@Getter
public abstract class PhysicalElement<T extends GeometricElement> {
    private final T element;
    private final Color color;
}
