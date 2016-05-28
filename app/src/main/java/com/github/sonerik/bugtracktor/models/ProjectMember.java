package com.github.sonerik.bugtracktor.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


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
