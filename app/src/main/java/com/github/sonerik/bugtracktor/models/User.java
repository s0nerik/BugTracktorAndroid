package com.github.sonerik.bugtracktor.models;


import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class User  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("email")
  private String email = null;
  @SerializedName("password")
  private String password = null;
  @SerializedName("nickname")
  private String nickname = null;
  @SerializedName("real_name")
  private String realName = null;
  @SerializedName("avatar_url")
  private String avatarUrl = null;

  
  /**
   * Unique user identifier.
   **/
  @ApiModelProperty(value = "Unique user identifier.")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
  /**
   * User's email.
   **/
  @ApiModelProperty(required = true, value = "User's email.")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   * User's password.
   **/
  @ApiModelProperty(required = true, value = "User's password.")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  
  /**
   * User's nickname.
   **/
  @ApiModelProperty(value = "User's nickname.")
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  
  /**
   * User's real name.
   **/
  @ApiModelProperty(value = "User's real name.")
  public String getRealName() {
    return realName;
  }
  public void setRealName(String realName) {
    this.realName = realName;
  }

  
  /**
   * User's avatar URL.
   **/
  @ApiModelProperty(value = "User's avatar URL.")
  public String getAvatarUrl() {
    return avatarUrl;
  }
  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  email: ").append(email).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("  nickname: ").append(nickname).append("\n");
    sb.append("  realName: ").append(realName).append("\n");
    sb.append("  avatarUrl: ").append(avatarUrl).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
