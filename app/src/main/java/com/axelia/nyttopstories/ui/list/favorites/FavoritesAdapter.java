package com.axelia.nyttopstories.ui.list.favorites;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.axelia.nyttopstories.data.model.Story;

import java.util.List;


public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Story> itemList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FavoriteStoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Story item = itemList.get(position);
        ((FavoriteStoryViewHolder) holder).bindTo(item);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public void submitList(List<Story> items) {
        itemList = items;
        notifyDataSetChanged();
    }
}
