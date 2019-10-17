package com.lilin.fx.view;

import com.lilin.fx.assembly.MyListView;
import com.lilin.fx.assembly.MyTreeView;
import com.lilin.fx.bean.FileVo;
import com.lilin.fx.cellFactory.TextFieldTreeCellImpl;
import com.lilin.fx.utils.FileUtils;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import java.util.*;


public class EasyFileUtil extends Application {

    private String cssDefault = "-fx-border-color: blue;\n" + "-fx-border-insets: 2;\n"
            + "-fx-border-width: 1;\n" + "-fx-border-style: solid;\n";
    private ObservableList<String> dataList = FXCollections.observableArrayList();

    private ObservableList<TreeItem<String>> selectedTreeItems = FXCollections.observableArrayList();

    private ObservableList<TreeItem<String>> selectedTreeItems2 = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("文件工具");
        //水平布局
        HBox hBox = new HBox(10);
        HBox hBox2 = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.TOP_LEFT);
        //hBox.setStyle(cssDefault);
        /**
         * 创建组件
         */
        Label filePath = new Label("文件夹路径:");

        Label needRepalceStr = new Label("需替换的字符串:");
        //批量修改的文件名中包含的字符串
        TextField witchStr = new TextField();
        Label replaceTo = new Label("替换为:");
        //批量修改的文件名中包含的字符串
        TextField contianStr = new TextField();


        //文件选择
        //FileChooser fileChooser = new FileChooser();
        //文件夹选择
        DirectoryChooser dc = new DirectoryChooser();
        //文件或者文件夹路径
        TextField filePathText = new TextField();
        filePathText.setPrefColumnCount(20);
        //按钮
        Button fileBtn = new Button("浏览");
        //选取
        Button choosebtn = new Button("选取");
        //删除
        Button deletebtn = new Button("删除");
        //修改
        Button modifybtn = new Button("修改");
        //列表框
        ListView listView = new ListView();
        //列表树
        TreeView<String> tree = new TreeView<>();


        /**
         * 打开文件夹选择器  选中一个文件夹 把文件夹的路径填充到文本域中
         */
        fileBtn.setOnAction(
                (final ActionEvent e) -> {
                    dc.setTitle("选择一个文件夹");
                    dc.setInitialDirectory(
                            new File(System.getProperty("user.home"))
                    );
                    File file = dc.showDialog(primaryStage);
                    try {
                        if (file.getAbsolutePath() != null) {
                            String directoryPath = file.getAbsolutePath();
                            filePathText.setText(directoryPath);
                            //给listView添加数据
                        /*Map<String, List<String>> map = new HashMap<>();
                        map = FileUtils.getFile2(directoryPath, map);
                        for (Map.Entry entry : map.entrySet()) {
                            for (String fileName : (List<String>) entry.getValue()) {
                                dataList.add(fileName);
                                //listView.getItems().add(new CheckBoxListItem(fileName));
                            }
                        }
                        listView.setItems(dataList);*/


                            //树结构设置并添加数据
                            FileVo fileVo = FileUtils.getFiles(directoryPath);
                            TreeItem<String> rootItem = new MyTreeView(fileVo).getTree();
                            tree.setRoot(rootItem);

                        }
                    } catch (Exception e1) {
                        if (e1 instanceof NullPointerException) {
                            System.out.println("请选择文件名的名字");
                        } else {
                            e1.printStackTrace();
                        }

                    }

                });
        /**
         *   将树中被选中的文件  放到 列表框中
         */
       /* choosebtn.setOnAction(
                (final ActionEvent e) -> {
                    //获取文件夹的路径
                    //String directoryPath = userTextField.getText();
                    //获取到多选模式下面  选中的文件下表
                    selectedTreeItems = tree.getSelectionModel().getSelectedItems();
                    for (TreeItem<String> treeNode : selectedTreeItems) {
                        //dataList.add(directoryPath + "\\" + treeNode.getValue());
                        listView.getItems().add(treeNode.getValue());
                    }
                    //listView.getItems().add(dataList);
                });*/


        /**
         * 批量修改文件的名字
         */
        modifybtn.setOnAction(
                (final ActionEvent e) -> {
                    selectedTreeItems2 = tree.getSelectionModel().getSelectedItems();
                    try {
                        selectedTreeItems2.stream().forEach(treeNode -> {
                            File file = new File(filePathText.getText() + File.separator + treeNode.getValue());
                            if(!file.exists()){
                                return;
                            }
                            //看看你选中的文件名字是不是包含需要修改的文件名字
                            if (treeNode.getValue().contains(witchStr.getText())) {
                                //替换过后的新的文件名字
                                String str=treeNode.getValue().replace(witchStr.getText(),contianStr.getText());
                                File newFile = new File(filePathText.getText() + File.separator + str);
                                if(!newFile.exists()){
                                    return;
                                }
                                file.renameTo(newFile);
                                treeNode.setValue(str);
                            }
                        });
                        //弹框  提示替换成功了
                        dialog("恭喜","替换成功了！");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                });


        /**
         * 点击选择按钮 删除树中选中的文件的名字
         */
        deletebtn.setOnAction(
                (final ActionEvent e) -> {
                    //按钮部分可以使用预设的也可以像这样自己 new 一个
                    Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, "接下来你可以", new ButtonType("取消", ButtonBar.ButtonData.NO),
                            new ButtonType("确定", ButtonBar.ButtonData.YES));
                    //设置窗口的标题
                    _alert.setTitle("提示");
                    _alert.setHeaderText("你确定要删除吗？");
                    //设置对话框的 icon 图标，参数是主窗口的 stage
                    //showAndWait() 将在对话框消失以前不会执行之后的代码
                    Optional<ButtonType> _buttonType = _alert.showAndWait();
                    //根据点击结果返回
                    if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {

                        //保存待删除的子节点集合
                        List<TreeItem<String>> list = new ArrayList();
                        //选中删除节点集合
                        selectedTreeItems = tree.getSelectionModel().getSelectedItems();
                        System.out.println("选中" + selectedTreeItems.size() + "个节点");
                        //遍历
                        selectedTreeItems.stream().forEach(treeNode -> {
                            File file = new File(filePathText.getText() + File.separator + treeNode.getValue());
                            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
                            if (file.exists() && file.isFile()) {
                                if (file.delete()) {
                                    System.out.println("删除文件" + file.getName() + "成功！");
                                } else {
                                    System.out.println("删除文件" + file.getName() + "失败！");
                                }
                            } else {
                                System.out.println("删除文件失败：" + file.getName() + "不存在！");
                            }
                            list.add(treeNode);
                        });
                        /**
                         * 在tree中删除节点
                         *
                         * 这里需要借助java中的List 遍历删除tree中的节点  ObservableList不行 不知道为啥？？？
                         */
                        for (TreeItem<String> dd : list) {
                            System.out.println(dd.getValue());
                            dd.getParent().getChildren().remove(dd);
                        }
                    } else {
                        _alert.close();
                    }

                });
        /**
         * 设置listView的属性
         */
        MyListView myListView = new MyListView(listView);
        myListView.moreSelectModel(true);
        myListView.moreSelectEvent();
        //listView.setEditable(true);


        /**
         * 设置Tree的属性
         */
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tree.setPrefSize(300, 500);


        //把组件都添加到水瓶布局中
        hBox.getChildren().addAll(filePath, filePathText, fileBtn);

        VBox v_buttons = new VBox(10);
        v_buttons.getChildren().addAll(deletebtn, needRepalceStr,witchStr,replaceTo,contianStr, modifybtn);

        hBox2.getChildren().addAll(tree, v_buttons);


        //再把水平布局加到垂直布局中去
        final Pane rootGroup = new VBox(10);
        //rootGroup.setPadding(new Insets(20));
        rootGroup.getChildren().addAll(hBox, hBox2);
        rootGroup.setPadding(new Insets(10, 10, 12, 12));


        Scene scene = new Scene(rootGroup, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void dialog(String heeaderText,String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(heeaderText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
