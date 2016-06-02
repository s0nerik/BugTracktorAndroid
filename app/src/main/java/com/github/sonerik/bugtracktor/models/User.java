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
  @ApiModelProperty(value = "User's email.")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * User's password.
   **/
  @ApiModelProperty(value = "User's password.")
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return (id == null ? user.id == null : id.equals(user.id)) &&
        (email == null ? user.email == null : email.equals(user.email)) &&
        (password == null ? user.password == null : password.equals(user.password)) &&
        (nickname == null ? user.nickname == null : nickname.equals(user.nickname)) &&
        (realName == null ? user.realName == null : realName.equals(user.realName)) &&
        (avatarUrl == null ? user.avatarUrl == null : avatarUrl.equals(user.avatarUrl));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (id == null ? 0: id.hashCode());
    result = 31 * result + (email == null ? 0: email.hashCode());
    result = 31 * result + (password == null ? 0: password.hashCode());
    result = 31 * result + (nickname == null ? 0: nickname.hashCode());
    result = 31 * result + (realName == null ? 0: realName.hashCode());
    result = 31 * result + (avatarUrl == null ? 0: avatarUrl.hashCode());
    return result;
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
