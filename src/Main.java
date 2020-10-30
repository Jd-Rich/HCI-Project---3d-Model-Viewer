import java.applet.Applet;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.DisplayMode;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;


public class Main {

    public static void main(String[] args) {
        //Create GLprofile and capabilities objects
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        //Create the canvas
        final GLCanvas canvas = new GLCanvas(capabilities);
        //JavaFrame javaCanvas = new JavaFrame();
        Cube cube = new Cube();
        Pyramid pyramid = new Pyramid();

        canvas.addGLEventListener(cube);
        canvas.addGLEventListener(pyramid);

        canvas.setSize(400, 400);

        //create the frame
        final JFrame frame = new JFrame("cube");

        //add the frame to the canvas
        frame.getContentPane().add(canvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        final FPSAnimator animator = new FPSAnimator(canvas, 300, true);

        animator.start();
    }
}
