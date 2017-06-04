package com.github.sonerik.bugtracktor.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.f2prateek.dart.InjectExtra;
import com.github.sonerik.bugtracktor.R;
import com.github.sonerik.bugtracktor.bundlers.ParcelBundler;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.screens.base.BaseActivity;

import butterknife.BindView;
import icepick.State;

/**
 * Created by Alex on 6/4/2017.
 */
public class AttachmentViewerActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.progress)
    ProgressBar progress;

    @InjectExtra
    @State(ParcelBundler.class)
    IssueAttachment attachment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attachment_viewer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        Glide.with(this)
                .load(attachment.getUrl())
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(image);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
