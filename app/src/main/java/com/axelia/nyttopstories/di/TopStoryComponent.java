package com.axelia.nyttopstories.di;

import com.axelia.nyttopstories.di.modules.ApiServiceModule;
import com.axelia.nyttopstories.di.modules.AppExecutorsModule;
import com.axelia.nyttopstories.di.modules.ItemLocalDataSourceModule;
import com.axelia.nyttopstories.di.modules.ItemRemoteDataSourceModule;
import com.axelia.nyttopstories.di.modules.RoomDatabaseModule;
import com.axelia.nyttopstories.ui.details.DetailsActivity;
import com.axelia.nyttopstories.ui.list.browse.BrowseItemsFragment;
import com.axelia.nyttopstories.ui.list.favorites.FavoritesFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        AppExecutorsModule.class,
        ItemRemoteDataSourceModule.class,
        ItemLocalDataSourceModule.class,
        RoomDatabaseModule.class,
        ApiServiceModule.class
})
public interface TopStoryComponent {

    void inject(DetailsActivity detailsActivity);
    void inject(BrowseItemsFragment browseItemsFragment);
    void inject(FavoritesFragment favoritesFragment);
}
