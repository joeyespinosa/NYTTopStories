package com.axelia.nyttopstories.data.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import com.axelia.nyttopstories.data.remote.paging.ItemPageKeyedDataSource;

public class RepoStoriesResult {
    public LiveData<PagedList<Story>> data;
    public LiveData<Resource> resource;
    public MutableLiveData<ItemPageKeyedDataSource> sourceLiveData;

    public RepoStoriesResult(LiveData<PagedList<Story>> data,
                             LiveData<Resource> resource,
                             MutableLiveData<ItemPageKeyedDataSource> sourceLiveData) {
        this.data = data;
        this.resource = resource;
        this.sourceLiveData = sourceLiveData;
    }
}
