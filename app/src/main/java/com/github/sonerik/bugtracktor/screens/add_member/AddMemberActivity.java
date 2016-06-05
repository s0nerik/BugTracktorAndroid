package com.github.sonerik.bugtracktor.screens.add_member;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;
import io.github.kobakei.grenade.annotation.Extra;
import io.github.kobakei.grenade.annotation.Navigator;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by sonerik on 6/5/16.
 */
@Navigator
public class AddMemberActivity extends BaseActivity {

    @Extra
    @State(ParcelBundler.class)
    Project project;

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddMemberActivityNavigator.inject(this, getIntent());

        Fragment[] fragments = new Fragment[]{
                SelectUserFragmentBuilder.newSelectUserFragment(project),
                SelectRolesFragmentBuilder.newSelectRolesFragment(project)
        };
        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        indicator.setViewPager(pager);
    }

    @OnClick(R.id.btnCancel)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.btnAdd)
    public void onAdd() {
        finish();
    }
}
