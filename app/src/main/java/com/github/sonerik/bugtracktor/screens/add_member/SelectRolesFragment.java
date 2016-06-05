package com.github.sonerik.bugtracktor.screens.add_member;

import android.support.v7.widget.RecyclerView;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.models.Project;
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
public class SelectRolesFragment extends BaseFragment {
    @State(ParcelBundler.class)
    @Arg(bundler = ParcelerArgsBundler.class)
    Project project;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_roles;
    }
}
