package com.neory.marketsimplifieddemoapp.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.neory.marketsimplifieddemoapp.repository.IResult;
import com.neory.marketsimplifieddemoapp.repository.VolleyService;
import com.neory.marketsimplifieddemoapp.ui.model.DetailJsonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailViewModel extends AndroidViewModel {
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;
    private MutableLiveData<DetailJsonResult> jsonObject;

    private String detailsURL;

    public String getDetailsURL() {
        return detailsURL;
    }

    public void setDetailsURL(String detailsURL) {
        this.detailsURL = detailsURL;
    }

    public DetailViewModel(@NonNull Application application) {
        super(application);
            jsonObject = new MutableLiveData<>();;
            initVolleyCallback();
            mVolleyService = new VolleyService(mResultCallback,application);
        }

    public MutableLiveData<DetailJsonResult> getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(MutableLiveData<DetailJsonResult> jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void getUserDetails() {
       mVolleyService.getUserDetails("GETCALL",detailsURL);
    }
    void initVolleyCallback() {
        mResultCallback = new IResult() {

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

            }

            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                DetailJsonResult contactList = new DetailJsonResult();
                try {
                    contactList.setName(response.getString("name"));
                    contactList.setAvatar_url(response.getString("avatar_url"));
                    contactList.setEmail(response.getString("email"));
                    contactList.setType(response.getString("type"));
                    contactList.setLocation(response.getString("location"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonObject.setValue(contactList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

}
