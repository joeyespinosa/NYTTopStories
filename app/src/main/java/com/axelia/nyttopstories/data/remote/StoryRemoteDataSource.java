package com.axelia.nyttopstories.data.remote;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.axelia.nyttopstories.data.model.Resource;

import com.axelia.nyttopstories.data.model.RepoStoriesResult;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.remote.api.ApiService;
import com.axelia.nyttopstories.data.remote.paging.ItemDataSourceFactory;
import com.axelia.nyttopstories.data.remote.paging.ItemPageKeyedDataSource;
import com.axelia.nyttopstories.utils.*;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class StoryRemoteDataSource {

    private static final int PAGE_SIZE = 30;

    @Inject
    AppExecutors mExecutors;

    @Inject
    ApiService apiService;

    public StoryRemoteDataSource(ApiService service,
                                 AppExecutors executors) {
        apiService = service;
        mExecutors = executors;
    }


    public RepoStoriesResult loadTopStories(
            String type
    ) {
        ItemDataSourceFactory sourceFactory = new ItemDataSourceFactory(
                apiService,
                mExecutors.networkIO(),
                type
        );

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<Story>> itemPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.sourceLiveData, new Function<ItemPageKeyedDataSource, LiveData<Resource>>() {
            @Override
            public LiveData<Resource> apply(ItemPageKeyedDataSource input) {
                return input.networkState;
            }
        });

        return new RepoStoriesResult(
                itemPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }
}
