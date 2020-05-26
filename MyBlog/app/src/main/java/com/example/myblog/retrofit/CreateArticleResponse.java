package com.example.myblog.retrofit;

public class CreateArticleResponse {
    private boolean success;
    private boolean created;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
