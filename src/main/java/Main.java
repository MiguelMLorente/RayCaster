import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        setupCanvas();
    }

    private static void setupCanvas() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.addGLEventListener(new Canvas(glcanvas));
        glcanvas.setSize( 400, 400 );

        Frame frame = new Frame( "art.Wallpaper" );
        frame.add(glcanvas);
        frame.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 4, screenSize.height / 3);
    }
}
