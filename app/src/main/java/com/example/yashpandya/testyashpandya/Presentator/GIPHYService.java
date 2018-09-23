package com.example.yashpandya.testyashpandya.Presentator;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.yashpandya.testyashpandya.Adapter.ListAdapter;
import com.example.yashpandya.testyashpandya.MainActivity;
import com.example.yashpandya.testyashpandya.Model.GIPHYdataModel;
import com.example.yashpandya.testyashpandya.R;
import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.network.api.CompletionHandler;
import com.giphy.sdk.core.network.api.GPHApi;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.giphy.sdk.core.network.response.ListMediaResponse;

import java.util.ArrayList;

public class GIPHYService  {
    String APIKEY = "T5FDwOqr6zB7GlSIccNQ3E9J6fgmJckW";
    GPHApi client = new GPHApiClient(APIKEY);
    GIPHYdataModel model;
    View view;
    ArrayList<GIPHYdataModel> dataList;
    ListAdapter adapter;

    public GIPHYService(View view) {
        this.view = view;
    }

    public void searchString(String search) {
        client.search(search, MediaType.gif, 20, null, null, null, new CompletionHandler<ListMediaResponse>() {
            @Override
            public void onComplete(ListMediaResponse result, Throwable e) {
                if (result == null) {
                    // Do what you want to do with the error
                } else {
                    if (result.getData() != null) {
                        dataList = new ArrayList<>();
                        for (Media gif : result.getData()) {
                            Log.v("giphy", gif.getImages().getOriginal().getGifUrl());
                            Log.v("video", gif.getImages().getOriginal().getMp4Url());
                            model = new GIPHYdataModel();
                            model.setGIfUrl(gif.getImages().getOriginal().getGifUrl());
                            model.setMp4Url(gif.getImages().getOriginal().getMp4Url());
                            dataList.add(model);
                        }
                        view.WriteResponse(new ArrayList<GIPHYdataModel>(dataList));
                    } else {
                        Log.e("giphy error", "No results found");
                    }
                }
            }
        });
    }

    public interface View {
        void WriteResponse(ArrayList<GIPHYdataModel> modell);}
}
