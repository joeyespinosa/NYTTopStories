package com.axelia.nyttopstories.data;

import androidx.lifecycle.LiveData;

import com.axelia.nyttopstories.data.local.StoryLocalDataSource;
import com.axelia.nyttopstories.data.model.RepoStoriesResult;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.remote.StoryRemoteDataSource;
import com.axelia.nyttopstories.utils.AppExecutors;

import java.util.List;


import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class StoryRepository implements DataSource {

    private final StoryLocalDataSource mLocalDataSource;
    private final StoryRemoteDataSource mRemoteDataSource;
    private final AppExecutors mExecutors;

    @Inject
    public StoryRepository(StoryLocalDataSource localDataSource,
                           StoryRemoteDataSource remoteDataSource,
                           AppExecutors executors) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mExecutors = executors;
    }

    @Override
    public RepoStoriesResult loadStories(String type) {
        return mRemoteDataSource.loadTopStories(type);
    }

    @Override
    public LiveData<List<Story>> getAllSavedStories() {
        return mLocalDataSource.getAllSavedStories();
    }

    @Override
    public void saveStory(final Story story) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("Adding to favorites");
                mLocalDataSource.saveStory(story);
            }
        });
    }

    @Override
    public void deleteStory(final long itemId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("Removing item");
                mLocalDataSource.deleteStory(itemId);
            }
        });
    }
}
