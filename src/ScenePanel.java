import java.awt.Color;
import java.awt.event.*;
import javax.swing.Timer;
import java.lang.reflect.*;
import java.util.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.sun.xml.ws.util.StringUtils;

public class ScenePanel extends GLJPanel {
    // Constants
    private final static int TIMER_DELAY = 2000;
    private final static int DEFAULT_COUNTER = 1;
    private final static double ROTATE_X = 45.0;
    private final static double ROTATE_Y = 15.0;
    private final static double ROTATE_Z = 0.0;
    private final static double TRANSLATE_X = 0.0;
    private final static double TRANSLATE_Y = 0.0;
    private final static double TRANSLATE_Z = 0.0;
    private final static double SCALE = 1.5;
    private final static double ROTATE_BY = 15.0;
    private final static double TRANSLATE_BY = 0.1;
    private final static double SCALE_BY = 0.1;

    private final static Color SKYBLUE = new Color(0,191,255);
    private final static Color MUSTARD = new Color(124,124,64);
    private final static Color VIOLET = new Color(238,130,238);
    private final static Color SLATEGRAY = new Color(112,128,144);
    private final static Color SILVER = new Color(192,192,192);
    private final static Color CHOCOLATE = new Color(210,105,30);
    private final static Color HOTPINK = new Color(255,20,147);
    private final static Color INDIGO = new Color(75,0,130);
    private final static Color LIME = new Color(0,255,0);
    private final static Color FOREST = new Color(34,139,34);
    private final static Color GOLD = new Color(255,215,0);
    private final static Color RED = new Color(255,0,0);
    private final static Color ORANGE = new Color(255,165,0);
    private final static Color YELLOW = new Color(255,255,0);
    private final static Color GREEN = new Color(0,128,0);
    private final static Color TEAL = new Color(0,128,128);
    private final static Color BLUE = new Color(0,0,255);
    private final static Color WHITE = new Color(255,255,255);
    private final static Color BLACK = new Color(0,0,0);

    private Application parent;
    private Timer animationTimer;
    private int counter;
    private boolean isPlaying;
    private double scale;
    private double rotateX;
    private double rotateY;
    private double rotateZ;
    private double translateX;
    private double translateY;
    private double translateZ;

    protected ScenePanel(Application parent) {
        super (new GLCapabilities(null));

        this.addGLEventListener(new ScenePanel.SceneGLEventListener());
        this.addKeyListener(new ScenePanel.SceneKeyListener());

        this.setApplication(parent);
        this.setAnimationTimer(new Timer(ScenePanel.TIMER_DELAY, new ScenePanel.TimerListener()));
        this.setCounter(ScenePanel.DEFAULT_COUNTER);
        this.setIsPlaying(false);
        this.setDefaultTransformation();
    }

    private void setXRotation(double rotateX) { this.rotateX = rotateX; }

    private void setYRotation(double rotateY) { this.rotateY = rotateY; }

    private void setZRotation(double rotateZ) { this.rotateZ = rotateZ; }

    private void setXTranslation(double translateX) { this.translateX = translateX; }

    private void setYTranslation(double translateY) { this.translateY = translateY; }

    private void setZTranslation(double translateZ) {this.translateZ = translateZ; }

    private void setScale(double scale) { this.scale = scale; }

    private void setApplication(Application parent) { this.parent = parent; }

    private void setAnimationTimer(Timer animationTimer) { this.animationTimer = animationTimer; }

    private void setCounter(int counter) { this.counter = counter; }

    private void setIsPlaying(boolean isPlaying) { this.isPlaying = isPlaying; }

    private double getXRotation() { return this.rotateX; }

    private double getYRotation() { return this.rotateY; }

    private double getZRotation() { return this.rotateZ; }

    private double getXTranslation() { return this.translateX; }

    private double getYTranslation() { return this.translateY; }

    private double getZTranslation() { return this.translateZ; }

    private double getScale() { return this.scale; }

    private Application getApplication() { return this.parent; }

    private Timer getAnimationTimer() { return this.animationTimer; }

    private int getCounter() { return this.counter; }

    private boolean getIsPlaying() { return this.isPlaying; }

    private void addStatus(String message) { this.getApplication().addStatus(message); }

    private void setDefaultTransformation() {
        this.setXRotation(ScenePanel.ROTATE_X);
        this.setYRotation(ScenePanel.ROTATE_Y);
        this.setZRotation(ScenePanel.ROTATE_Z);
        this.setXTranslation(ScenePanel.TRANSLATE_X);
        this.setYTranslation(ScenePanel.TRANSLATE_Y);
        this.setZTranslation(ScenePanel.TRANSLATE_Z);
        this.setScale(ScenePanel.SCALE);
    }

    protected void resetScene() {
        if (!this.getIsPlaying())
            this.addStatus("The scene has been reset.");

        this.setDefaultTransformation();
        this.repaint();
    }

    protected void resetScene(boolean isPlaying) {
        this.setIsPlaying(isPlaying);
        this.resetScene();
    }

    protected void toggleAnimation(String methodName) {
        try {
            boolean isStopped;
            String capitalizedMethodName;
            Method timerMethod;

            isStopped = methodName.equals("stop");
            capitalizedMethodName = StringUtils.capitalize(methodName);

            timerMethod = Timer.class.getDeclaredMethod(methodName);

            this.addStatus(capitalizedMethodName + ((isStopped) ? "p" : "") + "ing animation");

            timerMethod.invoke(this.getAnimationTimer());

            if (isStopped)
                this.setCounter(ScenePanel.DEFAULT_COUNTER);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            this.addStatus("An error has occurred:\n" + exception);
        }
    }

    private void runAnimation() {
        switch (this.getCounter()) {
            case 1:
                this.performTransformation("YRotation", -ScenePanel.ROTATE_BY * 3.0);
                break;
            case 2:
                this.performTransformation("XRotation", ScenePanel.ROTATE_BY * 2.0);
                break;
            case 3:
                this.performTransformation("Scale", ScenePanel.SCALE_BY * 2.0);
                break;
            case 4:
                this.performTransformation("YRotation", ScenePanel.ROTATE_BY * 5.0);
                break;
            case 5:
                this.performTransformation("ZTranslation", -ScenePanel.TRANSLATE_BY * 2.0);
                break;
            case 6:
                this.performTransformation("Scale", -ScenePanel.SCALE_BY * 5.0);
                break;
            default:
                this.addStatus("Resetting the animation video.");
                this.resetScene();
                this.setCounter(0);
                break;
        }

        this.repaint();
        this.setCounter(this.getCounter() + 1);
    }

    private void performTransformation(String methodSuffix, double amount) {
        try {
            Method setMethod;
            Method getMethod;
            double currentGetValue;
            double newSetValue;
            String status;

            setMethod = ScenePanel.class.getDeclaredMethod("set" + methodSuffix, double.class);
            getMethod = ScenePanel.class.getDeclaredMethod("get" + methodSuffix);

            currentGetValue = (double) getMethod.invoke(this);
            newSetValue = currentGetValue + amount;

            status = methodSuffix.replaceAll("(.)([A-Z])", "$1 $2");
            this.addStatus(status + "by " + amount);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            this.addStatus("An error has occurred:\n" + exception);
        }
    }

    private final class SceneGLEventListener implements GLEventListener {
        private GL2 gl2;
        private ArrayList<SceneObject> sceneObjectList;

        private SceneGLEventListener() {
            ArrayList<SceneObject> sceneObjectList;

            this.setSceneObjectList(new ArrayList<>());
            sceneObjectList = this.getSceneObjectList();

            //sceneObjectList.add(new Floor(ScenePanel.SLATEGRAY,   0.5,    0.0, 0.0,  0.0));
            sceneObjectList.add(new Cube(ScenePanel.SKYBLUE, 0.125,  0.0, 1.4,  0.0));
            //sceneObjectList.add(new FiveSidedPyramid(ScenePanel.FOREST,  0.125,  2.5, 1.4,  0.0));
            //sceneObjectList.add(new HexagonalPrism(ScenePanel.INDIGO,   0.125, -2.5, 1.4,  0.0));
            //sceneObjectList.add(new TriangularPrism(ScenePanel.CHOCOLATE, 0.125,  0.0, 1.4, -2.5));
            //sceneObjectList.add(new TenSidedPolygon(ScenePanel.TEAL,   0.125,  0.0, 1.4,  2.5));
            //sceneObjectList.add(new Star(ScenePanel.GOLD,   0.125,  0.0, 4.4,  0.0));
        }

        private void setGL2(GL2 gl2) { this.gl2 = gl2; }

        private void setSceneObjectList(ArrayList<SceneObject> sceneObjectList) { this.sceneObjectList = sceneObjectList; }

        protected GL2 getGL2() { return this.gl2; }

        protected ArrayList<SceneObject> getSceneObjectList() { return this.sceneObjectList; }

        @Override
        public void init(GLAutoDrawable drawable) {
            double aspect;
            final GL2 newGL2;

            aspect = 4.0 / 3.0;
            newGL2 = drawable.getGL().getGL2();
            this.setGL2(newGL2);

            newGL2.glMatrixMode(GL2.GL_PROJECTION);
            newGL2.glOrtho(-aspect, aspect, -1, 1, -10, 100);
            newGL2.glMatrixMode(GL2.GL_MODELVIEW);
            newGL2.glShadeModel(GL2.GL_SMOOTH);
            newGL2.glClearColor(0, 0, 0, 0);
            newGL2.glClearDepth(1.0);
            newGL2.glEnable(GL2.GL_DEPTH_TEST);
            newGL2.glDepthFunc(GL2.GL_LEQUAL);
            newGL2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        }

        @Override
        public void display(GLAutoDrawable drawable) {
            final GL2 tempGL2;
            double tempScale;

            tempGL2 = this.getGL2();
            tempScale = ScenePanel.this.getScale();

            tempGL2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            tempGL2.glLoadIdentity();

            tempGL2.glRotated(ScenePanel.this.getXRotation(), 1, 0, 0);
            tempGL2.glRotated(ScenePanel.this.getYRotation(), 0, 1, 0);
            tempGL2.glRotated(ScenePanel.this.getZRotation(), 0, 0, 1);

            tempGL2.glScaled(tempScale, tempScale, tempScale);

            tempGL2.glTranslated(ScenePanel.this.getXTranslation(), ScenePanel.this.getYTranslation(), ScenePanel.this.getZTranslation());

            this.getSceneObjectList().forEach((SceneObject sceneObject) -> {
                sceneObject.constructObject(tempGL2);
            });
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {}

        @Override
        public void reshape(GLAutoDrawable drawable, int rs1, int rs2, int rs3, int rs4) {}
    }

    private final class SceneKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent event) {
            if (ScenePanel.this.getIsPlaying()) {
                ScenePanel.this.addStatus("Keyboard is disabled during animation of objects.");
                return;
            }

            switch(event.getKeyCode()) {

                case KeyEvent.VK_RIGHT:
                    ScenePanel.this.performTransformation("XRotation", ScenePanel.ROTATE_BY);
                    break;

                case KeyEvent.VK_LEFT:
                    ScenePanel.this.performTransformation("XRotation", -ScenePanel.ROTATE_BY);
                    break;

                case KeyEvent.VK_UP:
                    ScenePanel.this.performTransformation("YRotation", ScenePanel.ROTATE_BY);
                    break;

                case KeyEvent.VK_DOWN:
                    ScenePanel.this.performTransformation("YRotation", -ScenePanel.ROTATE_BY);
                    break;

                case KeyEvent.VK_PAGE_UP:
                    ScenePanel.this.performTransformation("ZRotation", ScenePanel.ROTATE_BY);
                    break;

                case KeyEvent.VK_PAGE_DOWN:
                    ScenePanel.this.performTransformation("ZRotation", -ScenePanel.ROTATE_BY);
                    break;

                case KeyEvent.VK_Q:
                    ScenePanel.this.performTransformation("XTranslation", ScenePanel.TRANSLATE_BY);
                    break;

                case KeyEvent.VK_W:
                    ScenePanel.this.performTransformation("XTranslation", -ScenePanel.TRANSLATE_BY);
                    break;

                case KeyEvent.VK_A:
                    ScenePanel.this.performTransformation("YTranslation", ScenePanel.TRANSLATE_BY);
                    break;

                case KeyEvent.VK_S:
                    ScenePanel.this.performTransformation("YTranslation", -ScenePanel.TRANSLATE_BY);
                    break;

                case KeyEvent.VK_Z:
                    ScenePanel.this.performTransformation("ZTranslation", ScenePanel.TRANSLATE_BY);
                    break;

                case KeyEvent.VK_X:
                    ScenePanel.this.performTransformation("ZTranslation", -ScenePanel.TRANSLATE_BY);
                    break;

                case KeyEvent.VK_ADD:
                    ScenePanel.this.performTransformation("Scale", ScenePanel.SCALE_BY);
                    break;

                case KeyEvent.VK_MINUS:
                    ScenePanel.this.performTransformation("Scale", -ScenePanel.SCALE_BY);
                    break;

                default:
                    ScenePanel.this.addStatus("An error has occurred:\n" + KeyEvent.getKeyText(event.getKeyCode())
                            + " is not a recognized command. Press 'Help' to view list of accepted commands.");
                    break;
            }

            ScenePanel.this.repaint();
        }

        @Override
        public void keyReleased(KeyEvent event) {}

        @Override
        public void keyTyped(KeyEvent event) {}
    }

    private final class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            ScenePanel.this.runAnimation();
        }
    }
}