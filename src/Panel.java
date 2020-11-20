import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Panel extends Frame implements AWTEventListener {
    //Shapes
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();

    //GL setup for the class
    GLProfile profile = GLProfile.get(GLProfile.GL2);
    GLCapabilities capabilities = new GLCapabilities(profile);
    GLCanvas glcanvas = new GLCanvas(capabilities);

    //The GUI
    Frame gui = new Frame("Frame");

    //Constructor for keyboard input
    public Panel() {
        this.getToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
    }

    public void getGUI() {
        //Add the eventlistener passing cube for the defualt shape then
        //set the size for the canvas.
        glcanvas.addGLEventListener(cube);
        glcanvas.setSize(400, 400);

        //Set the size for the Frame then add the glcanvas to it
        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);

        //Create a button section on the frame
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        gui.add(buttons, BorderLayout.PAGE_END);

        //Close the window when the X is clicked
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.dispose();
            }
        });

        //When the Pyramid button is clicked, the object changes to the pyramid
       JButton changeShapeToPyramid = new JButton(new AbstractAction("Pyramid") {
           @Override
           public void actionPerformed(ActionEvent e) {
               changeShapeToPyramid_ActionPerformed(e);
           }
       });
       buttons.add(changeShapeToPyramid);

        //When the Cube button is clicked, the object changes to the Cube
       JButton changeShapeToCube = new JButton(new AbstractAction("Cube") {
           @Override
           public void actionPerformed(ActionEvent e) {
               changeShapeToCube_ActionPerformed(e);
           }
       });
       buttons.add(changeShapeToCube);

        //Animator for the objects shown
        //final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        //animator.start();

    }

    //methods
    public void changeShapeToPyramid_ActionPerformed(ActionEvent e) {
        glcanvas.addGLEventListener(pyramid);
        glcanvas.setSize(400, 400);

        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);
    }

    public void changeShapeToCube_ActionPerformed(ActionEvent e) {
        glcanvas.addGLEventListener(cube);
        glcanvas.setSize(400, 400);

        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);
    }

    //Keybinds
    @Override
    public void eventDispatched(AWTEvent event) {
        if(event instanceof KeyEvent) {
            KeyEvent key = (KeyEvent)event;
            if(key.getID() == KeyEvent.KEY_PRESSED) {
                if(key.getKeyChar() == 'w') {
                    cube.reshape();
                }
                key.consume();
            }
        }
    }
}
