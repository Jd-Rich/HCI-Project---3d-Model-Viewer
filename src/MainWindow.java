

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;  // for drawing GLUT objects (such as the teapot)


/**
 * A template for a basic JOGL application with support for animation, and for
 * keyboard and mouse event handling, and for a menu.  To enable the support,
 * uncomment the appropriate lines in main(), in the constructor, and in the
 * init() method.  See all the lines that are marked with "TODO".
 *
 * See the JOGL documentation at http://jogamp.org/jogl/www/
 * Note that this program is based on JOGL 2.3, which has some differences
 * from earlier versions; in particular, some of the package names have changed.
 */
public class MainWindow extends JPanel implements
        KeyListener, MouseListener, MouseMotionListener, ActionListener {

    public static void main(String[] args) {
        JFrame window = new JFrame("3D Model Viewer");
        MainWindow panel = new MainWindow();
        window.setContentPane(panel);
        /* TODO: If you want to have a menu, comment out the following line. */
        window.setJMenuBar(panel.createMenuBar());
        window.pack();
        window.setPreferredSize(new Dimension(600,600));
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }

    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();

    private GLJPanel display;
    private JPanel buttonPanel;
    private JPanel sliderPanel;

    private Timer animationTimer;

    private int frameNumber = 0;  // The current frame number for an animation.

    private GLUT glut = new GLUT();  // TODO: For drawing GLUT objects, otherwise, not needed.

    public MainWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);

        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(600,600) );  // TODO: set display size here
        //display.addGLEventListener(cube);
        setLayout(new BorderLayout());
        add(display,BorderLayout.WEST);


        // TODO:  Other components could be added to the main panel.
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        JPanel commandPanel = new JPanel(new BorderLayout());
        commandPanel.add(buttonPanel, BorderLayout.NORTH);

        //add(buttonPanel, BorderLayout.SOUTH);

        sliderPanel = new JPanel(new BorderLayout());
        JSlider scaleSlider = new JSlider(0, 5, 1);
        JLabel scaleSliderLabel = new JLabel("     Scale Model ");
        JLabel spacer = new JLabel(" " );
        sliderPanel.add(spacer, BorderLayout.SOUTH);
        sliderPanel.add(scaleSliderLabel, BorderLayout.WEST);
        sliderPanel.add(scaleSlider, BorderLayout.CENTER);
        commandPanel.add(sliderPanel, BorderLayout.SOUTH);

        add(commandPanel, BorderLayout.SOUTH);

        getShapeButtons(display, buttonPanel);

        // TODO:  Uncomment the next two lines to enable keyboard event handling
        //display.requestFocusInWindow();
        display.addKeyListener(this);

        // TODO:  Uncomment the next one or two lines to enable mouse event handling
        display.addMouseListener(this);
        display.addMouseMotionListener(this);

        //TODO:  Uncomment the following line to start the animation
        startAnimation();

    }

    public void getShapeButtons(GLJPanel display, JPanel buttonPanel) {
        JButton selectCube = new JButton(new AbstractAction("Cube") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.repaint();
                display.addGLEventListener(cube);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(selectCube);

        JButton selectPyramid = new JButton(new AbstractAction("Pyramid") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.repaint();
                display.addGLEventListener(pyramid);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(selectPyramid);


    }

    // ------------ Support for a menu -----------------------

    public JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        MenuHandler menuHandler = new MenuHandler(); // An object to respond to menu commands.

        JMenu menu = new JMenu("Menu"); // Create a menu and add it to the menu bar
        menubar.add(menu);

        JMenuItem item = new JMenuItem("Quit");  // Create a menu command.
        item.addActionListener(menuHandler);  // Set up handling for this command.
        menu.add(item);  // Add the command to the menu.

        // TODO:  Add additional menu commands and menus.

        return menubar;
    }

    /**
     * A class to define the ActionListener object that will respond to menu commands.
     */
    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String command = evt.getActionCommand();  // The text of the command.
            if (command.equals("Quit")) {
                System.exit(0);
            }
            // TODO: Implement any additional menu commands.
        }
    }

// --------------------------- animation support ---------------------------

    /* You can call startAnimation() to run an animation.  A frame will be drawn every
     * 30 milliseconds (can be changed in the call to glutTimerFunc.  The global frameNumber
     * variable will be incremented for each frame.  Call pauseAnimation() to stop animating.
     */

    private boolean animating;  // True if animation is running.  Do not set directly.
    // This is set by startAnimation() and pauseAnimation().

    private void updateFrame() {
        frameNumber++;
        // TODO:  add any other updating required for the next frame.
    }

    public void startAnimation() {
        if ( ! animating ) {
            if (animationTimer == null) {
                animationTimer = new Timer(30, this);
            }
            animationTimer.start();
            animating = true;
        }
    }

    public void pauseAnimation() {
        if (animating) {
            animationTimer.stop();
            animating = false;
        }
    }

    public void actionPerformed(ActionEvent evt) {
        updateFrame();
        display.repaint();
    }


    // ------------ Support for keyboard handling  ------------

    /**
     * Called when the user presses any key on the keyboard, including
     * special keys like the arrow keys, the function keys, and the shift key.
     * Note that the value of key will be one of the constants from
     * the KeyEvent class that identify keys such as KeyEvent.VK_LEFT,
     * KeyEvent.VK_RIGHT, KeyEvent.VK_UP, and KeyEvent.VK_DOWN for the arrow
     * keys, KeyEvent.VK_SHIFT for the shift key, and KeyEvent.VK_F1 for a
     * function key.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        // TODO:  Add code to respond to key presses.
        if (key == KeyEvent.VK_LEFT) { cube.setRotateY(cube.getRotateY() - 15); }
        else if (key == KeyEvent.VK_RIGHT) { cube.setRotateY(cube.getRotateY() + 15); }
        else if (key == KeyEvent.VK_DOWN) { cube.setRotateX(cube.getRotateX() + 15); }
        else if (key == KeyEvent.VK_UP) { cube.setRotateX(cube.getRotateX() - 15); }
        else if (key == KeyEvent.VK_HOME) {
            cube.setRotateY(0);
            cube.setRotateX(0);
        }
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user types a character.  This function is called in
     * addition to one or more calls to keyPressed and keyTyped. Note that ch is an
     * actual character such as 'A' or '@'.
     */
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();  // Which character was typed.
        // TODO:  Add code to respond to the character being typed.
        if (ch == ' ') {
            if (animationTimer != null && animationTimer.isRunning())
                pauseAnimation();
            else
                startAnimation();
        }
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user releases any key.
     */
    public void keyReleased(KeyEvent e) {
    }





    // ---------------------- support for mouse events ----------------------

    private boolean dragging;  // is a drag operation in progress?

    private int startX, startY;  // starting location of mouse during drag
    private int prevX, prevY;    // previous location of mouse during drag

    /**
     * Called when the user presses a mouse button on the display.
     */
    public void mousePressed(MouseEvent evt) {
        if (dragging) {
            return;  // don't start a new drag while one is already in progress
        }
        int x = evt.getX();  // mouse location in pixel coordinates.
        int y = evt.getY();
        // TODO: respond to mouse click at (x,y)
        dragging = true;  // might not always be correct!
        prevX = startX = x;
        prevY = startY = y;
        display.repaint();    //  only needed if display should change
    }

    /**
     * Called when the user releases a mouse button after pressing it on the display.
     */
    public void mouseReleased(MouseEvent evt) {
        if (! dragging) {
            return;
        }
        dragging = false;
        // TODO:  finish drag (generally nothing to do here)
    }

    /**
     * Called during a drag operation when the user drags the mouse on the display/
     */
    public void mouseDragged(MouseEvent evt) {
        if (! dragging) {
            return;
        }
        int x = evt.getX();  // mouse location in pixel coordinates.
        int y = evt.getY();
        // TODO:  respond to mouse drag to new point (x,y)
        prevX = x;
        prevY = y;
        display.repaint();
    }

    public void mouseMoved(MouseEvent evt) { }    // Other methods required for MouseListener, MouseMotionListener.
    public void mouseClicked(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }



}