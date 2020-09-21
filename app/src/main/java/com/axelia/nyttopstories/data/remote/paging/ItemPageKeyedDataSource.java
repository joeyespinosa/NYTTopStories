package com.axelia.nyttopstories.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.axelia.nyttopstories.BuildConfig;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.model.TopStoryResponse;
import com.axelia.nyttopstories.data.remote.api.ApiService;
import com.axelia.nyttopstories.data.model.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemPageKeyedDataSource extends PageKeyedDataSource<Integer, Story> {

    private static final int FIRST_PAGE = 1;
    public MutableLiveData<Resource> networkState = new MutableLiveData<>();

    @Inject
    ApiService apiService;

    private final Executor networkExecutor;
    public RetryCallback retryCallback = null;

    private final String type;

    public ItemPageKeyedDataSource(ApiService apiService,
                                   Executor networkExecutor,
                                   String type
                                   ) {
        this.apiService = apiService;
        this.networkExecutor = networkExecutor;
        this.type = type;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Story> callback) {
        networkState.postValue(Resource.loading(null));

        // loadInitial
        Call<TopStoryResponse> request;
        request = apiService.getTopStories(type, BuildConfig.API_KEY);

        try {
            Response<TopStoryResponse> response = request.execute();
            TopStoryResponse data = response.body();
            List<Story> itemList = new ArrayList<>();
            itemList.clear();
            itemList = data != null ? data.getStories() : Collections.<Story>emptyList();

            retryCallback = null;
            networkState.postValue(Resource.success(null));
            callback.onResult(itemList, null, FIRST_PAGE + 1);
        } catch (IOException e) {
            retryCallback = new RetryCallback() {
                @Override
                public void invokeCallback() {
                    networkExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            loadInitial(params, callback);
                        }
                    });

                }
            };
            networkState.postValue(Resource.error(e.getMessage(), null));
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Story> callback) {
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Story> callback) {
        // loadAfter
        networkState.postValue(Resource.loading(null));

        Call<TopStoryResponse> request;
        request = apiService.getTopStories(type, BuildConfig.API_KEY);

        request.enqueue(new Callback<TopStoryResponse>() {
            @Override
            public void onResponse(Call<TopStoryResponse> call, Response<TopStoryResponse> response) {
                if (response.isSuccessful()) {
                    TopStoryResponse data = response.body();
                    List<Story> itemList = new ArrayList<>();
                    itemList.clear();
                    itemList = data != null ? data.getStories() : Collections.<Story>emptyList();
                    retryCallback = null;
                    callback.onResult(itemList, params.key + 1);
                    networkState.postValue(Resource.success(null));
                } else {
                    retryCallback = new RetryCallback() {
                        @Override
                        public void invokeCallback() {
                            loadAfter(params, callback);
                        }
                    };
                    networkState.postValue(Resource.error("Error Code: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<TopStoryResponse> call, Throwable t) {
                retryCallback = new RetryCallback() {
                    @Override
                    public void invokeCallback() {
                        networkExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                loadAfter(params, callback);
                            }
                        });
                    }
                };
                networkState.postValue(Resource.error(t != null ? t.getMessage() : "Unknown error", null));
            }
        });
    }

    public interface RetryCallback {
        void invokeCallback();
    }

}
