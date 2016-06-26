package com.github.sonerik.bugtracktor.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.InjectExtra;
import com.github.s0nerik.rxbus.RxBus;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.events.EProjectMemberCreated;
import com.github.sonerik.bugtracktor.events.ERoleSelectionChanged;
import com.github.sonerik.bugtracktor.events.EUserClicked;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.models.Role;
import com.github.sonerik.bugtracktor.models.User;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;
import com.jakewharton.rxbinding.support.v4.view.RxViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;
import lombok.val;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by sonerik on 6/5/16.
 */
//@HensonNavigable
public class AddMemberActivity extends BaseActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.btnCancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btnNext)
    AppCompatButton btnNext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @InjectExtra
    @State(ParcelBundler.class)
    Project project;

    @State(ParcelBundler.class)
    User selectedUser;
    @State(ParcelBundler.class)
    List<Role> selectedRoles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        initListeners();
    }

    private void initListeners() {
        RxBus.on(EUserClicked.class)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 selectedUser = e.user;
                 pager.setCurrentItem(1);
             });
        RxBus.on(ERoleSelectionChanged.class)
             .compose(bindToLifecycle())
             .subscribe(e -> {
                 if (e.selected)
                     selectedRoles.add(e.role);
                 else
                     selectedRoles.remove(e.role);

                 btnNext.setEnabled(!selectedRoles.isEmpty());
             });
        RxViewPager.pageSelections(pager)
                   .compose(bindToLifecycle())
                   .subscribe(i -> {
                       if (i == 1) {
                           toolbar.setTitle("Select roles");
                       } else {
                           toolbar.setTitle("Select user");
                       }
                   });
    }

    @OnClick(R.id.btnCancel)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.btnNext)
    public void onNext() {
        if (pager.getCurrentItem() == 0) {
            pager.setCurrentItem(1);
        } else {
            val member = new ProjectMember();
            member.setUser(selectedUser);
            member.setRoles(selectedRoles);
            RxBus.post(new EProjectMemberCreated(member));
            finish();
        }
    }
}
