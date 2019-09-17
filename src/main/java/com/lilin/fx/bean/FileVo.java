package com.lilin.fx.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 一个FileVo就是一个文件夹  包含里面的子文件夹集合和子文件
 */
public class FileVo {

    private String fileName;       //如果选中的是文件不是文件夹  那么这个就是文件的名字
    private Boolean isDirectory;   //是不是文件夹
    private String filePath;       //文件夹的路径
    private List<FileVo> fileVos = new ArrayList<>();          //子文件夹集合
    private Map<String, List<String>> map;   //文件夹中的文件


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<String>> map) {
        this.map = map;
    }

    public List<FileVo> getFileVos() {
        return fileVos;
    }

    public void setFileVos(List<FileVo> fileVos) {
        this.fileVos = fileVos;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
