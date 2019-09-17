package com.lilin.fx.assembly;

import com.lilin.fx.utils.CheckBoxListItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MyListView {


    private ListView listView;


    public MyListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * 当单选选中一个listView中的项目的时候  就出发这个事件
     */
    public void singleSelectEvent() {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(observable.getValue());
                System.out.println(oldValue);
                System.out.println(newValue);
            }
        });
    }

    /**
     * 当单选选中一个listView中的项目的时候  索引就出发这个事件
     */
    public void singleIndexEvent() {
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue.intValue());
                System.out.println(oldValue);
                System.out.println(newValue);

            }
        });
    }

    /**
     * 多选事件  选中listView中多个项目的时候触发事件
     */
    public void moreSelectEvent() {
        listView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                ObservableList list = c.getList();
                System.out.println(list.size());
            }
        });
    }

    /**
     * 是否设置可以多选模式
     *
     * @param flag
     */
    public void moreSelectModel(Boolean flag) {
        if (flag) {
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    /**
     * 设置listView的样式  前面有多选框
     */
    public void checkBoxModel() {
        listView.setCellFactory(new Callback<ListView<CheckBoxListItem>, ListCell<CheckBoxListItem>>() {
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
        });
    }

}
