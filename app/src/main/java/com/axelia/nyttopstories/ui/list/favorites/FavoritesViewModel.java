package com.axelia.nyttopstories.ui.list.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.axelia.nyttopstories.data.StoryRepository;
import com.axelia.nyttopstories.data.model.Story;

import java.util.List;


public class FavoritesViewModel extends ViewModel {

    private LiveData<List<Story>> favoriteListLiveData;

    public FavoritesViewModel(StoryRepository repository) {
        favoriteListLiveData = repository.getAllSavedStories();
    }

    public LiveData<List<Story>> getFavoriteListLiveData() {
        return favoriteListLiveData;
    }
}
