package com.github.sonerik.bugtracktor.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Parcel(Parcel.Serialization.BEAN)
@ApiModel(description = "")
public class ProjectMember  {
  
  @SerializedName("user")
  private User user = null;
  @SerializedName("project")
  private Project project = null;
  @SerializedName("roles")
  private List<Role> roles = null;
  @SerializedName("join_date")
  private Date joinDate = null;
  @SerializedName("exit_date")
  private Date exitDate = null;

  /**
   * User entity that is attached to the project as it's member.
   **/
  @ApiModelProperty(value = "User entity that is attached to the project as it's member.")
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Project getProject() {
    return project;
  }
  public void setProject(Project project) {
    this.project = project;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<Role> getRoles() {
    return roles;
  }
  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Date getJoinDate() {
    return joinDate;
  }
  public void setJoinDate(Date joinDate) {
    this.joinDate = joinDate;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Date getExitDate() {
    return exitDate;
  }
  public void setExitDate(Date exitDate) {
    this.exitDate = exitDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectMember projectMember = (ProjectMember) o;
    return (user == null ? projectMember.user == null : user.equals(projectMember.user)) &&
        (project == null ? projectMember.project == null : project.equals(projectMember.project)) &&
        (roles == null ? projectMember.roles == null : roles.equals(projectMember.roles)) &&
        (joinDate == null ? projectMember.joinDate == null : joinDate.equals(projectMember.joinDate)) &&
        (exitDate == null ? projectMember.exitDate == null : exitDate.equals(projectMember.exitDate));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (user == null ? 0: user.hashCode());
    result = 31 * result + (project == null ? 0: project.hashCode());
    result = 31 * result + (roles == null ? 0: roles.hashCode());
    result = 31 * result + (joinDate == null ? 0: joinDate.hashCode());
    result = 31 * result + (exitDate == null ? 0: exitDate.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectMember {\n");
    
    sb.append("  user: ").append(user).append("\n");
    sb.append("  project: ").append(project).append("\n");
    sb.append("  roles: ").append(roles).append("\n");
    sb.append("  joinDate: ").append(joinDate).append("\n");
    sb.append("  exitDate: ").append(exitDate).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
