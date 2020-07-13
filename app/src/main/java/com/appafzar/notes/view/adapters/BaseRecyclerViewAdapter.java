package com.appafzar.notes.view.adapters;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.OrderedRealmCollection;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmModel;

/**
 * The RealmBaseRecyclerAdapter class is an abstract utility class for binding
 * RecyclerView UI elements to Realm data.
 * <p>
 * This adapter will automatically handle any updates to its data and call {@code notifyDataSetChanged()},
 * {@code notifyItemInserted()}, {@code notifyItemRemoved()} or {@code notifyItemRangeChanged()} as appropriate.
 * <p>
 * The RealmAdapter will stop receiving updates if the Realm instance providing
 * the {@link OrderedRealmCollection} is closed.
 * <p>
 * If the adapter contains Realm model classes with a primary key that is either an {@code int}
 * or a {@code long}, call {@code setHasStableIds(true)} in the constructor
 * and override {@link #getItemId(int)} as described by the Javadoc in that method.
 *
 * @param <T> type of {@link RealmModel} stored in the adapter.
 * @param <S> type of RecyclerView.ViewHolder used in the adapter.
 *            <p>
 *            Updated by: Hashemi
 *            https://github.com/AppAfzar
 *            Website: appafzar.com
 *            phone:(+98)912-7500-206
 */
public abstract class BaseRecyclerViewAdapter<T, S extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<S> {

    protected boolean inDeletionMode = false;
    protected Set<Integer> countersToDelete = new HashSet<>();
    protected boolean pagination = false;
    protected int limitPerPage = 0;
    protected int pageNo = 1;
    @Nullable
    private List<T> dataList;


    public void enableDeletionMode(boolean enabled) {
        inDeletionMode = enabled;
        if (!enabled) {
            countersToDelete.clear();
        }
        notifyDataSetChanged();
    }

    public Set<Integer> getCountersToDelete() {
        return countersToDelete;
    }

    /**
     * Returns the number of header elements before the Realm collection elements. This is needed so
     * all indexes reported by the {@link OrderedRealmCollectionChangeListener} can be adjusted
     * correctly. Failing to override can cause the wrong elements to animate and lead to runtime
     * exceptions.
     *
     * @return The number of header elements in the RecyclerView before the collection elements. Default is {@code 0}.
     */
    public int dataOffset() {
        return 0;
    }

    @Override
    public int getItemCount() {
        //noinspection ConstantConditions
        return dataList != null ? dataList.size() : 0;
    }

    /**
     * Returns the item in the underlying data associated with the specified position.
     * <p>
     * This method will return {@code null} if the Realm instance has been closed or the index
     * is outside the range of valid adapter data (which e.g. can happen if {@link #getItemCount()}
     * is modified to account for header or footer views.
     * <p>
     * Also, this method does not take into account any header views. If these are present, modify
     * the {@code index} parameter accordingly first.
     *
     * @param position index of the item in the original collection backing this adapter.
     * @return the item at the specified position or {@code null} if the position does not exists or
     * the adapter data are no longer valid.
     */
    @SuppressWarnings("WeakerAccess")
    @Nullable
    public T getItem(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Only indexes >= 0 are allowed. Input was: " + position);
        }

        // To avoid exception, return null if there are some extra positions that the
        // child adapter is adding in getItemCount (e.g: to display footer view in recycler view)
        if (dataList != null && position >= dataList.size()) return null;
        //noinspection ConstantConditions
        return dataList != null ? dataList.get(position) : null;
    }

    /**
     * Returns data associated with this adapter.
     *
     * @return adapter data.
     */
    @Nullable
    public List<T> getData() {
        return dataList;
    }

    /**
     * Updates the data associated to the Adapter. Useful when the query has been changed.
     * If the query does not change you might consider using the automaticUpdate feature.
     *
     * @param dataList the new {@link OrderedRealmCollection} to display.
     */
    @SuppressWarnings("WeakerAccess")
    public void setData(@Nullable List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public boolean isInDeletionMode() {
        return inDeletionMode;
    }

}
