import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.util.Random;

public class Paddle implements GLEventListener {
    private float rotation = 0.0f;

    private float scale;
    private float rotateX, rotateY;

    public Paddle() {
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

        this.scale = scale;
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glDisable( GL2.GL_LIGHTING );
        gl.glDisable( GL2.GL_LIGHT0 );
        gl.glDisable( GL2.GL_NORMALIZE );

        gl.glClearColor(0.9f,0.9f,0.9f,1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glScalef(scale, scale, scale);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        //gl.glTranslatef(0f, 0f, -2.0f);
        //gl.glRotatef(rotation, 1f, 0f, 0f);

        gl.glRotatef(rotateY,0,1.0f,0);
        gl.glRotatef(rotateX,1.0f,0,0);

        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(1f, 0f, 0f);

        gl.glVertex3d(-0.5,0.3,0.8);
        gl.glVertex3d(0.5,0.3,0.8);
        gl.glVertex3d(0.8,0.7,0.8);
        gl.glVertex3d(-0.8,0.7,0.8);
        gl.glEnd();

        int paddles = 30;
        for(int i = 0; i < paddles; i++) {
            gl.glRotatef(360/paddles, 1, 0, 0);
            gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3f(randFloat(), randFloat(), randFloat());
            gl.glVertex3d(-0.5,0.3,0.8);
            gl.glVertex3d(0.5,0.3,0.8);
            gl.glVertex3d(0.8,0.7,0.8);
            gl.glVertex3d(-0.8,0.7,0.8);

            gl.glEnd();
        }

        gl.glFlush();

        rotateX -= 0.15f;
        rotateY -= 0.15f;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height) {

    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
                               boolean deviceChanged) {
    }
    @Override
    public void dispose(GLAutoDrawable arg0) {


    }
    @Override
    public void init(GLAutoDrawable arg0) {


    }

    public float randFloat() {
        Random rand = new Random();

        return rand.nextFloat();
    }
}
