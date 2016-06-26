package com.github.sonerik.bugtracktor.screens.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.s0nerik.rxlist.RxList;
import com.github.s0nerik.rxlist.RxListBinder;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import icepick.Icepick;
import ru.noties.debug.Debug;

/**
 * Created by sonerik on 6/5/16.
 */
public abstract class BaseFragment extends RxFragment {
    protected Map<RxList<?>, RecyclerView.Adapter<?>> getBindableLists() {
        return new HashMap<>();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        FragmentArgs.inject(this);

        for (Map.Entry<RxList<?>, RecyclerView.Adapter<?>> entry : getBindableLists().entrySet()) {
            RxListBinder.bind(entry.getKey(), entry.getValue())
                        .compose(bindToLifecycle())
                        .subscribe();
        }
    }

    protected void handleRequestError(Throwable t) {
        Debug.e(t);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
