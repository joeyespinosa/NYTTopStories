package com.axelia.nyttopstories.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.axelia.nyttopstories.data.StoryRepository;
import com.axelia.nyttopstories.ui.list.favorites.FavoritesViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FavoritesViewModelFactory implements ViewModelProvider.Factory {

    private final StoryRepository repository;

    @Inject
    FavoritesViewModelFactory(StoryRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FavoritesViewModel(repository);
    }
}
