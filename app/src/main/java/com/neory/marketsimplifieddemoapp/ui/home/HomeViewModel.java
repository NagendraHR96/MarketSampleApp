package com.neory.marketsimplifieddemoapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neory.marketsimplifieddemoapp.repository.IResult;
import com.neory.marketsimplifieddemoapp.repository.VolleyService;
import com.neory.marketsimplifieddemoapp.ui.model.JsonObjectResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<JsonObjectResult>>jsonObject;
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        jsonObject = new MutableLiveData<>();
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,application);
    }

    public LiveData<List<JsonObjectResult>>getText() {
        return jsonObject;
    }

    public void getUserDetails() {
        mVolleyService.getDataVolley("GETCALL","https://api.github.com/repositories");
    }
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONArray response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<JsonObjectResult>>(){}.getType();
                List<JsonObjectResult> contactList = gson.fromJson(String.valueOf(response), type);
              jsonObject.setValue(contactList);
            }

            @Override
            public void notifySuccess(String requestType, JSONObject response) {

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }
}