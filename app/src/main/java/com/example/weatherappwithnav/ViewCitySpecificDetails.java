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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

//public class ViewCitySpecificDetails extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view= super.onCreateView(inflater, container, savedInstanceState);
//        Intent intent=getActivity().getIntent();
//        String cityName=intent.getStringExtra("selectedCity");
//        String[] city=cityName.split(" ");
//        final TextView lattitude = (TextView)view. findViewById(R.id.lattitude);
//        final TextView longitude = (TextView) view.findViewById(R.id.longitude);
//        final TextView temperature = (TextView) view.findViewById(R.id.temp);
//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city[0] + "&appid=ac7f8536f0280705e0a21ca710c76f56";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONObject coord = response.getJSONObject("coord");
//                            lattitude.setText("Latitude :   "+String.valueOf(coord.get("lat")));
//                            longitude.setText("Longitude :   "+String.valueOf(coord.get("lon")));
//                            JSONObject temp = response.getJSONObject("main");
//                            temperature.setText("Temperature :  "+String.valueOf(Math.round((Double.parseDouble(temp.getString("temp"))-273.15))));
//
//
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        lattitude.setText("error man..." + error.getMessage() + "---->" );
//                    }
//                });
//
//// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
//
//        return view;
//    }
//}
public class ViewCitySpecificDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_city_specific_details);
        Intent  intent=getIntent();
        String cityName=intent.getStringExtra("selectedCity");
        String[] city=cityName.split(" ");
        final TextView lattitude = (TextView) findViewById(R.id.lattitude);
        final TextView longitude = (TextView) findViewById(R.id.longitude);
        final TextView temperature = (TextView) findViewById(R.id.temp);

        //hittin the API...wowww..
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city[0] + "&appid=ac7f8536f0280705e0a21ca710c76f56";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject coord = response.getJSONObject("coord");
                            lattitude.setText("Latitude :   "+String.valueOf(coord.get("lat")));
                            longitude.setText("Longitude :   "+String.valueOf(coord.get("lon")));
                            JSONObject temp = response.getJSONObject("main");
                            temperature.setText("Temperature :  "+String.valueOf(Math.round((Double.parseDouble(temp.getString("temp"))-273.15))));



                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        lattitude.setText("error man..." + error.getMessage() + "---->" );
                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
