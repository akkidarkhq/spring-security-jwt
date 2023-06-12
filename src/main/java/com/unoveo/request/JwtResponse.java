package com.unoveo.request;

public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String accessToken) {
        this.token = accessToken;
    }


    public String getType() {
        return type;
    }



    public void setType(String accessType) {
        this.type = accessType;
    }

}
