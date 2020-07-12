package com.appafzar.notes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appafzar.notes.R;
import com.appafzar.notes.activity.DrawingActivity;
import com.appafzar.notes.activity.NoteActivity;
import com.appafzar.notes.databinding.RowItemBinding;
import com.appafzar.notes.model.entity.Note;

import io.realm.OrderedRealmCollection;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class NotesAdapter extends RealmRecyclerViewAdapter<Note, NotesAdapter.ItemViewHolder> {

    private Context context;

    public NotesAdapter(Context context, OrderedRealmCollection<Note> data) {
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
        Note note = getItem(position);
        if (note == null) return;
        final int itemId = note.getId();
        holder.binding.textView.setText(note.getTitle());
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
            if (note.isPainting())
                DrawingActivity.start((Activity) context, note.getId());
            else
                NoteActivity.start((Activity) context, note.getId());
        });
        holder.binding.icon.setImageResource(note.isPainting() ? R.drawable.ic_drawing_24 : R.drawable.ic_note_24);
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
        }
    }
}
