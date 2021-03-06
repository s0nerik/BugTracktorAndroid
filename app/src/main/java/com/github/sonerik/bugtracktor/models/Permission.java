package com.github.sonerik.bugtracktor.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Parcel(Parcel.Serialization.BEAN)
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Permission permission = (Permission) o;
    return (name == null ? permission.name == null : name.equals(permission.name)) &&
        (description == null ? permission.description == null : description.equals(permission.description));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (name == null ? 0: name.hashCode());
    result = 31 * result + (description == null ? 0: description.hashCode());
    return result;
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
