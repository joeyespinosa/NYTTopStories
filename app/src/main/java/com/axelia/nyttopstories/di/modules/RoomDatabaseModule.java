package com.axelia.nyttopstories.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.axelia.nyttopstories.data.database.TopStoriesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomDatabaseModule {

    private TopStoriesDatabase topStoriesDatabase;

    public RoomDatabaseModule(Application application) {
        topStoriesDatabase = Room.databaseBuilder(application, TopStoriesDatabase.class, TopStoriesDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    TopStoriesDatabase provideDatabase() {
        return topStoriesDatabase;
    }

}

