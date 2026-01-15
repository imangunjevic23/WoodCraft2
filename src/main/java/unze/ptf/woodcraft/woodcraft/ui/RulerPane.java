package unze.ptf.woodcraft.woodcraft.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RulerPane extends Canvas {
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private static final double MAX_CANVAS_SIZE = 8192;

    private Orientation orientation = Orientation.HORIZONTAL;
    private double scale = 10.0;
    private boolean clamping;

    public RulerPane() {
        widthProperty().addListener((obs, oldVal, newVal) -> {
            clampSize();
            draw();
        });
        heightProperty().addListener((obs, oldVal, newVal) -> {
            clampSize();
            draw();
        });
    }

    public RulerPane(Orientation orientation) {
        this();
        this.orientation = orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        draw();
    }

    public void setScale(double scale) {
        this.scale = scale;
        draw();
    }

    public double getScale() {
        return scale;
    }

    private void clampSize() {
        if (clamping) {
            return;
        }
        clamping = true;
        try {
            double width = getWidth();
            double height = getHeight();
            if (width > MAX_CANVAS_SIZE) {
                setWidth(MAX_CANVAS_SIZE);
            } else if (width < 0) {
                setWidth(0);
            }
            if (height > MAX_CANVAS_SIZE) {
                setHeight(MAX_CANVAS_SIZE);
            } else if (height < 0) {
                setHeight(0);
            }
        } finally {
            clamping = false;
        }
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        double width = getWidth();
        double height = getHeight();
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.GRAY);
        gc.setFill(Color.DIMGRAY);
        gc.setLineWidth(1);
        if (orientation == Orientation.HORIZONTAL) {
            for (int cm = 0; cm <= width / scale; cm++) {
                double x = cm * scale;
                double tick = cm % 10 == 0 ? 12 : 6;
                gc.strokeLine(x, height, x, height - tick);
                if (cm % 10 == 0) {
                    gc.fillText(Integer.toString(cm), x + 2, 12);
                }
            }
        } else {
            for (int cm = 0; cm <= height / scale; cm++) {
                double y = cm * scale;
                double tick = cm % 10 == 0 ? 12 : 6;
                gc.strokeLine(width, y, width - tick, y);
                if (cm % 10 == 0) {
                    gc.fillText(Integer.toString(cm), 2, y + 10);
                }
            }
        }
    }
}
