package com.vgsoft.mypersonaltestapp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CursorModel extends RealmObject {

    @PrimaryKey
    private int id;
    private int page;
    private int totalPages;
    private int position;

    public CursorModel() {
    }

    public CursorModel(int id, int page, int totalPages, int position) {
        this.id = id;
        this.page = page;
        this.totalPages = totalPages;
        this.position = position;
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
