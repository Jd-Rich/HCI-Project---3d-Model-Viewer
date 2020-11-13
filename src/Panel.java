import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Panel  extends Frame{
    private JButton changeShape = new JButton("Change Shape");
    Cube cube = new Cube();


    public Component getGUI() {
        //GL setup
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(cube);
        glcanvas.setSize(400, 400);


        Frame gui = new Frame("Frame");
        gui.setSize(600,600);
        gui.add(glcanvas);
        gui.setVisible(true);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        gui.add(buttons, BorderLayout.PAGE_END);


        //Close the window when the X is clicked
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.dispose();
            }
        });

        changeShape.setMargin(new Insets(2, 2, 2, 2));
        changeShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeShape_ActionPerformed(e);
            }
        });
        buttons.add(changeShape);
        return gui;
    }

    //methods
    public void changeShape_ActionPerformed(ActionEvent e) {

    }
}
