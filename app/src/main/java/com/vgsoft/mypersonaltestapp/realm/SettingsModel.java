package com.vgsoft.mypersonaltestapp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SettingsModel extends RealmObject {

    @PrimaryKey
    private int id;
    private int page;
    private int totalPages;
    private int position;

    public SettingsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
