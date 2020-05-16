package com.example.myblog.retrofit;

import java.util.HashMap;
import java.util.Map;

public class LoginResponse {
    private boolean success;
    private int user = 0;
    private Map<String, Object> additionProperties = new HashMap<String, Object>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Map<String, Object> getAdditionProperties() {
        return additionProperties;
    }

    public void setAdditionProperties(Map<String, Object> additionProperties) {
        this.additionProperties = additionProperties;
    }
}
