package com.appafzar.notes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appafzar.notes.R;
import com.appafzar.notes.activity.FolderActivity;
import com.appafzar.notes.databinding.RowItemBinding;
import com.appafzar.notes.model.entity.Folder;

import io.realm.OrderedRealmCollection;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FoldersAdapter extends RealmRecyclerViewAdapter<Folder, FoldersAdapter.ItemViewHolder> {

    private Context context;

    public FoldersAdapter(Context context, OrderedRealmCollection<Folder> data) {
        super(data, true);
        this.context = context;
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.
        setHasStableIds(false);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(RowItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Folder folder = getItem(position);
        if (folder == null) return;
        final int itemId = folder.getId();
        holder.binding.textView.setText(folder.getName());
        holder.binding.checkBox.setChecked(countersToDelete.contains(itemId));
        if (inDeletionMode) {
            holder.binding.checkBox.setVisibility(View.VISIBLE);
            holder.binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) countersToDelete.add(itemId);
                else countersToDelete.remove(itemId);

            });
        } else {
            holder.binding.checkBox.setOnCheckedChangeListener(null);
            holder.binding.checkBox.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            FolderActivity.start((Activity) context, folder.getId());
        });
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        int totalPageItem = pageNo * limitPerPage;
        return (pagination && totalPageItem < count) ? totalPageItem : count;
    }

    @Override
    public void setPaginationPerPage(int limitPerPage) {
        this.limitPerPage = limitPerPage;
        notifyDataSetChanged();
    }

    @Override
    public void setPagination(boolean pagination) {
        this.pagination = pagination;
        notifyDataSetChanged();
    }

    @Override
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private RowItemBinding binding;

        ItemViewHolder(RowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.icon.setImageResource(R.drawable.ic_folder_24);
        }
    }
}
