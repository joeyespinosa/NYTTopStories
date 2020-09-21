package com.axelia.nyttopstories.ui.list.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.axelia.nyttopstories.StoryApplication;
import com.axelia.nyttopstories.R;
import com.axelia.nyttopstories.data.model.Resource;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.utils.BrowseItemsViewModelFactory;
import com.axelia.nyttopstories.utils.Constants;
import com.axelia.nyttopstories.utils.ItemOffsetDecoration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;

import javax.inject.Inject;

public class BrowseItemsFragment extends Fragment {

    private BrowseItemViewModel viewModel;
    private TextView sectionTextView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    @Inject
    BrowseItemsViewModelFactory factory;

    public static BrowseItemsFragment newInstance() {
        return new BrowseItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoryApplication.getComponent(Objects.requireNonNull(getActivity())).inject(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Constants.FIREBASE_ID_BROWSE_ITEMS);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,  Constants.FIREBASE_NAME_BROWSE_ITEMS);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_story, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sectionTextView = getActivity().findViewById(R.id.textview_section);

        viewModel = ViewModelProviders.of(this, factory).get(BrowseItemViewModel.class);

        viewModel.getStories("Arts");

        viewModel.getCurrentTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                sectionTextView.setText(title);
            }
        });

        setupListAdapter();
        setupAds();
    }

    private void setupAds() {
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    //region Options Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort_by) {

            MaterialDialog.Builder dialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.section)
                    .items(R.array.section_array)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            viewModel.getStories(text.toString());
                        }
                    });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    private void setupListAdapter() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerview_list);
        final BrowseItemsAdapter browseItemsAdapter = new BrowseItemsAdapter(viewModel);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(browseItemsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        viewModel.getPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Story>>() {
            @Override
            public void onChanged(PagedList<Story> items) {
                browseItemsAdapter.submitList(null);
                browseItemsAdapter.notifyDataSetChanged();
                browseItemsAdapter.submitList(items);
                browseItemsAdapter.notifyDataSetChanged();
            }
        });


        viewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {
                browseItemsAdapter.setNetworkState(resource);
            }
        });
    }
}
