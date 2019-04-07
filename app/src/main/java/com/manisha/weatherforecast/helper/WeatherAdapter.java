package com.manisha.weatherforecast.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manisha.weatherforecast.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Manisha on 07/04/2019.
 * An adapter used to populate data in recyclerview
 */
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String mDATE_FORMAT = "dd MMM yyyy hh.mm aa";
    private static final String mHUMIDITY = "Humidity ";
    private static final String mWIND_SPEED = "Wind Speed ";
    private ArrayList<WeatherItem> data;
    private Context context;
    private LayoutInflater inflater;
    private String apiUrlIcon = "http://openweathermap.org/img/w/";


    public WeatherAdapter(Context context, ArrayList<WeatherItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.weather_item, parent, false);
        WeatherItemHolder itemholder = new WeatherItemHolder(view);
        return itemholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        WeatherItemHolder weatherItemHolder = (WeatherItemHolder) holder;

        // Get the data
        WeatherItem data = this.data.get(position);

        // Get our weather date in Date format
        Date date = Utils.getDate(data.date);
        SimpleDateFormat format = new SimpleDateFormat(mDATE_FORMAT);
        String displayDate = format.format(date);
        // put values in the holder
        weatherItemHolder.textDateTime.setText(displayDate);
        weatherItemHolder.textDescription.setText(data.description);
        weatherItemHolder.textTemp_max.setText(String.valueOf(data.temp_max) + " \u2103");
        weatherItemHolder.textTemp_min.setText(String.valueOf(data.temp_min) + " \u2103");
        weatherItemHolder.textHumidity.setText(mHUMIDITY + String.valueOf(data.humidity + "%"));
        weatherItemHolder.textWindSpeed.setText(mWIND_SPEED + String.valueOf(data.windSpeed + "KPH"));

        // Including the weather image created from the icon - use glide for this
        Glide.with(context).load(apiUrlIcon + data.icon + ".png")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(weatherItemHolder.imageIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * A view holder for each of our weather items
     */
    class WeatherItemHolder extends RecyclerView.ViewHolder {
        LinearLayout weatherLayout;
        TextView textDateTime;
        TextView textDescription;
        TextView textTemp_max;
        TextView textTemp_min;
        TextView textHumidity;
        TextView textWindSpeed;
        ImageView imageIcon;

        WeatherItemHolder(View itemView) {
            super(itemView);

            weatherLayout = itemView.findViewById(R.id.ll_weather_layout);
            textDateTime = itemView.findViewById(R.id.tv_date_time);
            textDescription = itemView.findViewById(R.id.tv_description);
            textTemp_max = itemView.findViewById(R.id.tv_temp_max);
            textTemp_min = itemView.findViewById(R.id.tv_temp_min);
            textHumidity = itemView.findViewById(R.id.tv_humidity);
            textWindSpeed = itemView.findViewById(R.id.tv_wind_speed);
            imageIcon = itemView.findViewById(R.id.iv_weather);
        }
    }

}




