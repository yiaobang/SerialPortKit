package com.yiaobang.serialportkit.model;

import com.yiaobang.mvvm.BaseModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

public class NavItem extends BaseModel {

    public enum Type { GROUP, LEAF, CREATOR }
    private final StringProperty name = new SimpleStringProperty();
    @Getter
    private final Type type;
    @Getter
    private final ObservableList<NavItem> children = FXCollections.observableArrayList();
    private final StringProperty contentType = new SimpleStringProperty();
    // 用于“新建”按钮的工厂函数
    @Getter
    @Setter
    private Supplier<NavItem> creatorFactory;
    public NavItem(String name, Type type) {
        this(name, type, null);
    }
    public NavItem(String name, Type type, String contentType) {
        this.name.set(name);
        this.type = type;
        this.contentType.set(contentType);
    }
    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public String getContentType() { return contentType.get(); }
    public void setContentType(String ct) { this.contentType.set(ct); }
    public StringProperty contentTypeProperty() { return contentType; }


    // 只能包含组的组
    public static NavItem groupWithGroups(String name, NavItem... groups) {
        for (NavItem item : groups) {
            if (item.getType() != NavItem.Type.GROUP) {
                throw new IllegalArgumentException("groupWithGroups 只能包含组节点");
            }
        }
        NavItem group = new NavItem(name, NavItem.Type.GROUP);
        group.getChildren().addAll(groups);
        return group;
    }

    // 只能包含叶子的组，并自动加creator，creator节点带工厂
    public static NavItem groupWithLeaves(String name, String creatorName, Supplier<NavItem> creatorFactory, NavItem... leaves) {
        for (NavItem item : leaves) {
            if (item.getType() != NavItem.Type.LEAF) {
                throw new IllegalArgumentException("groupWithLeaves 只能包含叶子节点");
            }
        }
        NavItem group = new NavItem(name, NavItem.Type.GROUP);
        group.getChildren().addAll(leaves);
        NavItem creator = new NavItem(creatorName, NavItem.Type.CREATOR);
        creator.setCreatorFactory(creatorFactory);
        group.getChildren().add(creator);
        return group;
    }

    public static NavItem leaf(String name, String contentType) {
        return new NavItem(name, NavItem.Type.LEAF, contentType);
    }
}
