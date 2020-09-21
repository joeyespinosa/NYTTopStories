package com.axelia.nyttopstories.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.axelia.nyttopstories.data.local.dao.MultimediumDao;
import com.axelia.nyttopstories.data.local.dao.StoriesDao;
import com.axelia.nyttopstories.data.model.Multimedium;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.model.UtilConverters;


@Database(
        entities = {Story.class, Multimedium.class},
        version = 1,
        exportSchema = false)
@TypeConverters(UtilConverters.class)
public abstract class TopStoriesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "topstories.db";

    public abstract StoriesDao resultsDao();
    public abstract MultimediumDao multimediumDao();

    private static TopStoriesDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                TopStoriesDatabase.class,
                DATABASE_NAME).build();
    }
}
