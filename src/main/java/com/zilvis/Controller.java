package com.zilvis;

import com.zilvis.models.CellData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements NativeKeyListener {
    private Robot robot = new Robot();
    private int index = 0;
    private Color col;
    @FXML
    private Label colorLabel;
    @FXML
    private Label coordinatesLabel;
    @FXML
    private Circle colorCircle;
    @FXML
    private TableView<CellData> table;

    public void initialize() {
        setup();
        tableSetup();
        updateLabels();
        colorLabel.setText("Just testing");

    }

    private void updateLabels() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        coordinatesLabel.setText("x = " + getX() + " y = " + getY());
                        colorCircle.setFill(getColor());
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void tableSetup() {
        TableColumn idxCol = new TableColumn("#");
        idxCol.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn colorCol = new TableColumn("Color Code");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn xCol = new TableColumn("X");
        xCol.setCellValueFactory(new PropertyValueFactory<>("x"));

        TableColumn yCol = new TableColumn("Y");
        yCol.setCellValueFactory(new PropertyValueFactory<>("y"));

        TableColumn keyCol = new TableColumn("Key");
        keyCol.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn coloredCol = new TableColumn("Color");
        coloredCol.setCellFactory(param -> new TableCell<CellData, String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: #" + getColorCode(col));
                }
            }
        });

        table.getColumns().clear();
        table.getColumns().addAll(idxCol, colorCol, xCol, yCol, keyCol, coloredCol);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        Platform.runLater(() -> {
            CellData data = getCellData(e);
            table.getItems().add(data);
        });

        index++;
    }

    private CellData getCellData(NativeKeyEvent e) {
        int x = getX();
        int y = getY();
        col = getColor();
        String color = getColorCode(col);
        String keyCode = NativeKeyEvent.getKeyText(e.getKeyCode());
        return new CellData(index, x, y, color, keyCode);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    private int getX() {
        return (int) robot.getMouseX();
    }

    private int getY() {
        return (int) robot.getMouseY();
    }

    private Color getColor() {
        return robot.getPixelColor(getX(), getY());
    }

    private String getColorCode(Color color) {
        return color.toString().substring(2, 8);
    }

    private void setup() {
        try {
            GlobalScreen.registerNativeHook();
        } catch(NativeHookException e) {
            System.out.println(e.getMessage());
        }

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        // Change the level for all handlers attached to the default logger.
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            handlers[i].setLevel(Level.OFF);
        }

        GlobalScreen.addNativeKeyListener(this);
    }
}
