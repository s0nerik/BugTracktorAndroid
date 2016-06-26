package com.github.sonerik.bugtracktor.screens.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.f2prateek.dart.Dart;
import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Created by sonerik on 5/28/16.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected Map<BindableRxList<?>, RecyclerView.Adapter<?>> getBindableLists() {
        return new HashMap<>();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        Dart.inject(this);

        for (Map.Entry<BindableRxList<?>, RecyclerView.Adapter<?>>
                entry : getBindableLists().entrySet()) {
            entry.getKey()
                 .bind(entry.getValue())
                 .compose(bindToLifecycle())
                 .subscribe();
        }
    }

    protected void handleRequestError(Throwable t) {
        Log.e(App.TAG, "Request error.", t);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }
}
