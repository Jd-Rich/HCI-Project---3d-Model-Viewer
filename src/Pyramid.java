import java.awt.DisplayMode;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;

public class Pyramid implements GLEventListener {
    public static DisplayMode displayMode, displayModeOld;
    private GLU glu = new GLU();
    private float rtri = 0.0f;

    @Override
    public void display (GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Clear screen and depth buffer
        gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );

        // Reset view
        gl.glLoadIdentity();

        // Move the triangle
        gl.glTranslatef(-0.5f,0.0f, -6.0f);

        // Rotate the pyramid on x, y, and z axis
        gl.glRotatef(rtri, 1.0f, 1.0f, 1.0f);

        // Begin drawing the pyramid
        gl.glBegin(GL2.GL_TRIANGLES);

        // Draw 3D shape of pyramid with light blue sides and yellow edges
        // Front side of pyramid
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f); // Face color
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Bottom edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Left edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Right edge

        // Right side of pyramid
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f); // Face color
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Bottom edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Left edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Right edge

        // Left side of pyramid
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f); // Face color
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Bottom edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Left edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Right edge

        // Bottom side of pyramid
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f); // Face color
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Bottom edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Left edge
        gl.glVertex4f(1.0f, 1.0f, 0.0f, 0.0f); // Right edge

        //End pyramid drawing
        gl.glEnd();
        gl.glFlush();

        rtri += 0.2f;
    }

    @Override
    public void dispose (GLAutoDrawable drawable) {}

    @Override
    public void init (GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();

        if(height <= 0)
            height = 1;

        final float h = (float)width / (float)height;

        gl.glViewport(0,0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
