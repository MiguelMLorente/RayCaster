import art.Color;
import art.Scene;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class Canvas implements GLEventListener {
    private final GLCanvas glCanvas;

    @Override
    public void display(GLAutoDrawable drawable) {
        draw(drawable);
    }

    public void init(GLAutoDrawable glAutoDrawable) {}
    public void dispose(GLAutoDrawable glAutoDrawable) {}
    public void reshape(GLAutoDrawable drawable, int i, int i1, int i2, int i3) {
        draw(drawable);
    }

    private void draw(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        Dimension screenSize = glCanvas.getSize();
        Camera camera = new Camera(screenSize);
        Scene scene = new Scene();

        double xResolution = 1 / (double) screenSize.width;
        double yResolution = 1 / (double) screenSize.height;
        gl.glBegin (GL2.GL_POINTS);
        for (int xPosition = 0; xPosition <= screenSize.width; xPosition++) {
            for (int yPosition = 0; yPosition <= screenSize.height; yPosition++) {
                Color color =  camera.observePosition(xPosition, yPosition, scene).getColor();
                gl.glColor3d(color.red, color.green, color.blue);
                gl.glVertex3d(2 * xPosition * xResolution - 1, 2 * yPosition * yResolution - 1, 0);
            }
        }

        gl.glEnd();
        gl.glFlush();
    }
}