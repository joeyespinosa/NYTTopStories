package com.axelia.nyttopstories.di.modules;

import com.axelia.nyttopstories.data.database.TopStoriesDatabase;
import com.axelia.nyttopstories.data.local.StoryLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = RoomDatabaseModule.class
)
public class ItemLocalDataSourceModule {

    @Singleton
    @Provides
    StoryLocalDataSource provideLocalDataSource(TopStoriesDatabase database) {
        return new StoryLocalDataSource(database);
    }
}

