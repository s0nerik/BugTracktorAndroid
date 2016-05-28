package com.github.sonerik.bugtracktor.models;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


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
