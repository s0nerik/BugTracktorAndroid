package com.github.sonerik.bugtracktor.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Parcel(Parcel.Serialization.BEAN)
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role = (Role) o;
    return (id == null ? role.id == null : id.equals(role.id)) &&
        (name == null ? role.name == null : name.equals(role.name)) &&
        (description == null ? role.description == null : description.equals(role.description)) &&
        (permissions == null ? role.permissions == null : permissions.equals(role.permissions));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (id == null ? 0: id.hashCode());
    result = 31 * result + (name == null ? 0: name.hashCode());
    result = 31 * result + (description == null ? 0: description.hashCode());
    result = 31 * result + (permissions == null ? 0: permissions.hashCode());
    return result;
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
