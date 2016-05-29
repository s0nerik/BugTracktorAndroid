package com.github.sonerik.bugtracktor.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class Issue  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("issue_index")
  private Integer issueIndex = null;
  @SerializedName("author")
  private User author = null;
  @SerializedName("project")
  private Project project = null;
  @SerializedName("type")
  private IssueType type = null;
  @SerializedName("history")
  private List<IssueChange> history = null;
  @SerializedName("short_description")
  private String shortDescription = null;
  @SerializedName("full_description")
  private String fullDescription = null;
  @SerializedName("creation_date")
  private Date creationDate = null;

  
  /**
   * Unique issue identifier.
   **/
  @ApiModelProperty(value = "Unique issue identifier.")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
  /**
   * Index of the issue inside the project.
   **/
  @ApiModelProperty(value = "Index of the issue inside the project.")
  public Integer getIssueIndex() {
    return issueIndex;
  }
  public void setIssueIndex(Integer issueIndex) {
    this.issueIndex = issueIndex;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public User getAuthor() {
    return author;
  }
  public void setAuthor(User author) {
    this.author = author;
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
  public IssueType getType() {
    return type;
  }
  public void setType(IssueType type) {
    this.type = type;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<IssueChange> getHistory() {
    return history;
  }
  public void setHistory(List<IssueChange> history) {
    this.history = history;
  }

  
  /**
   * Short description of the issue.
   **/
  @ApiModelProperty(value = "Short description of the issue.")
  public String getShortDescription() {
    return shortDescription;
  }
  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  
  /**
   * Full description of the issue.
   **/
  @ApiModelProperty(value = "Full description of the issue.")
  public String getFullDescription() {
    return fullDescription;
  }
  public void setFullDescription(String fullDescription) {
    this.fullDescription = fullDescription;
  }

  
  /**
   * Date when issue was created.
   **/
  @ApiModelProperty(value = "Date when issue was created.")
  public Date getCreationDate() {
    return creationDate;
  }
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Issue {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  issueIndex: ").append(issueIndex).append("\n");
    sb.append("  author: ").append(author).append("\n");
    sb.append("  project: ").append(project).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("  history: ").append(history).append("\n");
    sb.append("  shortDescription: ").append(shortDescription).append("\n");
    sb.append("  fullDescription: ").append(fullDescription).append("\n");
    sb.append("  creationDate: ").append(creationDate).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
