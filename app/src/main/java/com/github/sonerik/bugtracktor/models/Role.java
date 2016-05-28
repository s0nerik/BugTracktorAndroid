package com.github.sonerik.bugtracktor.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class Role  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("description")
  private String description = null;
  @SerializedName("permissions")
  private List<Permission> permissions = null;

  
  /**
   * Unique role identifier.
   **/
  @ApiModelProperty(value = "Unique role identifier.")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
  /**
   * Role name.
   **/
  @ApiModelProperty(value = "Role name.")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * Role description.
   **/
  @ApiModelProperty(value = "Role description.")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<Permission> getPermissions() {
    return permissions;
  }
  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Role {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  permissions: ").append(permissions).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
