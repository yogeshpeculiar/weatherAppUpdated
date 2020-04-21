package com.example.weatherappwithnav;

import android.os.AsyncTask;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetTemp {
    public Double getTempFromCityName(String requestedCityName) {


    String response = "";
    GetTempFromApi getTempFromApi = new GetTempFromApi();
        JSONObject jsonObject=null;
        JSONObject temp=null;
        try {
    response = getTempFromApi.execute("https://api.openweathermap.org/data/2.5/weather?q=" + requestedCityName + "&appid=ac7f8536f0280705e0a21ca710c76f56").get();

     jsonObject = new JSONObject(response);
     temp = jsonObject.getJSONObject("main");
     Double cityTemperature=Double.parseDouble(temp.getString("temp"))-273.15;
     cityTemperature=Math.round(cityTemperature * 100.0) / 100.0;
    return cityTemperature;
}
catch (Exception e)
{

}
    return null;
}
public JSONObject getTempApiResponse(String cityName){
    String response = "";
    GetTempFromApi getTempFromApi = new GetTempFromApi();
    JSONObject jsonObject=null;
    JSONObject temp=null;
    try {
        response = getTempFromApi.execute("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=ac7f8536f0280705e0a21ca710c76f56").get();

        jsonObject = new JSONObject(response);
        return jsonObject;
}
    catch (Exception e){

    }
    return null;
    }
    public class GetTempFromApi extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int data = isr.read();
                String content = "";
                char ch;
                while(data != -1){
                    ch = (char) data;
                    content = content + ch;
                    data  = isr.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
