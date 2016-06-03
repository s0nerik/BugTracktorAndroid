package com.github.sonerik.bugtracktor.api;

import com.cloudinary.Cloudinary;
import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.IssueAttachment;
import com.github.sonerik.bugtracktor.models.Token;
import com.github.sonerik.bugtracktor.prefs.MainPrefs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import lombok.experimental.Delegate;
import rx.Observable;

/**
 * Created by sonerik on 5/28/16.
 */
public class BugTracktorApi {
    private interface Login {
        Observable<Token> login(String email, String password);
    }

    @Delegate(excludes = Login.class)
    private final BugTracktorWebService service;
    private final File cacheDir;
    private final MainPrefs prefs;
    private final Cloudinary cloudinary;

    @Inject
    public BugTracktorApi(BugTracktorWebService service, File cacheDir, MainPrefs prefs, Cloudinary cloudinary) {
        this.service = service;
        this.cacheDir = cacheDir;
        this.prefs = prefs;
        this.cloudinary = cloudinary;
    }

    public boolean isLoggedIn() {
        return !prefs.getToken().isEmpty();
    }

    public void logOut() {
        prefs.removeToken();
    }

    public Observable<Token> login(String email, String password) {
        return service.login(email, password)
                      .doOnNext(result -> prefs.setToken(result.getToken()));
    }

    public Observable<IssueAttachment> addAttachment(Issue issue, Object attachment) {
        return uploadImage(attachment)
                .concatMap(url -> {
                    IssueAttachment issueAttachment = new IssueAttachment();
                    issueAttachment.setUrl(url);
                    if (issue.getAttachments() == null)
                        issue.setAttachments(new ArrayList<>());
                    issue.getAttachments().add(issueAttachment);
                    return Observable.just(issueAttachment);
                })
                .onErrorReturn(null);
    }

    private Observable<String> uploadImage(Object image) {
        return Observable.defer(() -> {
            try {
                Map response = cloudinary.uploader().upload(image, null);
                return Observable.just(response.get("url").toString());
            } catch (IOException e) {
                return Observable.error(e);
            }
        });
    }
}
