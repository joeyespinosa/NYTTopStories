package com.axelia.nyttopstories.data.local;

import androidx.lifecycle.LiveData;

import com.axelia.nyttopstories.data.database.TopStoriesDatabase;
import com.axelia.nyttopstories.data.model.Multimedium;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.model.StoryDetails;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


@Singleton
public class StoryLocalDataSource {

    private final TopStoriesDatabase mDatabase;

    @Inject
    public StoryLocalDataSource(TopStoriesDatabase database) {
        mDatabase = database;
    }

    public void saveStory(Story item) {
        long savedId = mDatabase.resultsDao().insertStory(item);
        insertMultimedia(item.getMultimedia(), savedId);
    }

    private void insertMultimedia(List<Multimedium> items, long itemId) {
        for (Multimedium item : items) {
            item.setResultId(itemId);
        }
        mDatabase.multimediumDao().insertAllItems(items);
        Timber.d("%s items saved into database.", items.size());
    }

    public LiveData<StoryDetails> getStory(long itemId) {
        return mDatabase.resultsDao().getStory(itemId);
    }

    public LiveData<List<Story>> getAllSavedStories() {
        return mDatabase.resultsDao().getAllSavedStories();
    }

    public void deleteStory(long storyId) {
        mDatabase.resultsDao().deleteStory(storyId);
    }
}
