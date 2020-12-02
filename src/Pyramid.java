import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;


public class Pyramid implements GLEventListener {
    private GLU glu = new GLU();
    //private float rtri = 0.0f;

    private float scale;
    private float rotateX, rotateY;

    public Pyramid() {
        this.rotateX = 0.0f;
        this.rotateY = 0.0f;
        this.scale = 0.25f;
    }

    public float getRotateX() { return this.rotateX; }
    public float getRotateY() { return this.rotateY; }
    public float getScale() { return this.scale; }

    public void setRotateX(float rotateX) { this.rotateX = rotateX; }
    public void setRotateY(float rotateY) { this.rotateY = rotateY; }
    public void setScale(float scale) {
        if(scale <= 0.0f) scale = 0.0f;
        else if(scale >= 1.0f) scale = 1.0f;
        this.scale = scale; }

    @Override
    public void display (GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0, 0, 0, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glOrtho(-scale,scale,-scale,scale,-2*scale,2*scale);
        gl.glScalef(scale,scale,scale);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        //gl.glTranslatef(0f, 0f, -5.0f);
        gl.glLoadIdentity();

        //Rotate the cube on X, Y, Z
        gl.glRotatef(rotateY, 0, 1.0f, 0);
        gl.glRotatef(rotateX, 1.0f, 0, 0);
        // Begin drawing the pyramid
        gl.glBegin(GL2.GL_TRIANGLES);

        // Draw 3D shape of pyramid with red, blue, green, and yellow sides
        // Front side of pyramid
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Face color
        gl.glVertex3f(1.0f, 2.0f, 0.0f); // Bottom edge
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Face color
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Left edge
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Face color
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Right edge

        // Right side of pyramid
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Face color
        gl.glVertex3f(1.0f, 2.0f, 0.0f); // Bottom edge
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Face color
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Left edge
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Face color
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Right edge

        // Left side of pyramid
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Face color
        gl.glVertex3f(1.0f, 2.0f, 0.0f); // Bottom edge
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Face color
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Left edge
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Face color
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Right edge

        // Bottom side of pyramid
        gl.glColor3f(1.0f, 1.0f, 0.0f); // Face color
        gl.glVertex3f(1.0f, 2.0f, 0.0f); // Bottom edge
        gl.glColor3f(1.0f, 1.0f, 0.0f); // Face color
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Left edge
        gl.glColor3f(1.0f, 1.0f, 0.0f); // Face color
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Right edge

        //End pyramid drawing
        gl.glEnd();
        gl.glFlush();

        //rtri += 0.2f;
        rotateX -= 0.15f;
        rotateY -= 0.15f;
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
