package com.github.sonerik.bugtracktor.models;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class Token  {
  
  @SerializedName("token")
  private String token = null;

  
  /**
   * User's temporary access token.
   **/
  @ApiModelProperty(required = true, value = "User's temporary access token.")
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Token {\n");
    
    sb.append("  token: ").append(token).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
