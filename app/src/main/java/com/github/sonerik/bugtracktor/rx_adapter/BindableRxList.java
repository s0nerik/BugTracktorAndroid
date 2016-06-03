package com.github.sonerik.bugtracktor.rx_adapter;

import android.support.v7.widget.RecyclerView;

import rx.Subscription;

/**
 * Created by Alex on 6/3/2016.
 */
public class BindableRxList<E> extends RxList<E> {
    private Subscription sub;

    public <VH extends RecyclerView.ViewHolder> void bind(RecyclerView.Adapter<VH> adapter) {
        unbind();
        sub = events()
                .subscribe(e -> {
                    switch (e.type) {
                        case ITEM_ADDED:
                            adapter.notifyItemInserted(e.index);
                            break;
                        case ITEM_REMOVED:
                            adapter.notifyItemRemoved(e.index);
                            break;
                        case ITEM_CHANGED:
                            adapter.notifyItemChanged(e.index);
                            break;
                        case ITEMS_CLEARED:
                            adapter.notifyDataSetChanged();
                            break;
                    }
                });
    }

    public void unbind() {
        if (sub != null)
            sub.unsubscribe();
    }
}
