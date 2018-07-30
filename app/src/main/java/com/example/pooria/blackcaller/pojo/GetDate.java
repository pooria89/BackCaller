package com.example.pooria.blackcaller.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetDate {
    @SerializedName("get")
    @Expose
    private ArrayList<Gett> get = new ArrayList<>();

    public ArrayList<Gett> getGet() {
        return get;
    }

    public void setGet(ArrayList<Gett> get) {
        this.get = get;
    }
}
