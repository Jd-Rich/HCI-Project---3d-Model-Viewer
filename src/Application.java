import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;

public class Application extends JFrame {
    // Unit testing variable
    private final static boolean DEBUG = false;

    // Window variables
    private int windowHeight;
    private int windowWidth;
    private String windowTitle;

    // GUI variables
    // Frame
    private JFrame mainFrame;
    // Panels
    private JPanel mainPanel;
    private JPanel interfacePanel;
    private JPanel scenePanel;
    private JPanel buttonPanel;
    private JPanel logPanel;
    // Buttons
    private JButton helpButton;
    private JButton resetButton;
    private JButton clearButton;
    private JToggleButton toggleButton;
    // Text Area
    private JTextArea logTextArea;
    // Scroll Pane
    private JScrollPane logScrollPane;
    // Scene Panel
    private ScenePanel scene;

    // Class to handle GUI
    protected Application() {

        super("3D Model Viewer");

        this.setWindowHeight(660);
        this.setWindowWidth(660);
        this.setWindowTitle("3D Model Viewer");

        this.constructGUI();
    }

    // Setter methods for window properties
    private void setWindowHeight(int windowHeight) { this.windowHeight = windowHeight; }
    private void setWindowWidth(int windowWidth) { this.windowWidth = windowWidth; }
    private void setWindowTitle(String windowTitle) { this.windowTitle = windowTitle; }

    // Getter methods for window properties
    protected int getWindowHeight() { return this.windowHeight; }
    protected int getWindowWidth() { return this.windowWidth; }
    protected String getWindowTitle() { return this.windowTitle; }

    // Setter and getter method for scene properties
    private void setScene(ScenePanel scene) { this.scene = scene; }
    protected ScenePanel getScene() { return this.scene; }

    // GUI Construction
    private void constructGUI() {
        // Panel properties
        this.mainPanel = new JPanel(new BorderLayout());
        this.interfacePanel = new JPanel(new BorderLayout());
        this.scenePanel = new JPanel(new GridLayout(1,1,5,5));
        this.buttonPanel = new JPanel(new GridLayout(4,1,5,5));
        this.logPanel = new JPanel(new GridLayout(1,1,5,5));

        // Scene panel properties
        this.scene = new ScenePanel(this);
        this.scene.setPreferredSize(new Dimension(640, 480));
        this.setScene(scene);

        // Panel border properties
        this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.scenePanel.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(218.7f,47.7f,25.5f)));
        this.buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        this.scenePanel.setBorder(BorderFactory.createTitledBorder("Scene"));
        this.logPanel.setBorder(BorderFactory.createTitledBorder("Status"));

        // Button properties
        this.helpButton = new JButton("Help");
        this.resetButton = new JButton("Reset");
        this.clearButton = new JButton("Clear");
        this.toggleButton = new JToggleButton("Video");

        // Status log properties
        this.logTextArea = new JTextArea();
        this.logTextArea.setEditable(false);
        this.logTextArea.setFont(new Font("Monospaced", Font.BOLD,14));
        this.logTextArea.setLineWrap(true);
        this.logScrollPane = new JScrollPane(this.logTextArea);

        // Add the scene to the scene panel
        this.scenePanel.add(this.scene);
        this.scenePanel.setPreferredSize(new Dimension(640,480));

        // Add the buttons to the button panel
        this.buttonPanel.add(this.helpButton);
        this.buttonPanel.add(this.resetButton);
        this.buttonPanel.add(this.clearButton);
        this.buttonPanel.add(this.toggleButton);

        // Add the status to the log panel
        this.logPanel.add(this.logScrollPane);

        // Add child panels to parent panel
        this.interfacePanel.add(this.buttonPanel, BorderLayout.WEST);
        this.interfacePanel.add(this.logPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.scenePanel, BorderLayout.CENTER);
        this.mainPanel.add(this.interfacePanel, BorderLayout.SOUTH);

        // Info popup button handler
        this.helpButton.addActionListener((ActionEvent event) -> {
            this.displayHelpMenu();
            this.getScene().requestFocusInWindow();
        });

        // Reset scene button handler
        this.resetButton.addActionListener((ActionEvent event) -> {
            this.getScene().resetScene();
            this.getScene().requestFocusInWindow();
        });

        // Clear status button handler
        this.clearButton.addActionListener((ActionEvent event) -> {
            this.logTextArea.setText("");
            this.getScene().requestFocusInWindow();
        });

        // Switch button for auto and user transformation
        this.toggleButton.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED)
                this.toggleButtonHandler(true);

            else if (event.getStateChange() == ItemEvent.DESELECTED)
                this.toggleButtonHandler(false);

            else
                this.scene.requestFocusInWindow();
        });

        // Display properties of the main frame of the application
        this.mainFrame = new JFrame(this.getWindowTitle());
        this.mainFrame.setContentPane(this.mainPanel);
        this.mainFrame.setSize(this.getWindowWidth(), this.getWindowHeight());
        this.mainFrame.setResizable(false);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setVisible(true);

        if (Application.DEBUG)
            this.listDimensions();

        this.scene.requestFocusInWindow();
    }

    private void listDimensions() {
        this.addStatus("Height of window frame: " + this.mainFrame.getHeight() + " pixels.");
        this.addStatus("Width of window frame: " + this.mainFrame.getWidth() + " pixels.");

        this.addStatus("Height of scene panel: " + this.scenePanel.getHeight() + " pixels.");
        this.addStatus("Width of scene panel: " + this.scenePanel.getWidth() + " pixels.");

        this.addStatus("Non-Scene Height: " + this.determineNonSceneDimension("getHeight"));
        this.addStatus("Non-Scene Width: " + this.determineNonSceneDimension("getWidth"));
    }

    private int determineNonSceneDimension(String getSide) {
        try {
            Method sideMethod;
            int result;
            int mainFrameDimension;
            int scenePanelDimension;

            sideMethod = Component.class.getDeclaredMethod(getSide);

            mainFrameDimension = (int) sideMethod.invoke(this.mainFrame);

            scenePanelDimension = (int) sideMethod.invoke(this.scenePanel);

            result = mainFrameDimension - scenePanelDimension;

            return result;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            this.addStatus("An error occurred:\n" + exception);
            return -1;
        }
    }

    // Method to display viewer data to the user
    protected void addStatus(String status) {
        this.logTextArea.append(status + "\n");

        // Display the data in console when debugging
        if (Application.DEBUG) {
            System.out.println(status);
        }
    }
    
    // Method to display the help menu as a pop-up
    private void displayHelpMenu() {
        String title;
        String helpText;

        title = "Help Menu";
        helpText =
                "<html>"
                + "<div style='font-weight:normal; margin: 2px 10px;'>"
                + "Use the keyboard to manipulate the 3D object."
                + "<br />"
                + "<ul>"
                        + "<li>x-axis rotation: Left and right arrow keys</li>"
                        + "<li>y-axis rotation: Up and down arrow keys</li>"
                        + "<li>z-axis rotation: PgUp and PgDn keys</li>"
                + "</ul>"
                + "<br />"
                + "<ul>"
                        + "<li>x-axis translation: Keys Q and W</li>"
                        + "<li>y-axis translation: Keys A and S</li>"
                        + "<li>z-axis translation: Keys Z and X</li>"
                + "</ul>"
                + "<br />"
                + "<ul>"
                        + "<li>Scale object: Plus(+) and Minus(-) keys</li>"
                + "</ul>"
                + "</div"
                + "<html>";

        // Open the help menu as a pop-up window
        JOptionPane.showMessageDialog(this.mainFrame, helpText, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void toggleButtonHandler(boolean isTransformModeAutomatic){
        this.getScene().resetScene(isTransformModeAutomatic);

        if (isTransformModeAutomatic)
            this.getScene().toggleAnimation("start");
        else
            this.getScene().toggleAnimation("stop");
    }
}
