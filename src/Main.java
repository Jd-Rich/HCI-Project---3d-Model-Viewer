import java.applet.Applet;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;

import com.jogamp.opengl.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.border.EmptyBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;


public class Main {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Panel panel = new Panel();
                panel.getGUI();

                /*
                JFrame frame = new JFrame("Try Panel");
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setLocationByPlatform(true);
                Container cp = frame.getContentPane();
                cp.setLayout(new BorderLayout(3, 3));

                Panel panel = new Panel();
                cp.add(panel.getGUI());

                frame.getContentPane().add(glcanvas);
                frame.setVisible(true);
                frame.pack();
                frame.setMinimumSize(frame.getSize());
                frame.setVisible(true);
                */
            }
        };
        SwingUtilities.invokeLater(r);

    }
}









/*
        //Instantiate shape objects
        Cube cube = new Cube();
        Pyramid pyramid = new Pyramid();

        //Create GLprofile and capabilities objects
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        //Create the canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(cube);
        //glcanvas.addGLEventListener(pyramid);
        glcanvas.setSize(400, 400);

        //create the frame
        final JFrame frame = new JFrame("3D Object Viewer");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);

        //Add a button to the frame
        JButton changeShape = new JButton();
        frame.setSize(1000, 1000);
        changeShape.setSize(200,20);
        changeShape.setVisible(true);
        changeShape.setText("Change Shape to Pyramid");
        frame.add(changeShape);


        //add the frame to the canvas
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        //Create animation of the object in the frame
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
        */



