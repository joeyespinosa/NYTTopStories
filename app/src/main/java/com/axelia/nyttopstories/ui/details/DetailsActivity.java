package com.axelia.nyttopstories.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.axelia.nyttopstories.R;
import com.axelia.nyttopstories.StoryApplication;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.databinding.ActivityDetailsBinding;
import com.axelia.nyttopstories.utils.ItemDetailsViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Objects;

import javax.inject.Inject;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_DETAILS = "EXTRA_ITEM_DETAILS";
    public static final String EXTRA_IS_SAVED = "EXTRA_IS_SAVED";
    private ActivityDetailsBinding mBinding;
    private ItemDetailsViewModel mViewModel;
    private Story story;

    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_SAVE = Menu.FIRST + 1;

    @Inject
    ItemDetailsViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);

        StoryApplication.getComponent(Objects.requireNonNull(this)).inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mBinding.setLifecycleOwner(this);

        mViewModel = ViewModelProviders.of(this, factory).get(ItemDetailsViewModel.class);

        setupToolbar();

        // Load item details from Intent Extras
        final String details = getIntent().getStringExtra(EXTRA_ITEM_DETAILS);
        story = new Gson().fromJson(details, Story.class);

        mBinding.setStory(story);

        // Add events on saving the story to database
        mBinding.detailsInfo.buttonReadOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(story.getUrl()));
                startActivity(intent);
            }
        });

        mViewModel.getSnackbarPrompt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer message) {
                Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (getIntent().getBooleanExtra(EXTRA_IS_SAVED, false)) {
            menu.add(0, MENU_DELETE, Menu.NONE, R.string.action_delete)
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, MENU_SAVE, Menu.NONE, R.string.action_favorite)
                    .setIcon(R.drawable.ic_favorite_border_black_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SAVE: {
                mViewModel.saveStory(story);
                invalidateOptionsMenu();
                return true;
            }
            case MENU_DELETE: {
                mViewModel.deleteStory(story.id);
                invalidateOptionsMenu();
                DetailsActivity.this.finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            handleCollapsedToolbarTitle();
        }
    }


    private void handleCollapsedToolbarTitle() {
        mBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                // verify if the toolbar is completely collapsed and set the name as the title
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.setTitle(getString(R.string.top_story_details));
                    isShow = true;
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    mBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
