package com.manisha.weatherforecast.connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.manisha.weatherforecast.helper.WeatherItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Manisha on 07/04/2019.
 */
public class ConnectionManager {

    private static ConnectionManager instance;
    private static Context context;
    // The Open weather API Key
    private String OPEN_WEATHER_API_KEY = "put yours API key here";
    private String mApiSearchByCityFormat = "http://api.openweathermap.org/data/2.5/forecast?q=%s&mode=json&units=metric&appid=" + OPEN_WEATHER_API_KEY;
    private String mApiUrl;
    private RequestQueue requestQueue;
    private String mCityBySearch;

    private ConnectionManager(Context context, String cityBySearch) {
        ConnectionManager.context = context;
        requestQueue = getRequestQueue();
        this.mCityBySearch = cityBySearch;
    }

    public static synchronized ConnectionManager getInstance(Context context, String citySearch) {
        if (instance == null) {
            instance = new ConnectionManager(context, citySearch);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private String makeUrl() {
        if (mCityBySearch != null) {
            this.mApiUrl = String.format(mApiSearchByCityFormat, mCityBySearch);
        }
        return this.mApiUrl;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Make a GET call to the OpenWeather 5 day forecast
     *
     * @param okListener
     * @param errorListener
     */
    public void GETWeather(final ConnectionListener<ArrayList> okListener, final ConnectionListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, makeUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parse json response
                    ArrayList result = parseWeatherObject(response);

                    okListener.onResult(result);
                } catch (JSONException e) {
                    errorListener.onResult(null);

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onResult(error);
            }
        }
        );

        addToRequestQueue(jsObjRequest);
    }

    /**
     * Given the json response, build an arraylist
     *
     * @param json
     * @return ArrayList
     * @throws JSONException
     */
    private ArrayList parseWeatherObject(JSONObject json) throws JSONException {
        ArrayList arrayList = new ArrayList();

        //getting the list node from the json
        JSONArray list = json.getJSONArray("list");

        // Now iterate through each one creating our data structure and grabbing the info we need
        for (int i = 0; i < list.length(); i++) {
            // Create a new instance of WeatherItem
            WeatherItem weatherItem = new WeatherItem();

            JSONObject weatherData = list.getJSONObject(i);
            weatherItem.temp_max = weatherData.getJSONObject("main").getDouble("temp_max");
            weatherItem.temp_min = weatherData.getJSONObject("main").getDouble("temp_min");
            weatherItem.humidity = weatherData.getJSONObject("main").getDouble("humidity");
            weatherItem.windSpeed = weatherData.getJSONObject("wind").getDouble("speed");

            // pull out the date and put it in our own data
            weatherItem.date = weatherData.getString("dt_txt");

            // Now go for the weather object
            JSONArray weatherArray = weatherData.getJSONArray("weather");
            JSONObject ob = (JSONObject) weatherArray.get(0);

            weatherItem.description = ob.getString("description");
            weatherItem.icon = ob.getString("icon");

            arrayList.add(weatherItem);
        }
        return arrayList;
    }

    public void setmCityBySearch(String mCityBySearch) {
        this.mCityBySearch = mCityBySearch;
    }
}
