package com.github.sonerik.bugtracktor.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class Project  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("short_description")
  private String shortDescription = null;
  @SerializedName("full_description")
  private String fullDescription = null;
  @SerializedName("creator")
  private User creator = null;
  @SerializedName("members")
  private List<ProjectMember> members = null;
  @SerializedName("issues")
  private List<Issue> issues = null;

  
  /**
   * Unique project identifier.
   **/
  @ApiModelProperty(value = "Unique project identifier.")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
  /**
   * Project name.
   **/
  @ApiModelProperty(value = "Project name.")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * Short description of the project.
   **/
  @ApiModelProperty(value = "Short description of the project.")
  public String getShortDescription() {
    return shortDescription;
  }
  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  
  /**
   * Full description of the project.
   **/
  @ApiModelProperty(value = "Full description of the project.")
  public String getFullDescription() {
    return fullDescription;
  }
  public void setFullDescription(String fullDescription) {
    this.fullDescription = fullDescription;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public User getCreator() {
    return creator;
  }
  public void setCreator(User creator) {
    this.creator = creator;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<ProjectMember> getMembers() {
    return members;
  }
  public void setMembers(List<ProjectMember> members) {
    this.members = members;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<Issue> getIssues() {
    return issues;
  }
  public void setIssues(List<Issue> issues) {
    this.issues = issues;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Project {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  shortDescription: ").append(shortDescription).append("\n");
    sb.append("  fullDescription: ").append(fullDescription).append("\n");
    sb.append("  creator: ").append(creator).append("\n");
    sb.append("  members: ").append(members).append("\n");
    sb.append("  issues: ").append(issues).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
