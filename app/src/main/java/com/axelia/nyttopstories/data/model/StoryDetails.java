package com.axelia.nyttopstories.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StoryDetails {

    @Embedded
    public Story story;

    @Relation(parentColumn = "id", entityColumn = "story_id")
    public List<Multimedium> multimediumList;

    public StoryDetails(Story story, List<Multimedium> multimediumList) {
        this.story = story;
        this.multimediumList = multimediumList;

    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public List<Multimedium> getMultimediumList() {
        return multimediumList;
    }

    public void setMultimediumList(List<Multimedium> multimediumList) {
        this.multimediumList = multimediumList;
    }
}
