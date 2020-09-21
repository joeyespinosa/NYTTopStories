package com.axelia.nyttopstories.data.remote.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.data.remote.api.ApiService;

import java.util.concurrent.Executor;


public class ItemDataSourceFactory extends DataSource.Factory<Integer, Story> {

    public MutableLiveData<ItemPageKeyedDataSource> sourceLiveData = new MutableLiveData<>();

    private final ApiService apiService;
    private final Executor networkExecutor;

    private final String type;

    public ItemDataSourceFactory(ApiService apiService,
                                 Executor networkExecutor,
                                 String type) {
        this.apiService = apiService;
        this.networkExecutor = networkExecutor;
        this.type = type;
    }

    @Override
    public DataSource<Integer, Story> create() {
        ItemPageKeyedDataSource dataSource = new ItemPageKeyedDataSource(apiService, networkExecutor, type);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }
}
