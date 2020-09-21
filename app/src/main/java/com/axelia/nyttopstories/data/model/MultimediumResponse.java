package com.axelia.nyttopstories.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultimediumResponse {

    @SerializedName("multimedia")
    List<Multimedium> multimedia;

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedium> cast) {
        this.multimedia = multimedia;
    }
}
