package com.axelia.nyttopstories.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.axelia.nyttopstories.data.StoryRepository;
import com.axelia.nyttopstories.ui.list.browse.BrowseItemViewModel;
import com.axelia.nyttopstories.ui.list.favorites.FavoritesViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final StoryRepository repository;

    public static ViewModelFactory getInstance(StoryRepository repository) {
        return new ViewModelFactory(repository);
    }

    private ViewModelFactory(StoryRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BrowseItemViewModel.class)) {
            //noinspection unchecked
            return (T) new BrowseItemViewModel(repository);
        } else if (modelClass.isAssignableFrom(FavoritesViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoritesViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
