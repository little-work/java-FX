package com.lilin.fx.view;

import com.lilin.fx.bean.FileVo;
import com.lilin.fx.utils.FileUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EasyFileUtil extends Application {

    private String cssDefault = "-fx-border-color: blue;\n" + "-fx-border-insets: 2;\n"
            + "-fx-border-width: 1;\n" + "-fx-border-style: solid;\n";
    private ObservableList<String> dataList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EasyFileUtil");
        //水平布局
        HBox hBox = new HBox(10);
        HBox hBox2 = new HBox(10);
        hBox2.setAlignment(Pos.TOP_LEFT);
        hBox.setAlignment(Pos.CENTER);
        //hBox.setStyle(cssDefault);
        /**
         * 创建组件
         */
        Label userName = new Label("File Path:");
        //文件选择
        //FileChooser fileChooser = new FileChooser();
        //文件夹选择
        DirectoryChooser dc = new DirectoryChooser();
        TextField userTextField = new TextField();
        userTextField.setPrefColumnCount(20);
        Button fileBtn = new Button("browse");
        ListView listView = new ListView();
        Button fileBt2 = new Button("sekected");
        // TODO: 2019/9/10
       /* for(int i=1; i<15; i++) {
            listView.getItems().add(new CheckBoxListItem("Value"+i));
        }
*/


        fileBtn.setOnAction(
                (final ActionEvent e) -> {
                    dc.setTitle("View Fle");
                    dc.setInitialDirectory(
                            new File(System.getProperty("user.home"))
                    );
                    File file = dc.showDialog(primaryStage);
                    String directoryPath=file.getAbsolutePath();

                    userTextField.setText(directoryPath);
                    FileVo fileVo= FileUtils.getFiles(file.getAbsolutePath());

                    Map<String, List<String>> map=new HashMap<>();
                    map= FileUtils.getFile2(directoryPath,map);
                    for(Map.Entry entry:map.entrySet()){
                        for (String fileName:(List<String>)entry.getValue()){
                            dataList.add(fileName);
                            //listView.getItems().add(new CheckBoxListItem(fileName));
                        }
                    }
                    //https://blog.csdn.net/qq_43449112/article/details/88701291
                    listView.setItems(dataList);
                    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    /*listView.setCellFactory(new Callback<ListView<CheckBoxListItem>, ListCell<CheckBoxListItem>>() {
                        @Override
                        public ListCell<CheckBoxListItem> call(ListView<CheckBoxListItem> lv) {
                            CheckBoxListCell<CheckBoxListItem> cell = new CheckBoxListCell<>(CheckBoxListItem::selectedProperty, new StringConverter<CheckBoxListItem>() {
                                @Override
                                public String toString(CheckBoxListItem object) {
                                    return object.getName();
                                }

                                @Override
                                public CheckBoxListItem fromString(String string) {
                                    return null;
                                }
                            });
                            return cell;
                        }
                    });*/
                });



        //把组件都添加到水瓶布局中
        hBox.getChildren().addAll(userName, userTextField, fileBtn, listView);
        hBox2.getChildren().addAll(listView,fileBt2);


        final Pane rootGroup = new VBox(10);
        rootGroup.setPadding(new Insets(20));
        rootGroup.getChildren().addAll(hBox, hBox2);
        rootGroup.setPadding(new Insets(10, 10, 12, 12));


        Scene scene = new Scene(rootGroup, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }
}
