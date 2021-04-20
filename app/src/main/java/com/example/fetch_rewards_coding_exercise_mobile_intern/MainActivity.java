package com.example.fetch_rewards_coding_exercise_mobile_intern;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecyclerViewAdapter(dataArrayList, MainActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<List<MainData>> call = api.getData();
        call.enqueue(new Callback<List<MainData>>() {
            @Override
            public void onResponse (Call<List<MainData>> call, Response<List<MainData>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<MainData> postList = response.body();
                // Filter out any items where "name" is blank or null.
                List<MainData> tempList = new ArrayList<>();
                for(MainData data :postList)
                {

                    if(null!= data.getName() && !data.getName().isEmpty()) {


                        //sort by name
                        Collections.sort(tempList, (o1, o2) -> {
                            // splitting name with respect to white space and setting the integer part in a local variable to compare
                            Integer n1 = Integer.parseInt(o1.getName().split(" ")[1]);
                            Integer n2 = Integer.parseInt(o2.getName().split(" ")[1]);
                            return n1.compareTo(n2);
                        });
                        //sort by ListId
                        Collections.sort(tempList, (mainData, t1) -> mainData.getListId().compareTo(t1.getListId()) );


                        //sort by id
                        // Collections.sort(tempList, (mainData, t1) -> Integer.parseInt(mainData.getId())-Integer.parseInt(t1.getId()));

                        tempList.add(data);

                    }
                }

                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(tempList, MainActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure (Call<List<MainData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
