package com.axelia.nyttopstories.ui.list.browse;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.nyttopstories.data.model.Story;
import com.axelia.nyttopstories.databinding.ItemStoryBinding;
import com.axelia.nyttopstories.ui.details.DetailsActivity;
import com.google.gson.Gson;

public class StoryViewHolder extends RecyclerView.ViewHolder {

    private final ItemStoryBinding binding;

    public StoryViewHolder(@NonNull ItemStoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(final Story item) {
        binding.setStory(item);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Item", item.getAbstract());

                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_ITEM_DETAILS, new Gson().toJson(item));
                intent.putExtra(DetailsActivity.EXTRA_IS_SAVED, false);
                view.getContext().startActivity(intent);


            }
        });
        binding.executePendingBindings();
    }

    public static StoryViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStoryBinding binding = ItemStoryBinding.inflate(layoutInflater, parent, false);
        return new StoryViewHolder(binding);
    }
}
