package com.example.trenes.Apis;

import android.text.TextUtils;

import java.util.ArrayList;

public class RetrofitArray<T> {
    private ArrayList<T> list = new ArrayList<>();

    public void add(T t) {
        this.list.add(t);
    }

    public String toString() {
        if (this.list.isEmpty()) {
            return null;
        }
        return TextUtils.join(",", this.list);
    }

    public int size() {
        return this.list.size();
    }
}
