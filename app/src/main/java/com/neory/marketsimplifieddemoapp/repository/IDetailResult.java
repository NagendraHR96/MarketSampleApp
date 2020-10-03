package com.neory.marketsimplifieddemoapp.repository;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

public interface IDetailResult {
    public void notifySuccess(String requestType, JsonObject response);
    public void notifyError(String requestType, VolleyError error);
}
