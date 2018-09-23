package com.example.yashpandya.testyashpandya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.yashpandya.testyashpandya.Adapter.ListAdapter;
import com.example.yashpandya.testyashpandya.Adapter.PerformClick;
import com.example.yashpandya.testyashpandya.Model.GIPHYdataModel;
import com.example.yashpandya.testyashpandya.Presentator.GIPHYService;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GIPHYService.View,View.OnClickListener {
    @BindView(R.id.etsearch)
    EditText etsearch;
    @BindView(R.id.ivsearch)
    ImageView ivsearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    GIPHYService giphyService;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        giphyService = new GIPHYService(this);
                //Taking Input from EditText
        onclick();
    }

    public void onclick(){
        ivsearch.setOnClickListener(this);
    }
    public void callGIPHYService(String searchstring){
        giphyService.searchString(searchstring.toString());
    }
    @Override
    public void WriteResponse(ArrayList<GIPHYdataModel> modell) {
        adapter=new ListAdapter(this, modell, new PerformClick() {
            @Override
            public void performclick(View v, String MP4Url) {
                //PAssing MP4 Url to Exoplayer
                Intent intent=new Intent(getApplicationContext(),VideoPlayer.class);
                intent.putExtra("url",MP4Url);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivsearch:
                callGIPHYService(etsearch.getText().toString());
                break;
        }
    }
}
