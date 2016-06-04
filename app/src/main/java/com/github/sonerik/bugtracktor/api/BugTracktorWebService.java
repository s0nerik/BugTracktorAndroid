package com.github.sonerik.bugtracktor.api;

import com.github.sonerik.bugtracktor.models.Issue;
import com.github.sonerik.bugtracktor.models.IssueChange;
import com.github.sonerik.bugtracktor.models.IssueType;
import com.github.sonerik.bugtracktor.models.Permission;
import com.github.sonerik.bugtracktor.models.Project;
import com.github.sonerik.bugtracktor.models.ProjectMember;
import com.github.sonerik.bugtracktor.models.Success;
import com.github.sonerik.bugtracktor.models.Token;
import com.github.sonerik.bugtracktor.models.User;

import java.util.Date;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sonerik on 5/28/16.
 */
public interface BugTracktorWebService {
    @POST("register")
    Observable<User> register(@Body User user);

    @GET("get_token")
    Observable<Token> login(@Header("email") String email, @Header("password") String password);

    @GET("permissions")
    Observable<List<Permission>> getPermissions(@Query("projectId") Integer projectId);

    // region Projects

    @GET("projects")
    Observable<List<Project>> getProjects();

    @POST("projects")
    Observable<Project> createProject(@Body Project project);

    @GET("projects/{projectId}")
    Observable<Project> getProject(@Path("projectId") int id);

    @PATCH("projects/{projectId}")
    Observable<Project> updateProject(@Path("projectId") int id, Project project);

    @DELETE("projects/{projectId}")
    Observable<Success> removeProject(@Path("projectId") int id);

    // endregion


    // region Project issues

    @GET("projects/{projectId}/issues")
    Observable<List<Issue>> getIssues(@Path("projectId") int projectId,
                                      @Query("limit") Integer limit,
                                      @Query("fromDate") Date fromDate,
                                      @Query("toDate") Date toDate);

    @POST("projects/{projectId}/issues")
    Observable<Issue> createIssue(@Path("projectId") int projectId, @Body Issue issue);

    @GET("projects/{projectId}/issues/{issueIndex}")
    Observable<Issue> getIssue(@Path("projectId") int projectId, @Path("issueIndex") int issueIndex);

    @PATCH("projects/{projectId}/issues/{issueIndex}")
    Observable<Issue> updateIssue(@Path("projectId") int projectId, @Path("issueIndex") int issueIndex, @Body Issue issue);

    // endregion


    // region Project members

    @GET("projects/{projectId}/members")
    Observable<List<ProjectMember>> getProjectMembers(@Path("projectId") int id);

    @POST("projects/{projectId}/members")
    Observable<ProjectMember> addProjectMember(@Path("projectId") int id, @Body ProjectMember user);

    // endregion

    // region Issue types

    @GET("projects/{projectId}/issueTypes")
    Observable<List<IssueType>> getIssueTypes(@Path("projectId") int projectId,
                                              @Query("limit") Integer limit);

    @POST("projects/{projectId}/issueTypes")
    Observable<IssueType> createIssueType(@Path("projectId") int projectId, @Body IssueType issueType);

    @GET("projects/{projectId}/issueTypes/{issueTypeId}")
    Observable<IssueType> getIssueType(@Path("projectId") int projectId, @Path("issueTypeId") int issueTypeId);

    @PATCH("projects/{projectId}/issueTypes/{issueTypeId}")
    Observable<IssueType> updateIssueType(@Path("projectId") int projectId, @Path("issueTypeId") int issueTypeId, @Body IssueType issueType);

    // endregion


    // region Issue changes

    @GET("projects/{projectId}/issues/{issueIndex}/changes")
    Observable<List<IssueChange>> getIssueChanges(@Path("projectId") int projectId,
                                                  @Path("issueIndex") int issueIndex,
                                                  @Query("limit") Integer limit,
                                                  @Query("fromDate") Date fromDate,
                                                  @Query("toDate") Date toDate);

    // endregion
}
