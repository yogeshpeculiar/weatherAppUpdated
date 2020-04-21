package com.example.weatherappwithnav;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Search extends Fragment {
    TextView lattitude,longitude,temperature;
    EditText cityName;
    Button getInformation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_search, container, false);
         cityName=(EditText)view.findViewById(R.id.cityName);
        lattitude = (TextView) view.findViewById(R.id.lattitude);
         longitude = (TextView) view.findViewById(R.id.longitude);
        temperature = (TextView) view.findViewById(R.id.temperature);
        getInformation=(Button)view.findViewById(R.id.getInfo);
        getInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String requestedCityName=cityName.getText().toString();
                GetTemp getTemp=new GetTemp();
                try {
                    JSONObject response = getTemp.getTempApiResponse(requestedCityName);
                    JSONObject coord = response.getJSONObject("coord");
                    lattitude.setText("Latitiude    "+String.valueOf(coord.get("lat")));
                    longitude.setText("Longitude    "+String.valueOf(coord.get("lon")));
                    JSONObject temp = response.getJSONObject("main");
                    temperature.setText("Temperature    "+temp.getString("temp"));
                }
                catch (Exception e){

                }
            }
        });

        return view;

    }

}
