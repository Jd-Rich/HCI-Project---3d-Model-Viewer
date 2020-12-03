import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;  // for drawing GLUT objects (such as the teapot)



public class MainWindow extends JPanel implements
        ItemListener, KeyListener, MouseListener, MouseMotionListener, ActionListener, ChangeListener {



    public static void main(String[] args) {
        JFrame window = new JFrame("3D Model Viewer");
        MainWindow panel = new MainWindow();
        window.setContentPane(panel);

        window.setJMenuBar(panel.createMenuBar());
        window.pack();
        window.setPreferredSize(new Dimension(800,800));
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }

    //Shape objects
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();
    CubeLighting cubeLighting = new CubeLighting();
    PyramidLighting pyramidLighting = new PyramidLighting();
    Paddle paddle = new Paddle();
    Star star = new Star();

    private GLJPanel display;
    private JPanel buttonPanel;
    private JPanel sliderPanel;

    private JSlider scaleSlider;
    private JToggleButton playAnimation;
    private JButton newColors;

    private Timer animationTimer;

    private int frameNumber = 0;  // The current frame number for an animation.

    public float randFloat() {
        Random rand = new Random();

        return rand.nextFloat();
    }

    public MainWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);

        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(600,600) );
        display.setBorder(new LineBorder(Color.blue));
        setLayout(new BorderLayout());
        add(display,BorderLayout.WEST);

        //display.addGLEventListener(star);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        JPanel commandPanel = new JPanel(new BorderLayout());
        sliderPanel = new JPanel(new BorderLayout());

        commandPanel.add(buttonPanel, BorderLayout.NORTH);


        scaleSlider = new JSlider(0, 100, 25);
        scaleSlider.addChangeListener(this);
        scaleSlider.setMajorTickSpacing(10);
        scaleSlider.setMinorTickSpacing(1);
        scaleSlider.setPaintTicks(true);

        JLabel scaleSliderLabel = new JLabel("     Scale Model ");
        JLabel spacer = new JLabel(" " );
        sliderPanel.add(spacer, BorderLayout.SOUTH);
        sliderPanel.add(scaleSliderLabel, BorderLayout.WEST);
        sliderPanel.add(scaleSlider, BorderLayout.CENTER);
        commandPanel.add(sliderPanel, BorderLayout.SOUTH);

        playAnimation = new JToggleButton();
        playAnimation.setText("Playing");
        playAnimation.setBackground(Color.GREEN);
        playAnimation.setForeground(Color.WHITE);
        playAnimation.addItemListener(this);

        JPanel miscPanel = new JPanel(new BorderLayout());
        miscPanel.add(playAnimation, BorderLayout.NORTH);

        newColors = new JButton(new AbstractAction("Change Color") {
            @Override
            public void actionPerformed(ActionEvent e) {
                cube.setRedColor(randFloat());
                cube.setGreenColor(randFloat());
                cube.setBlueColor(randFloat());
                pyramid.setRedColor(randFloat());
                pyramid.setGreenColor(randFloat());
                pyramid.setBlueColor(randFloat());
                star.setRedColor(randFloat());
                star.setGreenColor(randFloat());
                star.setBlueColor(randFloat());

            }
        });

        miscPanel.add(newColors, BorderLayout.EAST);

        add(miscPanel, BorderLayout.EAST);

        add(commandPanel, BorderLayout.SOUTH);

        getShapeButtons(display, buttonPanel);

        display.requestFocusInWindow();
        display.addKeyListener(this);
        display.addMouseListener(this);
        display.addMouseMotionListener(this);

        startAnimation();

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (playAnimation.isSelected()) {
            pauseAnimation();
        }
        else{
            startAnimation();}
    }
    public void getShapeButtons(GLJPanel display, JPanel buttonPanel) {
        JButton selectCube = new JButton(new AbstractAction("Cube") {
            @Override
            public void actionPerformed(ActionEvent e) {
                cube = new Cube();
                newColors.setVisible(true);

                display.repaint();
                display.addGLEventListener(cube);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(selectCube);

        JButton selectStar = new JButton(new AbstractAction("Star") {
            @Override
            public void actionPerformed(ActionEvent e) {
                star = new Star();
                newColors.setVisible(true);

                display.repaint();
                display.addGLEventListener(star);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(selectStar);

        JButton selectPyramid = new JButton(new AbstractAction("Pyramid") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pyramid = new Pyramid();
                newColors.setVisible(true);

                display.repaint();
                display.addGLEventListener(pyramid);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(selectPyramid);

        JButton addPaddle = new JButton(new AbstractAction("Paddle") {
            @Override
            public void actionPerformed(ActionEvent e) {
                paddle = new Paddle();
                newColors.setVisible(false);

                display.repaint();
                display.addGLEventListener(paddle);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(addPaddle);

        JButton addCubeLighting = new JButton(new AbstractAction("Cube Lighting") {
            @Override
            public void actionPerformed(ActionEvent e) {

                cubeLighting = new CubeLighting();
                newColors.setVisible(false);

                display.repaint();
                display.addGLEventListener(cubeLighting);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(addCubeLighting);

        JButton addPyramidLighting = new JButton(new AbstractAction("Pyramid Lighting") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pyramidLighting = new PyramidLighting();
                newColors.setVisible(false);
                display.repaint();
                display.addGLEventListener(pyramidLighting);
                display.requestFocusInWindow();
            }
        }); buttonPanel.add(addPyramidLighting);

    }

    // ------------ Support for a menu -----------------------

    public JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        MenuHandler menuHandler = new MenuHandler(); // An object to respond to menu commands.

        JMenu menu = new JMenu("Menu"); // Create a menu and add it to the menu bar
        menubar.add(menu);

        // Scale menu option
        JMenu scale = new JMenu("Scale");
        menubar.add(scale);
        JMenuItem scaleUp = new JMenuItem("Scale Up (H)");
        JMenuItem scaleDown = new JMenuItem("Scale Down (G)");
        scaleUp.addActionListener(menuHandler);
        scaleDown.addActionListener(menuHandler);
        scale.add(scaleUp);
        scale.add(scaleDown);

        // Rotate menu option
        JMenu rotate = new JMenu("Rotate");
        menubar.add(rotate);
        JMenuItem rotateLeft = new JMenuItem("Rotate Left (Left)");
        JMenuItem rotateRight = new JMenuItem("Rotate Right (Right)");
        JMenuItem rotateUp = new JMenuItem("Rotate Up (Up)");
        JMenuItem rotateDown = new JMenuItem("Rotate Down (Down)");
        rotateLeft.addActionListener(menuHandler);
        rotateRight.addActionListener(menuHandler);
        rotateUp.addActionListener(menuHandler);
        rotateDown.addActionListener(menuHandler);
        rotate.add(rotateLeft);
        rotate.add(rotateRight);
        rotate.add(rotateUp);
        rotate.add(rotateDown);

        JMenuItem exit = new JMenuItem("Exit");  // Create a menu command.
        exit.addActionListener(menuHandler);  // Set up handling for this command.
        menu.add(exit);  // Add the command to the menu.

        return menubar;
    }

    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String command = evt.getActionCommand();  // The text of the command.
            if (command.equals("Exit")) {
                System.exit(0);
            } else if (command.equals("Scale Up (H)")) {
                cube.setScale(cube.getScale() + 0.1f);
                pyramid.setScale(pyramid.getScale() + 0.1f);
                scaleSlider.setValue((int)(cube.getScale() * 100));
            } else if (command.equals("Scale Down (G)")) {
                cube.setScale(cube.getScale() - 0.1f);
                pyramid.setScale(pyramid.getScale() - 0.1f);
                scaleSlider.setValue((int)(cube.getScale() * 100));
            } else if (command.equals("Rotate Left (Left)")) {
                cube.setRotateY(cube.getRotateY() - 15);
                pyramid.setRotateY(pyramid.getRotateY() - 15);
            } else if (command.equals("Rotate Right (Right)")) {
                cube.setRotateY(cube.getRotateY() + 15);
                pyramid.setRotateY(pyramid.getRotateY() + 15);
            } else if (command.equals("Rotate Up (Up)")) {
                cube.setRotateX(cube.getRotateX() - 15);
                pyramid.setRotateX(pyramid.getRotateX() - 15);
            } else if (command.equals("Rotate Down (Down)")) {
                cube.setRotateX(cube.getRotateX() + 15);
                pyramid.setRotateX(pyramid.getRotateX() + 15);
            }
        }
    }

// --------------------------- animation support ---------------------------



    private boolean animating;  // True if animation is running.

    private void updateFrame() {
        frameNumber++;
    }

    public void startAnimation() {
        if ( ! animating ) {
            playAnimation.setText("Playing");
            playAnimation.setSelected(false);

            if (animationTimer == null) {
                animationTimer = new Timer(30, this);
            }
            animationTimer.start();
            animating = true;
        }
    }

    public void pauseAnimation() {
        if (animating) {
            playAnimation.setText("Paused");
            playAnimation.setSelected(true);
            animationTimer.stop();
            animating = false;
        }
    }

    public void actionPerformed(ActionEvent evt) {
        updateFrame();
        display.repaint();
    }


    // ------------ Support for keyboard handling  ------------

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        // TODO:  Add code to respond to key presses.
        if (key == KeyEvent.VK_LEFT) {
            cube.setRotateY(cube.getRotateY() - 15);
            pyramid.setRotateY(pyramid.getRotateY() - 15);
            cubeLighting.setRotateY(cubeLighting.getRotateY() - 15);
            pyramidLighting.setRotateY(pyramidLighting.getRotateY() - 15);
            paddle.setRotateY(paddle.getRotateY() - 15);
            star.setRotateY(star.getRotateY() - 15);
        }
        else if (key == KeyEvent.VK_RIGHT) {
            cube.setRotateY(cube.getRotateY() + 15);
            pyramid.setRotateY(pyramid.getRotateY() + 15);
            cubeLighting.setRotateY(cubeLighting.getRotateY() + 15);
            pyramidLighting.setRotateY(pyramidLighting.getRotateY() + 15);
            paddle.setRotateY(paddle.getRotateY() + 15);
            star.setRotateY(star.getRotateY() + 15);
        }
        else if (key == KeyEvent.VK_DOWN) {
            cube.setRotateX(cube.getRotateX() + 15);
            pyramid.setRotateX(pyramid.getRotateX() + 15);
            cubeLighting.setRotateX(cubeLighting.getRotateX() + 15);
            pyramidLighting.setRotateX(pyramidLighting.getRotateX() + 15);
            paddle.setRotateX(paddle.getRotateX() + 15);
            star.setRotateX(star.getRotateX() + 15);
        }
        else if (key == KeyEvent.VK_UP) {
            cube.setRotateX(cube.getRotateX() - 15);
            pyramid.setRotateX(pyramid.getRotateX() - 15);
            cubeLighting.setRotateX(cubeLighting.getRotateY() - 15);
            pyramidLighting.setRotateX(pyramidLighting.getRotateX() + 15);
            paddle.setRotateX(paddle.getRotateX() + 15);
            star.setRotateX(star.getRotateX() + 15);
        }
        else if (key == KeyEvent.VK_A) {
            cube.setRotateY(cube.getRotateY() - 15);
            pyramid.setRotateY(pyramid.getRotateY() - 15);
            cubeLighting.setRotateY(cubeLighting.getRotateY() - 15);
            pyramidLighting.setRotateY(pyramidLighting.getRotateY() - 15);
            paddle.setRotateY(paddle.getRotateY() - 15);
            star.setRotateY(star.getRotateY() - 15);
        }
        else if (key == KeyEvent.VK_D) {
            cube.setRotateY(cube.getRotateY() + 15);
            pyramid.setRotateY(pyramid.getRotateY() + 15);
            cubeLighting.setRotateY(cubeLighting.getRotateY() + 15);
            pyramidLighting.setRotateY(pyramidLighting.getRotateY() + 15);
            paddle.setRotateY(paddle.getRotateY() + 15);
            star.setRotateY(star.getRotateY() + 15);
        }
        else if (key == KeyEvent.VK_S) {
            cube.setRotateX(cube.getRotateX() + 15);
            pyramid.setRotateX(pyramid.getRotateX() + 15);
            cubeLighting.setRotateX(cubeLighting.getRotateX() + 15);
            pyramidLighting.setRotateX(pyramidLighting.getRotateX() + 15);
            paddle.setRotateX(paddle.getRotateX() + 15);
            star.setRotateX(star.getRotateX() + 15);
        }
        else if (key == KeyEvent.VK_W) {
            cube.setRotateX(cube.getRotateX() - 15);
            pyramid.setRotateX(pyramid.getRotateX() - 15);
            cubeLighting.setRotateX(cubeLighting.getRotateY() - 15);
            pyramidLighting.setRotateX(pyramidLighting.getRotateX() - 15);
            paddle.setRotateX(paddle.getRotateX() - 15);
            star.setRotateX(star.getRotateX() - 15);
        }
        else if (key == KeyEvent.VK_HOME) {
            cube.setRotateY(0);
            cube.setRotateX(0);
            pyramid.setRotateY(0);
            pyramid.setRotateX(0);
            cubeLighting.setRotateY(0);
            cubeLighting.setRotateX(0);
            pyramidLighting.setRotateX(0);
            pyramidLighting.setRotateY(0);
            star.setRotateX(0);
            star.setRotateY(0);
        }
        else if (key == KeyEvent.VK_G) {

            cube.setScale(cube.getScale() - 0.01f);
            pyramid.setScale(pyramid.getScale() - 0.01f);
            cubeLighting.setScale(cubeLighting.getScale() - 0.01f);
            pyramidLighting.setScale(pyramidLighting.getScale() - 0.01f);
            paddle.setScale(paddle.getScale() - 0.01f);
            star.setScale(star.getScale() - 0.01f);
            scaleSlider.setValue((int)(cube.getScale() * 100));
        }
        else if (key == KeyEvent.VK_H) {
            cube.setScale(cube.getScale() + 0.01f);
            pyramid.setScale(pyramid.getScale() + 0.01f);
            cubeLighting.setScale(cubeLighting.getScale() + 0.01f);
            pyramidLighting.setScale(pyramidLighting.getScale() + 0.01f);
            paddle.setScale(paddle.getScale() + 0.01f);
            star.setScale(star.getScale() + 0.01f);
            scaleSlider.setValue((int)(cube.getScale() * 100));
        }


        display.repaint();  // Causes the display() function to be called.
    }


    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();  // Which character was typed.
        // TODO:  Add code to respond to the character being typed.
        if (ch == ' ') {
            if (animationTimer != null && animationTimer.isRunning()) {

                pauseAnimation();
            }
            else {
                startAnimation();
            }
        }
        display.repaint();  // Causes the display() function to be called.
    }


    public void keyReleased(KeyEvent e) {
        repaint();
    }

    // Support for scale slider events
    @Override
    public void stateChanged(ChangeEvent event) {

        if (event.getSource() == scaleSlider) {

        System.out.println("SLIDER VALUE: " + scaleSlider.getValue());
        cube.setScale(scaleSlider.getValue() / 100.0f);
        pyramid.setScale(scaleSlider.getValue() / 100.0f);
        paddle.setScale(scaleSlider.getValue() / 100.0f);
        cubeLighting.setScale(scaleSlider.getValue() / 100.0f);
        pyramidLighting.setScale(scaleSlider.getValue() / 100.0f);
        star.setScale(scaleSlider.getValue() / 100.0f);
        //System.out.println(cube.getScale());
        //System.out.println(pyramid.getScale());

        }
        display.repaint();

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