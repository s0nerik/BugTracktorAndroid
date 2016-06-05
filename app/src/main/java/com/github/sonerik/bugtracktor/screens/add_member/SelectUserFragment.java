package com.github.sonerik.bugtracktor.screens.add_member;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.users.UsersAdapter;
import com.github.sonerik.bugtracktor.adapters.users.UsersItem;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseFragment;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;

import butterknife.BindView;
import icepick.State;

/**
 * Created by sonerik on 6/5/16.
 */
@FragmentWithArgs
public class SelectUserFragment extends BaseFragment {
    @State(ParcelBundler.class)
    @Arg(bundler = ParcelerArgsBundler.class)
    Project project;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private BindableRxList<UsersItem> items = new BindableRxList<>();
    private UsersAdapter adapter = new UsersAdapter(items);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_users;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);
    }
}
