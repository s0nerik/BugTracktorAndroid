package com.github.sonerik.bugtracktor.screens;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.sonerik.bugtracktor.App;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.adapters.roles.RolesAdapter;
import com.github.sonerik.bugtracktor.adapters.roles.RolesItem;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.Role;
import com.github.sonerik.bugtracktor.rx_adapter.BindableRxList;
import com.github.sonerik.bugtracktor.screens.base.BaseFragment;
import com.github.sonerik.bugtracktor.utils.Rx;
import com.google.common.collect.Collections2;
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
public class SelectRolesFragment extends BaseFragment {
    @Inject
    BugTracktorApi api;

    @State(ParcelBundler.class)
    @Arg(bundler = ParcelerArgsBundler.class)
    Project project;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private BindableRxList<RolesItem> items = new BindableRxList<>();
    private RolesAdapter adapter = new RolesAdapter(items);

    @Override
    protected Map<BindableRxList<?>, RecyclerView.Adapter<?>> getBindableLists() {
        return ImmutableMap.of(items, adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_roles;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get().inject(this);
        SelectRolesFragmentBuilder.injectArguments(this);
        loadRoles();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setAdapter(adapter);
    }

    private void loadRoles() {
        api.getRoles(project.getId())
           .compose(bindToLifecycle())
           .compose(Rx.applySchedulers())
           .subscribe(this::onRolesAvailable, this::handleRequestError);
    }

    private void onRolesAvailable(List<Role> roles) {
        items.clear();
        items.addAll(Collections2.transform(roles, r -> new RolesItem(r, false)));
    }
}
