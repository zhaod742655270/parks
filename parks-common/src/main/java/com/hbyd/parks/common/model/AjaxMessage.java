package com.hbyd.parks.common.model;
import java.util.List;

/**
 * Created by len on 14-7-15.
 */
public class AjaxMessage<T> {
    private Boolean success;
    private String message;
    private List<T> data;

    public AjaxMessage() {
        success = true;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
