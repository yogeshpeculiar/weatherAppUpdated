package com.example.weatherappwithnav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Explore extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        File files = new File(getActivity().getFilesDir(), "text");
        if (!files.exists()) {
            files.mkdir();
        }
        File file = new File(files, "Response");


//        try {
//            File gpxfile = new File(file, "Response");
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.write("finally");
//            writer.close();
//
//            FileReader reader=new FileReader(gpxfile);
//          char [] c=new char[1024];
//                int charsRead = reader.read(c, 0, c.length);
//                Toast.makeText(getActivity(), String.valueOf(c), Toast.LENGTH_LONG).show();
//
//
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
        ArrayList<ListItem> listItems=new ArrayList<>();
        ArrayList<String> cities=new ArrayList<>();
        JSONObject jsonObject;
        try {
            if (file.length()>0){
                //Reading the city List
                FileReader reader = new FileReader(file);
            int size = (int) file.length();
            char[] c = new char[size];
            int charsRead = reader.read(c, 0, c.length);
            Toast.makeText(getActivity(), String.valueOf(c), Toast.LENGTH_LONG).show();
                jsonObject = new JSONObject(String.valueOf(c));
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i=2;i<jsonArray.length();i++) {
                    boolean isExists=false;
                    for(int j=0;j<i;j++) {
                        if (jsonArray.getJSONObject(i).getString("city").toLowerCase().indexOf(jsonArray.getJSONObject(j).getString("city").toLowerCase()) != -1) {
                            isExists = true;

                        }}
                    if(!isExists)
                        cities.add(jsonArray.getJSONObject(i).getString("city"));

                }
                Log.i("LOADING SECOND TIME","RETRIEVIN FRM THE MEMORY");
        }




            View view = inflater.inflate(R.layout.activity_explore, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity()); //to get the context use getActivity()
            recyclerView.setLayoutManager(layoutManager);
            if(cities.isEmpty()) {
                GetCities getCities = new GetCities();
//                cities = getCities.getListOfCities();   //getting the list of cities..
//                FileOutputStream fileOutputStream=new FileOutputStream("ApiResponseStorage.txt");
//
//                ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
//                objectOutputStream.writeObject(cities);
//                objectOutputStream.close();
//                Log.i("LOADING FIRST TIME","SAVING THE ARRAY LIST FIRST TIME");


                String response=getCities.giveRawResponse();
                jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i=2;i<jsonArray.length();i++) {
                    boolean isExists=false;
                    for(int j=0;j<i;j++) {
                        if (jsonArray.getJSONObject(i).getString("city").toLowerCase().indexOf(jsonArray.getJSONObject(j).getString("city").toLowerCase()) != -1) {
                            isExists = true;

                        }}
                    if(!isExists)
                        cities.add(jsonArray.getJSONObject(i).getString("city"));

                }
                FileWriter writer=new FileWriter(file);
                writer.write(response);
                writer.close();


            }
            GetTemp getTemp = new GetTemp();                          //getting the temperature
            for (int i = 0; i < cities.size(); i++) {
                ListItem listItem = new ListItem();
                Double temp = getTemp.getTempFromCityName(cities.get(i));
                if (temp == null) {                               //removing the city from the list if its temp cannot be found
                    cities.remove(i);
                    i--;
                } else {
                    listItem.setCity(cities.get(i));
                    listItem.setTemperature(String.valueOf(temp));
                    listItem.setmImageResource(R.mipmap.ic_launcher);
                    listItems.add(listItem);
//                   cities.set(i, cities.get(i) + "     " + String.valueOf(temp));
                }
            }
            mAdapter = new MyAdapter(getActivity(), listItems);
            recyclerView.setAdapter(mAdapter);

            return view;
        }
        catch (Exception e){
           e.printStackTrace();
            return null;
        }

    }


}
