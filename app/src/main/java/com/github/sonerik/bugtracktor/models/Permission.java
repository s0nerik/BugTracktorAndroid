package com.github.sonerik.bugtracktor.models;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class Permission  {
  
  @SerializedName("name")
  private String name = null;
  @SerializedName("description")
  private String description = null;

  
  /**
   * Unique permission name.
   **/
  @ApiModelProperty(value = "Unique permission name.")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * Permission description.
   **/
  @ApiModelProperty(value = "Permission description.")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Permission {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
