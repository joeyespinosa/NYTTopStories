package com.axelia.nyttopstories.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.model.StoryDetails;

import java.util.List;

@Dao
public interface StoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertStory(Story item);

    @Transaction
    @Query("SELECT * FROM story WHERE story.id= :itemId")
    LiveData<StoryDetails> getStory(long itemId);

    @Query("SELECT * FROM story")
    LiveData<List<Story>> getAllSavedStories();

    @Query("DELETE FROM story WHERE story.id = :itemId")
    void deleteStory(long itemId);
}
