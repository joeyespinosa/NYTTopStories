package com.axelia.nyttopstories.ui.list.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.axelia.nyttopstories.StoryApplication;
import com.axelia.nyttopstories.R;
import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.databinding.FragmentFavoriteStoryBinding;
import com.axelia.nyttopstories.ui.list.browse.MainActivity;
import com.axelia.nyttopstories.utils.FavoritesViewModelFactory;
import com.axelia.nyttopstories.utils.ItemOffsetDecoration;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private FragmentFavoriteStoryBinding binding;

    @Inject
    FavoritesViewModelFactory factory;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoryApplication.getComponent(Objects.requireNonNull(getActivity())).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteStoryBinding.inflate(inflater, container, false);
        binding.storiesList.textviewSection.setText(R.string.favorites);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(FavoritesViewModel.class);
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = binding.storiesList.recyclerviewList;

        final FavoritesAdapter favoritesAdapter = new FavoritesAdapter();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        
        viewModel.getFavoriteListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> itemList) {
                if (itemList.isEmpty()) {
                    binding.storiesList.recyclerviewList.setVisibility(View.GONE);
                    binding.textviewEmptyState.setVisibility(View.VISIBLE);
                } else {
                    binding.storiesList.recyclerviewList.setVisibility(View.VISIBLE);
                    binding.textviewEmptyState.setVisibility(View.GONE);
                    favoritesAdapter.submitList(itemList);
                }
            }
        });
    }
}
