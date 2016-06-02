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
  @SerializedName("is_opened")
  private Boolean isOpened = null;
  @SerializedName("is_active")
  private Boolean isActive = null;
  @SerializedName("author")
  private User author = null;
  @SerializedName("assignees")
  private List<User> assignees = null;
  @SerializedName("attachments")
  private List<IssueAttachment> attachments = null;
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
   * Whether the issue is opened for discussion.
   **/
  @ApiModelProperty(value = "Whether the issue is opened for discussion.")
  public Boolean getIsOpened() {
    return isOpened;
  }
  public void setIsOpened(Boolean isOpened) {
    this.isOpened = isOpened;
  }

  /**
   * Whether the issue should be visible for project members.
   **/
  @ApiModelProperty(value = "Whether the issue should be visible for project members.")
  public Boolean getIsActive() {
    return isActive;
  }
  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
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
  public List<User> getAssignees() {
    return assignees;
  }
  public void setAssignees(List<User> assignees) {
    this.assignees = assignees;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<IssueAttachment> getAttachments() {
    return attachments;
  }
  public void setAttachments(List<IssueAttachment> attachments) {
    this.attachments = attachments;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Issue issue = (Issue) o;
    return (id == null ? issue.id == null : id.equals(issue.id)) &&
        (issueIndex == null ? issue.issueIndex == null : issueIndex.equals(issue.issueIndex)) &&
        (isOpened == null ? issue.isOpened == null : isOpened.equals(issue.isOpened)) &&
        (isActive == null ? issue.isActive == null : isActive.equals(issue.isActive)) &&
        (author == null ? issue.author == null : author.equals(issue.author)) &&
        (assignees == null ? issue.assignees == null : assignees.equals(issue.assignees)) &&
        (attachments == null ? issue.attachments == null : attachments.equals(issue.attachments)) &&
        (project == null ? issue.project == null : project.equals(issue.project)) &&
        (type == null ? issue.type == null : type.equals(issue.type)) &&
        (history == null ? issue.history == null : history.equals(issue.history)) &&
        (shortDescription == null ? issue.shortDescription == null : shortDescription.equals(issue.shortDescription)) &&
        (fullDescription == null ? issue.fullDescription == null : fullDescription.equals(issue.fullDescription)) &&
        (creationDate == null ? issue.creationDate == null : creationDate.equals(issue.creationDate));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (id == null ? 0: id.hashCode());
    result = 31 * result + (issueIndex == null ? 0: issueIndex.hashCode());
    result = 31 * result + (isOpened == null ? 0: isOpened.hashCode());
    result = 31 * result + (isActive == null ? 0: isActive.hashCode());
    result = 31 * result + (author == null ? 0: author.hashCode());
    result = 31 * result + (assignees == null ? 0: assignees.hashCode());
    result = 31 * result + (attachments == null ? 0: attachments.hashCode());
    result = 31 * result + (project == null ? 0: project.hashCode());
    result = 31 * result + (type == null ? 0: type.hashCode());
    result = 31 * result + (history == null ? 0: history.hashCode());
    result = 31 * result + (shortDescription == null ? 0: shortDescription.hashCode());
    result = 31 * result + (fullDescription == null ? 0: fullDescription.hashCode());
    result = 31 * result + (creationDate == null ? 0: creationDate.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Issue {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  issueIndex: ").append(issueIndex).append("\n");
    sb.append("  isOpened: ").append(isOpened).append("\n");
    sb.append("  isActive: ").append(isActive).append("\n");
    sb.append("  author: ").append(author).append("\n");
    sb.append("  assignees: ").append(assignees).append("\n");
    sb.append("  attachments: ").append(attachments).append("\n");
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
