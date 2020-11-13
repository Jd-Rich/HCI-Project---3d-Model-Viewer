import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Panel  extends Frame{
    //Boolean variables for which shape to change to
    boolean changeToCube = false;
    boolean changeToPyramid = false;

    //Shapes
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();

    /*
    //Buttons for the frame
    private JButton changeShapeToPyramid = new JButton("Pyramid");
    private JButton changeShapeToCube = new JButton("Cube");
    */
    //GL setup
    GLProfile profile = GLProfile.get(GLProfile.GL2);
    GLCapabilities capabilities = new GLCapabilities(profile);
    GLCanvas glcanvas = new GLCanvas(capabilities);

    //The GUI
    Frame gui = new Frame("Frame");

    public void getGUI() {
        glcanvas.addGLEventListener(cube);
        glcanvas.setSize(400, 400);

        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        gui.add(buttons, BorderLayout.PAGE_END);

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();

        //Close the window when the X is clicked
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.dispose();
            }
        });


       JButton changeShapeToPyramid = new JButton(new AbstractAction("Pyramid") {
           @Override
           public void actionPerformed(ActionEvent e) {
               changeShapeToPyramid_ActionPerformed(e);
           }
       });
       buttons.add(changeShapeToPyramid);


       JButton changeShapeToCube = new JButton(new AbstractAction("Cube") {
           @Override
           public void actionPerformed(ActionEvent e) {
               changeShapeToCube_ActionPerformed(e);
           }
       });
       buttons.add(changeShapeToCube);

    }

    //methods
    public void changeShapeToPyramid_ActionPerformed(ActionEvent e) {
        glcanvas.addGLEventListener(pyramid);
        glcanvas.setSize(400, 400);

        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        gui.add(buttons, BorderLayout.PAGE_END);
    }

    public void changeShapeToCube_ActionPerformed(ActionEvent e) {
        glcanvas.addGLEventListener(cube);
        glcanvas.setSize(400, 400);

        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        gui.add(buttons, BorderLayout.PAGE_END);
    }
}
