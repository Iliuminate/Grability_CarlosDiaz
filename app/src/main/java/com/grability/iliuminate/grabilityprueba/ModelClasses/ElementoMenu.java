package com.grability.iliuminate.grabilityprueba.ModelClasses;

/**
 * Created by Iliuminate on 12/01/2016.
 */
public class ElementoMenu {

    CharSequence title;
    int     groupId,
            itemId,
            order;

    public ElementoMenu() {
    }

    public ElementoMenu(CharSequence title, int groupId, int itemId, int order) {
        this.title = title;
        this.groupId = groupId;
        this.itemId = itemId;
        this.order = order;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ElementoMenu{" +
                "title='" + title + '\'' +
                ", groupId=" + groupId +
                ", itemId=" + itemId +
                ", order=" + order +
                '}';
    }
}
