package com.axelia.nyttopstories.ui.details;

import androidx.lifecycle.ViewModel;

import com.axelia.nyttopstories.R;
import com.axelia.nyttopstories.data.StoryRepository;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.utils.SnackbarPrompt;

import javax.inject.Inject;

public class ItemDetailsViewModel extends ViewModel {

    private final StoryRepository repository;

    private final SnackbarPrompt mSnackbarText = new SnackbarPrompt();


    @Inject
    public ItemDetailsViewModel(final StoryRepository repository) {
        this.repository = repository;
    }

    public void saveStory(Story story) {
        repository.saveStory(story);
        showSnackbarPrompt(R.string.item_added_successfully);
    }

    public void deleteStory(long itemId) {
        repository.deleteStory(itemId);
        showSnackbarPrompt(R.string.item_removed_successfully);
    }

    private void showSnackbarPrompt(Integer message) {
        mSnackbarText.setValue(message);
    }

    public SnackbarPrompt getSnackbarPrompt() {
        return mSnackbarText;
    }
}
