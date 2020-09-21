package com.axelia.nyttopstories.ui.list.browse;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.axelia.nyttopstories.data.StoryRepository;
import com.axelia.nyttopstories.data.model.Resource;
import com.axelia.nyttopstories.data.model.RepoStoriesResult;
import com.axelia.nyttopstories.data.model.Story;


public class BrowseItemViewModel extends ViewModel {

    private LiveData<RepoStoriesResult> repoStoriesResult;
    private LiveData<PagedList<Story>> pagedList;
    private LiveData<Resource> networkState;

    private MutableLiveData<String> currentTitle = new MutableLiveData<>();
    private MutableLiveData<String> sortBy = new MutableLiveData<>();

    private StoryRepository repository;

    public BrowseItemViewModel(final StoryRepository repository) {

        this.repository = repository;

        sortBy.setValue("Arts");
        currentTitle.setValue("Arts");

        repoStoriesResult = Transformations.map(sortBy, new Function<String, RepoStoriesResult>() {
            @Override
            public RepoStoriesResult apply(String type) {
                return repository.loadStories(type);
            }
        });

        pagedList = Transformations.switchMap(repoStoriesResult,
                new Function<RepoStoriesResult, LiveData<PagedList<Story>>>() {
                    @Override
                    public LiveData<PagedList<Story>> apply(RepoStoriesResult input) {
                        return input.data;
                    }
                });

        networkState = Transformations.switchMap(repoStoriesResult, new Function<RepoStoriesResult, LiveData<Resource>>() {
            @Override
            public LiveData<Resource> apply(RepoStoriesResult input) {
                return input.resource;
            }
        });
    }

    public MutableLiveData<String> getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(MutableLiveData<String> currentTitle) {
        this.currentTitle = currentTitle;
    }

    public void getStories(final String section) {
        sortBy.setValue(section);
        currentTitle.setValue(section);

    }

    public LiveData<PagedList<Story>> getPagedList() {
        return pagedList;
    }

    public LiveData<Resource> getNetworkState() {
        return networkState;
    }

    public void retry() {
        repoStoriesResult.getValue().sourceLiveData.getValue().retryCallback.invokeCallback();
    }
}