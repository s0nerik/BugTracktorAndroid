package com.github.sonerik.bugtracktor.screens;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.s0nerik.rxlist.RxList;
import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.users.UsersAdapter;
import com.github.sonerik.bugtracktor.adapters.users.UsersItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.screens.base.BaseFragment;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.google.common.collect.ImmutableMap;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;

/**
 * Created by sonerik on 6/5/16.
 */
@FragmentWithArgs
public class SelectUserFragment extends BaseFragment {
    @Inject
    BugTracktorApi api;

    @State(ParcelBundler.class)
    @Arg(bundler = ParcelerArgsBundler.class)
    Project project;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private RxList<UsersItem> items = new RxList<>();
    private UsersAdapter adapter = new UsersAdapter(items);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_users;
    }

    @Override
    protected Map<RxList<?>, RecyclerView.Adapter<?>> getBindableLists() {
        return ImmutableMap.of(items, adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get().inject(this);
        loadUsers();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        layoutManager.setAutoMeasureEnabled(true);
    }

    private void loadUsers() {
        api.getUsers(null, null, null, null, null)
           .compose(bindToLifecycle())
           .compose(Rx.applySchedulers())
           .subscribe(this::onUsersLoaded, this::handleRequestError);
    }

    private void onUsersLoaded(List<User> users) {
        for (User user : users) {
            items.add(new UsersItem(user, false));
        }
    }
}
