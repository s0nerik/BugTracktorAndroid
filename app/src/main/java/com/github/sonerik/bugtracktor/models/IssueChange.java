package com.github.sonerik.bugtracktor.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Parcel(Parcel.Serialization.BEAN)
@ApiModel(description = "")
public class IssueChange  {
  
  @SerializedName("issue_id")
  private Integer issueId = null;
  @SerializedName("author_id")
  private Integer authorId = null;
  @SerializedName("date")
  private String date = null;
  @SerializedName("change")
  private String change = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getIssueId() {
    return issueId;
  }
  public void setIssueId(Integer issueId) {
    this.issueId = issueId;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getAuthorId() {
    return authorId;
  }
  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getChange() {
    return change;
  }
  public void setChange(String change) {
    this.change = change;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueChange issueChange = (IssueChange) o;
    return (issueId == null ? issueChange.issueId == null : issueId.equals(issueChange.issueId)) &&
        (authorId == null ? issueChange.authorId == null : authorId.equals(issueChange.authorId)) &&
        (date == null ? issueChange.date == null : date.equals(issueChange.date)) &&
        (change == null ? issueChange.change == null : change.equals(issueChange.change));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (issueId == null ? 0: issueId.hashCode());
    result = 31 * result + (authorId == null ? 0: authorId.hashCode());
    result = 31 * result + (date == null ? 0: date.hashCode());
    result = 31 * result + (change == null ? 0: change.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueChange {\n");
    
    sb.append("  issueId: ").append(issueId).append("\n");
    sb.append("  authorId: ").append(authorId).append("\n");
    sb.append("  date: ").append(date).append("\n");
    sb.append("  change: ").append(change).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
