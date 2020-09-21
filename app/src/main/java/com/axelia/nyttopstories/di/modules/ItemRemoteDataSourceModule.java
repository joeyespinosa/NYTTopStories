package com.axelia.nyttopstories.di.modules;

import com.axelia.nyttopstories.data.remote.StoryRemoteDataSource;
import com.axelia.nyttopstories.data.remote.api.ApiService;
import com.axelia.nyttopstories.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                AppExecutorsModule.class,
                ApiServiceModule.class
        }
)
public class ItemRemoteDataSourceModule {

    @Singleton
    @Provides
    StoryRemoteDataSource provideRemoteDataSource(ApiService apiService, AppExecutors executors) {
        return new StoryRemoteDataSource(apiService, executors);
    }
}

