package com.axelia.nyttopstories.data;

import androidx.lifecycle.LiveData;

import com.axelia.nyttopstories.data.model.RepoStoriesResult;
import com.axelia.nyttopstories.data.model.Story;

import java.util.List;

public interface DataSource {

    RepoStoriesResult loadStories(String type);

    LiveData<List<Story>> getAllSavedStories();

    void saveStory(Story story);

    void deleteStory(long itemId);
}