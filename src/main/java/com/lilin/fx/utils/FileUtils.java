package com.lilin.fx.utils;

import com.lilin.fx.bean.FileVo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件处理工具类
 */
public class FileUtils {

    /**
     * 给定一个路径 数据结构树
     *
     * @param path   需要遍历的文件夹路径
     * @return  数据结构树
     */
    public static FileVo  getFiles(String path) {

        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("文件或者文件名不存在");
        }
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(true);
            fileVo.setFilePath(path);
            List<String> list = new ArrayList<>();
            Map<String, List<String>> map = new HashMap<>();
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            if(files.length>0){
                for (int i = 0; i < files.length; i++) {
                    // 如果还是文件夹 递归获取里面的文件 文件夹
                    if (files[i].isDirectory()) {
                        FileVo fileVo2 = getFiles(files[i].getPath());
                        fileVo.getFileVos().add(fileVo2);
                    } else {
                        list.add(files[i].getName());
                    }
                }
            }
            //文件夹名字和它里面文件的集合
            map.put(path, list);
            fileVo.setMap(map);
            return fileVo;
        } else {
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(false);
            fileVo.setFilePath(path);
            fileVo.setFileName(file.getName());
            //System.out.println("文件：" + file.getName());
            return fileVo;
        }
    }

    /**
     * 给定一个路径  返回一个map
     * 包含这个路径下面所有的文件名（不包含子文件夹名）
     *
     * @param path
     * @param map
     * @return
     */
    public static Map<String, List<String>> getFile2(String path, Map<String, List<String>> map) {

        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("文件或者文件名不存在");
        }
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            List<String> list = new ArrayList<>();
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    map = getFile2(files[i].getPath(), map);
                } else {
                    list.add(files[i].getName());
                }
            }
            map.put(path, list);
            return map;
        } else {
            List<String> list = new ArrayList<>();
            list.add(file.getName());
            map.put(path, list);
            return map;
        }
    }

    /**
     * 遍历文件实体类
     *
     * @param fileVo
     */
    public static void getFileName(FileVo fileVo) {
        if (fileVo.getDirectory()) {
            for (Map.Entry entry : fileVo.getMap().entrySet()) {
                System.out.println(entry.getKey() + "下面的文件是");
                for (String fileName : (List<String>) entry.getValue()) {
                    System.out.println(fileName);
                }
            }
            if (fileVo.getFileVos().size()>0) {
                for(FileVo fileV:fileVo.getFileVos()){
                    getFileName(fileV);
                }
            }
        } else {
            System.out.println(fileVo.getFileName());
        }
    }

    public static void main(String[] args) {
        getFileName(getFiles("E:\\桌面文档汇总\\logs"));
    }
}





