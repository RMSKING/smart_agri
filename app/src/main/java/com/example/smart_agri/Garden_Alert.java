package com.example.smart_agri;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Garden_Alert extends AppCompatActivity {

    private EditText cityEditText;
    private Button fetchWeatherButton;
    private TextView alertsTextView;
    private ImageView alertImageView;

    // Replace with your OpenWeatherMap API key
    private static final String API_KEY = "cbac092f192014ba5a26b90ecab6abba";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_alert);

        cityEditText = findViewById(R.id.cityEditText);
        fetchWeatherButton = findViewById(R.id.fetchWeatherButton);
        alertsTextView = findViewById(R.id.alertsTextView);
        alertImageView = findViewById(R.id.alertImageView);

        fetchWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEditText.getText().toString();
                new FetchWeatherTask().execute(city);
            }
        });
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String city = params[0];
            String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY;

            try {
                // Make an API request to fetch weather data for the specified city
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String weatherData = response.body().string();

                // Parse the weather data and generate garden maintenance alerts
                String alerts = generateGardenMaintenanceAlerts(weatherData);
                return alerts;
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to fetch weather data. Check your API key and location.";
            }
        }

        private String generateGardenMaintenanceAlerts(String weatherData) {
            try {
                // Parse the JSON weather data to retrieve relevant information
                JSONObject jsonObject = new JSONObject(weatherData);
                JSONObject main = jsonObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                int humidity = main.getInt("humidity");

                // Check temperature and humidity to generate alerts
                StringBuilder alerts = new StringBuilder("Garden Maintenance Alerts:\n");

                if (temperature < 10) {
                    alerts.append("- Low temperature: Consider providing heat.\n");
                    setRelevantImage(R.drawable.cold_image);
                } else if (temperature > 30) {
                    alerts.append("- High temperature: Provide shade or water plants.\n");
                    setRelevantImage(R.drawable.hot_image);
                }

                if (humidity < 30) {
                    alerts.append("- Low humidity: Water the garden to increase humidity.\n");
                    setRelevantImage(R.drawable.low_humidity_image);
                } else if (humidity > 70) {
                    alerts.append("- High humidity: Ensure proper ventilation.\n");
                    setRelevantImage(R.drawable.high_humidity_image);
                }

                // Set the image based on the garden maintenance alerts


                return alerts.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return "Failed to parse weather data.";
            }
        }

        private void setRelevantImage(int resourceId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertImageView.setImageResource(resourceId);
                }
            });
        }

        @Override
        protected void onPostExecute(String result) {
            alertsTextView.setText(result);
            alertsTextView.setVisibility(View.VISIBLE);
        }
    }
}
