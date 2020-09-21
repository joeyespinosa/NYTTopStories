package com.axelia.nyttopstories.ui.list.browse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.nyttopstories.data.model.Resource;
import com.axelia.nyttopstories.databinding.ItemNetworkStateBinding;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    private ItemNetworkStateBinding binding;

    public NetworkStateViewHolder(@NonNull ItemNetworkStateBinding binding,
                                  final BrowseItemViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.retry();
            }
        });
    }

    public static NetworkStateViewHolder create(ViewGroup parent, BrowseItemViewModel viewModel) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNetworkStateBinding binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false);
        return new NetworkStateViewHolder(binding, viewModel);
    }

    public void bindTo(Resource resource) {
        binding.setResource(resource);
        binding.executePendingBindings();
    }
}
