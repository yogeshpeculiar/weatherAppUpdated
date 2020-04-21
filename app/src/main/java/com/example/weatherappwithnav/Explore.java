package com.example.weatherappwithnav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Explore extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
ArrayList<ListItem> listItems=new ArrayList<>();
        View view =inflater.inflate(R.layout.activity_explore, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity()); //to get the context use getActivity()
        recyclerView.setLayoutManager(layoutManager);
        GetCities getCities = new GetCities();
        ArrayList<String> cities = getCities.getListOfCities();   //getting the list of cities..
        GetTemp getTemp = new GetTemp();                          //getting the temperature
        for (int i = 0; i < cities.size(); i++) {
            ListItem listItem=new ListItem();
            Double temp = getTemp.getTempFromCityName(cities.get(i));
            if (temp == null) {                               //removing the city from the list if its temp cannot be found
                cities.remove(i);
                i--;
            } else
               {
                   listItem.setCity(cities.get(i));
                   listItem.setTemperature(String.valueOf(temp));
                   listItem.setmImageResource(R.mipmap.ic_launcher);
                   listItems.add(listItem);
//                   cities.set(i, cities.get(i) + "     " + String.valueOf(temp));
               }
        }
        mAdapter = new MyAdapter(getActivity(),listItems);
        recyclerView.setAdapter(mAdapter);

        return view;

    }

}
