import com.jogamp.opengl.*;
import java.awt.*;

abstract class SceneObject {

    private Color color;
    private double[][] colorArray;
    private double xTranslation;
    private double yTranslation;
    private double zTranslation;
    private double scale;

    protected SceneObject(Color color, double scale, double xTranslation, double yTranslation, double zTranslation) {
        this.setColor(color);
        this.setColorArray(this.buildColorArray());
        this.setScale(scale);
        this.setXTranslation(xTranslation);
        this.setYTranslation(yTranslation);
        this.setZTranslation(zTranslation);
    }

    private void setColor(Color color) { this.color = color; }

    private void setColorArray(double[][] colorArray) { this.colorArray = colorArray; }

    private void setScale(double scale) { this.scale = scale; }

    private void setXTranslation(double xTranslation) { this.xTranslation = xTranslation; }

    private void setYTranslation(double yTranslation) { this.yTranslation = yTranslation; }

    private void setZTranslation(double zTranslation) { this.zTranslation = zTranslation; }

    protected Color getColor() { return this.color; }

    protected double[][] getColorArray() { return this.colorArray; }

    protected double getScale() { return this.scale; }

    protected double getXTranslation() { return this.xTranslation; }

    protected double getYTranslation() { return this.yTranslation; }

    protected double getZTranslation() { return this.zTranslation; }

    protected void constructObject(GL2 gl2) {
        double tempScale;
        double[][] tempColorArray;
        int[][] tempFaces;
        double[] arrayColorBlack;

        tempScale = this.getScale();
        tempColorArray = this.getColorArray();
        tempFaces = this.getFaces();
        arrayColorBlack = this.convertColorToDoubleArray(Color.BLACK);

        gl2.glPushMatrix();

        gl2.glScaled(tempScale, tempScale, tempScale);

        gl2.glTranslated(this.getXTranslation(), this.getYTranslation(),this.getZTranslation());

        for(int i = 0; i < tempFaces.length; i++) {
            gl2.glPushMatrix();

            this.drawShape(gl2, tempFaces, tempColorArray[i], GL2.GL_TRIANGLE_FAN, i);

            this.drawShape(gl2, tempFaces, arrayColorBlack, GL2.GL_LINE_LOOP, i);

            gl2.glPopMatrix();
        }

        gl2.glPopMatrix();
    }

    private void drawShape(GL2 gl2, int[][] faces, double[] array, int immediateMode, int counter) {
        int vertex;
        double[][] tempVertices = this.getVertices();

        gl2.glColor3d(array[0], array[1], array[2]);

        gl2.glBegin(immediateMode);

        for(int i = 0; i < faces[counter].length; i++) {
            vertex = faces[counter][i];
            gl2.glVertex3dv(tempVertices[vertex], 0);
        }

        gl2.glEnd();
    }

    private double[][] buildColorArray() {
        int numberFaces = this.getFaces().length;
        double[][] array = new double[numberFaces][3];

        Color base = this.getColor();
        Color brighter = base.brighter();
        Color darker = base.darker();

        double[] baseArray = this.convertColorToDoubleArray(base);
        double[] brighterArray = this.convertColorToDoubleArray(brighter);
        double[] darkerArray = this.convertColorToDoubleArray(darker);

        for(int i = 0; i < numberFaces; i++) {
            if (i == 0) array[i] = brighterArray;
            else if ((i + 1) == numberFaces) array[i] = darkerArray;
            else array[i] = baseArray;
        }
        return array;
    }

    private double[] convertColorToDoubleArray(Color color) {
        return new double[] {color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0};
    }

    abstract protected double[][] getVertices();
    abstract protected int[][] getFaces();

}
