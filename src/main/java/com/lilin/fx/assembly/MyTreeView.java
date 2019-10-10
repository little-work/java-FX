package com.lilin.fx.assembly;

import com.lilin.fx.bean.FileVo;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyTreeView {

    //自定义数据结构文件树
    private FileVo fileVo;

    public MyTreeView(FileVo fileVo) {
        this.fileVo = fileVo;
    }


    /**
     * 根据自定义的数据结构FileVo 遍历成树
     * @return
     */
    public TreeItem<String> getTree() {
        //创建根节点  并添加图标
        TreeItem<String> rootItem = new TreeItem<>(fileVo.getFilePath(),new ImageView(
                new Image(getClass().getResourceAsStream("/images/Folder.png"))
        ));
        if (fileVo != null) {
            //子节点也是目录
            if (fileVo.getDirectory()) {
                for (Map.Entry entry : fileVo.getMap().entrySet()) {
                    //将根节点下面的文件放到根节点下面
                    if (((List<String>) entry.getValue()).size() > 0) {
                        for (String fileName : (List<String>) entry.getValue()) {
                            rootItem.getChildren().add(new TreeItem<>(fileName,new ImageView(
                                    new Image(getClass().getResourceAsStream("/images/file.png"))
                            )));
                        }
                    }
                }
                //如果根节点下面有子文件夹的话
                if (fileVo.getFileVos().size() > 0) {
                    for (FileVo fv : fileVo.getFileVos()) {
                        getChild(fv,rootItem);
                    }
                }
            } else {
                rootItem.setValue(fileVo.getFileName());
            }
        }
        return rootItem;
    }

    /**
     * 递归遍历文件夹里面的子文件夹
     * @param fileVo
     * @param parentItem
     */
    public void  getChild(FileVo fileVo, TreeItem<String> parentItem) {
        TreeItem<String> chlidItem = new TreeItem<>(fileVo.getFilePath(),new ImageView(
                new Image(getClass().getResourceAsStream("/images/Folder.png"))
        ));
        for (Map.Entry entry : fileVo.getMap().entrySet()) {
            if (((List<String>) entry.getValue()).size() > 0) {
                for (String fileName : (List<String>) entry.getValue()) {
                    chlidItem.getChildren().add(new TreeItem<>(fileName,new ImageView(
                            new Image(getClass().getResourceAsStream("/images/file.png"))
                    )));
                }
            }
        }
        //判断有没有文件夹
        if (fileVo.getFileVos().size() > 0) {
            for (FileVo fv : fileVo.getFileVos()) {
                getChild(fv,chlidItem);
            }
        }
        parentItem.getChildren().add(chlidItem);
    }

}
