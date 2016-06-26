package com.github.sonerik.bugtracktor.rx_adapter;

import android.support.v7.widget.RecyclerView;

import com.github.s0nerik.rxlist.Event;
import com.github.s0nerik.rxlist.RxList;

import rx.Observable;

/**
 * Created by Alex on 6/3/2016.
 */
public class BindableRxList<E> extends RxList<E> {
    public <VH extends RecyclerView.ViewHolder> Observable<Event<E>> bind(RecyclerView.Adapter<VH> adapter) {
        return events()
                .doOnNext(e -> {
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
}
