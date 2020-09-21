package com.axelia.nyttopstories.data.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class UtilConverters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromMultimediaList(List<Multimedium> items) {
        return gson.toJson(items);
    }

    @TypeConverter
    public static List<Multimedium> toMultimediumList(String items) {
        if (items == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Multimedium>>() {}.getType();

        return gson.fromJson(items, listType);
    }
}
