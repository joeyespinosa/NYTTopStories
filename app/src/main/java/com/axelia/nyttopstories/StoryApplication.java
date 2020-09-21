package com.axelia.nyttopstories;

import android.app.Application;
import android.content.Context;

import androidx.databinding.library.BuildConfig;

import com.axelia.nyttopstories.di.DaggerTopStoryComponent;
import com.axelia.nyttopstories.di.TopStoryComponent;
import com.axelia.nyttopstories.di.modules.AppExecutorsModule;
import com.axelia.nyttopstories.di.modules.ApiServiceModule;
import com.axelia.nyttopstories.di.modules.ItemLocalDataSourceModule;
import com.axelia.nyttopstories.di.modules.ItemRemoteDataSourceModule;
import com.axelia.nyttopstories.di.modules.RoomDatabaseModule;

import timber.log.Timber;

public class StoryApplication extends Application {

    private TopStoryComponent component;

    public static TopStoryComponent getComponent(Context context) {
        return ((StoryApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = DaggerTopStoryComponent.builder()
                .appExecutorsModule(new AppExecutorsModule())
                .roomDatabaseModule(new RoomDatabaseModule(this))
                .apiServiceModule(new ApiServiceModule())
                .itemLocalDataSourceModule(new ItemLocalDataSourceModule())
                .itemRemoteDataSourceModule(new ItemRemoteDataSourceModule())
                .build();
    }
}
