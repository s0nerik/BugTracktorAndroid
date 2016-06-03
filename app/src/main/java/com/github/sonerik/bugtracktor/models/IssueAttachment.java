package com.github.sonerik.bugtracktor.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Parcel(Parcel.Serialization.BEAN)
@ApiModel(description = "")
public class IssueAttachment  {
  
  @SerializedName("issue_id")
  private Integer issueId = null;
  @SerializedName("url")
  private String url = null;

  /**
   * Unique issue id this attachment belongs to.
   **/
  @ApiModelProperty(value = "Unique issue id this attachment belongs to.")
  public Integer getIssueId() {
    return issueId;
  }
  public void setIssueId(Integer issueId) {
    this.issueId = issueId;
  }

  /**
   * Attachment url.
   **/
  @ApiModelProperty(value = "Attachment url.")
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueAttachment issueAttachment = (IssueAttachment) o;
    return (issueId == null ? issueAttachment.issueId == null : issueId.equals(issueAttachment.issueId)) &&
        (url == null ? issueAttachment.url == null : url.equals(issueAttachment.url));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (issueId == null ? 0: issueId.hashCode());
    result = 31 * result + (url == null ? 0: url.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueAttachment {\n");
    
    sb.append("  issueId: ").append(issueId).append("\n");
    sb.append("  url: ").append(url).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
