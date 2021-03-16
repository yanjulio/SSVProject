package com.soft.ssvapp.DataRetrofit.UshindiData;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("id")
    private int Id;
    @SerializedName("toSave")
    private String ToSave;

    public UserResponse(String toSave) {
        ToSave = toSave;
    }

    public int getId() {
        return Id;
    }

    public String getToSave() {
        return ToSave;
    }
}
