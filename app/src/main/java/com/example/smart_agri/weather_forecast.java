package com.example.smart_agri;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class weather_forecast extends AppCompatActivity {

    String api_keys = "cbac092f192014ba5a26b90ecab6abba";
    String City = "coimbatore";
    String url = "https://api.openweathermap.org/data/2.5/weather?q="+City+"&units=metric&appid="+api_keys;
    TextView txtcity,txtTime,txtvalueFelLike,txtvakluehumidity,txtvision,textTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        txtcity = findViewById(R.id.txtcity);
        txtTime = findViewById(R.id.txtTime);
        txtvalueFelLike = findViewById(R.id.txtvalue1);
        txtvakluehumidity = findViewById(R.id.txthumidity);
        txtvision = findViewById(R.id.txtvision);
        textTemp = findViewById(R.id.txtvalue);

        new DownloadJSON().execute(url);
    }

    public class DownloadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpURLConnection httpurlconnection = null;
            InputStream inputstream = null;
            InputStreamReader inputstreamreader = null;
            try {
                URL url = new URL(strings[0]);
                httpurlconnection = (HttpURLConnection) url.openConnection();
                inputstream = httpurlconnection.getInputStream();
                inputstreamreader = new InputStreamReader(inputstream);
                BufferedReader reader = new BufferedReader(inputstreamreader);
                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpurlconnection != null) {
                    httpurlconnection.disconnect();
                }
                try {
                    if (inputstreamreader != null) {
                        inputstreamreader.close();
                    }
                    if (inputstream != null) {
                        inputstream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String temp = jsonObject.getJSONObject("main").getString("temp");
                String humidity = jsonObject.getJSONObject("main").getString("humidity");
                String feel_Like = jsonObject.getJSONObject("main").getString("feels_like");
                String visibility = jsonObject.getString("visibility");
                Long time = jsonObject.getLong("dt");
                String sTime = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH)
                        .format(new Date(time * 1000));

                txtTime.setText(sTime);
                txtcity.setText(City);
                txtvision.setText(visibility);
                txtvalueFelLike.setText(feel_Like);
                txtvakluehumidity.setText(humidity);
                textTemp.setText(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    }



