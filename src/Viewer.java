import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Viewer extends JFrame implements GLEventListener {
    private static final long serialVersionUID = 1L;

    final private int width = 800;
    final private int height = 600;

    public Viewer() {
        super("3D Model Viewer");
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas canvas = new GLCanvas(capabilities);

        canvas.addGLEventListener(this);
        this.setName("3D Model Viewer");
        this.add(canvas);

        this.setSize(width, height);
        this.setBackground(Color.WHITE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);

        canvas.requestFocusInWindow();
    }

    public void play() {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Draw code goes here

        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Changes default black color of window when color buffer is cleared

        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
}
