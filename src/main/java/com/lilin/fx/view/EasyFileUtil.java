package com.lilin.fx.view;

import com.lilin.fx.assembly.MyListView;
import com.lilin.fx.assembly.MyTreeView;
import com.lilin.fx.bean.FileVo;
import com.lilin.fx.cellFactory.TextFieldTreeCellImpl;
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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EasyFileUtil");
        //水平布局
        HBox hBox = new HBox(10);
        HBox hBox2 = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.TOP_LEFT);
        //hBox.setStyle(cssDefault);
        /**
         * 创建组件
         */
        Label userName = new Label("文件夹路径:");
        //文件选择
        //FileChooser fileChooser = new FileChooser();
        //文件夹选择
        DirectoryChooser dc = new DirectoryChooser();
        TextField userTextField = new TextField();
        userTextField.setPrefColumnCount(20);
        Button fileBtn = new Button("浏览");
        ListView listView = new ListView();
        TreeView<String> tree=new TreeView<>();
        //给按钮添加事件
        fileBtn.setOnAction(
                (final ActionEvent e) -> {
                    dc.setTitle("选择一个文件夹");
                    dc.setInitialDirectory(
                            new File(System.getProperty("user.home"))
                    );
                    File file = dc.showDialog(primaryStage);
                    String directoryPath = file.getAbsolutePath();
                    userTextField.setText(directoryPath);
                    Map<String, List<String>> map = new HashMap<>();
                    map = FileUtils.getFile2(directoryPath, map);
                    for (Map.Entry entry : map.entrySet()) {
                        for (String fileName : (List<String>) entry.getValue()) {
                            dataList.add(fileName);
                            //listView.getItems().add(new CheckBoxListItem(fileName));
                        }
                    }
                    listView.setItems(dataList);


                    //树结构设置
                    FileVo fileVo=FileUtils.getFiles(directoryPath);
                    TreeItem<String> rootItem=new MyTreeView(fileVo).getTree();
                    tree.setRoot(rootItem);

                });
        /**
         * 设置listView的属性
         */
        MyListView myListView = new MyListView(listView);
        myListView.moreSelectModel(true);
        myListView.moreSelectEvent();
        listView.setEditable(true);

        /**
         * 设置Tree的属性
         */
        tree.setEditable(true);
        tree.setPrefSize(300,500);
        tree.setCellFactory((TreeView<String> p) ->
                new TextFieldTreeCellImpl());



        //把组件都添加到水瓶布局中
        hBox.getChildren().addAll(userName, userTextField, fileBtn);
        hBox2.getChildren().addAll(listView, tree);


        final Pane rootGroup = new VBox(10);
        //rootGroup.setPadding(new Insets(20));
        rootGroup.getChildren().addAll(hBox, hBox2);
        rootGroup.setPadding(new Insets(10, 10, 12, 12));


        Scene scene = new Scene(rootGroup, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
