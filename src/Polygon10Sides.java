import java.awt.DisplayMode;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

public class Polygon10Sides implements GLEventListener {
    public static DisplayMode displayMode, displayModeOld;
    private GLU glu = new GLU();

    private float scale;
    private float rotateX, rotateY;
    private float redColor, greenColor, blueColor;

    public Polygon10Sides() {
        this.rotateX = 0.0f;
        this.rotateY = 0.0f;
        this.scale = 0.25f;
        this.redColor = 1.0f;
        this.greenColor = 1.0f;
        this.blueColor = 1.0f;
    }

    public float getRotateX() { return this.rotateX; }
    public float getRotateY() { return this.rotateY; }
    public float getScale() { return this.scale; }
    public float getRedColor() { return this.redColor; }
    public float getGreenColor() { return this.greenColor; }
    public float getBlueColor() { return this.blueColor; }

    public void setRotateX(float rotateX) { this.rotateX = rotateX; }
    public void setRotateY(float rotateY) { this.rotateY = rotateY; }
    public void setScale(float scale) {
        if(scale <= 0.0f) scale = 0.0f;
        else if(scale >= 1.0f) scale = 1.0f;
        this.scale = scale;
    }
    public void setRedColor(float redColor) { this.redColor = redColor; }
    public void setGreenColor(float greenColor) { this.greenColor = greenColor; }
    public void setBlueColor(float blueColor) { this.blueColor = blueColor; }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glDisable( GL2.GL_LIGHTING );
        gl.glDisable( GL2.GL_LIGHT0 );
        gl.glDisable( GL2.GL_NORMALIZE );

        gl.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glScalef(scale,scale,scale);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        //Rotate the cube on X, Y, Z
        gl.glRotatef(rotateY, 0, 1.0f, 0);
        gl.glRotatef(rotateX, 1.0f, 0, 0);

        //Each side of the cube has a different color
        gl.glBegin(GL2.GL_QUADS);



        // 92
        gl.glColor3f(redColor, 0f, 0f);
        gl.glVertex3f(-0.5f, -0.5f, 1.0f);
        gl.glVertex3f(0.5f, -0.5f, 1.0f);
        gl.glVertex3f(0.5f, 0.5f, 1.0f);
        gl.glVertex3f(-0.5f, 0.5f, 1.0f);


        // 93
        gl.glColor3f(redColor, greenColor, 0f);
        gl.glVertex3f(-0.5f, 0.5f, 1.0f);
        gl.glVertex3f(0.5f, 0.5f, 1.0f);
        gl.glVertex3f(1f, 1f, 0f);
        gl.glVertex3f(-1f, 1f, 0f);


        // 94
        gl.glColor3f(0f, 0f, blueColor);
        gl.glVertex3f(-0.5f, -0.5f, 1.0f);
        gl.glVertex3f(-0.5f, 0.5f, 1.0f);
        gl.glVertex3f(-1f, 1f, 0f);
        gl.glVertex3f(-1f, -1f, 0f);

        // 95
        gl.glColor3f(0f, greenColor, 0f);
        gl.glVertex3f(0.5f, -0.5f, 1.0f);
        gl.glVertex3f(-0.5f, -0.5f, 1.0f);
        gl.glVertex3f(-1f, -1f, 0f);
        gl.glVertex3f(1f, -1f, 0f);

        // 96
        gl.glColor3f(0f, greenColor, blueColor);
        gl.glVertex3f(0.5f, 0.5f, 1.0f);
        gl.glVertex3f(0.5f, -0.5f, 1.0f);
        gl.glVertex3f(1f, -1f, 0f);
        gl.glVertex3f(1f, 1f, 0f);

        // 97
        gl.glColor3f(redColor, 0f, blueColor);
        gl.glVertex3f(-1f, -1f, 0f);
        gl.glVertex3f(1f, -1f, 0f);
        gl.glVertex3f(1f, 1f, 0f);
        gl.glVertex3f(-1f, 1f, 0f);

        // 98
        gl.glColor3f(redColor, greenColor, blueColor);
        gl.glVertex3f(-1f, 1f, 0f);
        gl.glVertex3f(1f, 1f, 0f);
        gl.glVertex3f(0.5f, 0.5f, -1.0f);
        gl.glVertex3f(-0.5f, 0.5f, -1.0f);

        // 99
        gl.glColor3f(redColor, 0f, 0f);
        gl.glVertex3f(-1f, -1f, 0f);
        gl.glVertex3f(-1f, 1f, 0f);
        gl.glVertex3f(-0.5f, 0.5f, -1.0f);
        gl.glVertex3f(-0.5f, -0.5f, -1.0f);

        // 100
        gl.glColor3f(redColor, greenColor, 0f);
        gl.glVertex3f(1f, -1f, 0f);
        gl.glVertex3f(-1f, -1f, 0f);
        gl.glVertex3f(-0.5f, -0.5f, -1.0f);
        gl.glVertex3f(0.5f, -0.5f, -1.0f);


        // 101
        gl.glColor3f(0f, 0f, blueColor);
        gl.glVertex3f(1f, 1f, 0f);
        gl.glVertex3f(1f, -1f, 0f);
        gl.glVertex3f(0.5f, -0.5f, -1.0f);
        gl.glVertex3f(0.5f, 0.5f, -1.0f);

        // 102
        gl.glColor3f(0f, greenColor, 0f);
        gl.glVertex3f(-1f, 1f, 0f);
        gl.glVertex3f(1f, 1f, 0f);
        gl.glVertex3f(0.5f, 0.5f, -1.0f);
        gl.glVertex3f(-0.5f, 0.5f, -1.0f);

        // 103
        gl.glColor3f(0f, greenColor, blueColor);
        gl.glVertex3f(0.5f, -0.5f, -1.0f);
        gl.glVertex3f(-0.5f, -0.5f, -1.0f);
        gl.glVertex3f(-0.5f, 0.5f, -1.0f);
        gl.glVertex3f(0.5f, 0.5f, -1.0f);

        gl.glEnd();
        gl.glFlush();

        rotateX -= 0.15f;
        rotateY -= 0.15f;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable( GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();

        final float h = ( float ) width / ( float ) height;
        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        glu.gluPerspective( 45.0f, h, 1.0, 20.0 );
        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();
    }

}
