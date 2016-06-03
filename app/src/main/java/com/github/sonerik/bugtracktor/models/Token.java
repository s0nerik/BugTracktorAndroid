package com.github.sonerik.bugtracktor.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Parcel(Parcel.Serialization.BEAN)
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Token token = (Token) o;
    return (token == null ? token.token == null : token.equals(token.token));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (token == null ? 0: token.hashCode());
    return result;
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
