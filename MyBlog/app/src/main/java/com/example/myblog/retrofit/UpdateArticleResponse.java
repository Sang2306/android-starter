package com.example.myblog.retrofit;

public class UpdateArticleResponse {
    private boolean success;
    private boolean update;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
