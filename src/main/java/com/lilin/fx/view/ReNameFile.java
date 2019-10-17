package com.lilin.fx.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ReNameFile extends Application {

    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage stage) {
        stage.setTitle("JavaFX Welcome");
        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Open a Picture...");
        final Button openMultipleButton = new Button("Open Pictures...");

        //选择单个文件,
        openButton.setOnAction(
                (final ActionEvent e) -> {
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        openFile(file);
                    }
                });
        //选择多个文件
        openMultipleButton.setOnAction(
                (final ActionEvent e) -> {
                    configureFileChooser(fileChooser);
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(stage);
                    if (list != null) {
                        list.stream().forEach((file) -> {
                            openFile(file);
                        });
                    }
                });

        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setHgap(5);//设置控件之间的垂直间隔距离
        pane.setVgap(5);//设置控件之间的水平间隔距离
        Label lbName = new Label("Please input a name:");
        TextField tfName = new TextField();
        Label lbPassword = new Label("Please input a password:");
        TextField tfPassword = new TextField();
        Button okbtn = new Button("OK");
        pane.getChildren().addAll(lbName,tfName,lbPassword,tfPassword,okbtn);


        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(openButton, 0, 1);
        GridPane.setConstraints(openMultipleButton, 0, 2);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton, openMultipleButton);




        final Pane rootGroup = new HBox(30);
        rootGroup.getChildren().addAll(inputGridPane,pane);
        rootGroup.setPadding(new Insets(80, 12, 12, 12));

        stage.setScene(new Scene(rootGroup,600,200));
        stage.show();
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void openFile(File file) {
        EventQueue.invokeLater(() -> {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
