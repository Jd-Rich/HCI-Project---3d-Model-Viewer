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

            }
        };
        SwingUtilities.invokeLater(r);

    }
}

