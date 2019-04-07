package com.manisha.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manisha.weatherforecast.connection.ConnectionListener;
import com.manisha.weatherforecast.connection.ConnectionManager;
import com.manisha.weatherforecast.helper.WeatherAdapter;

import java.util.ArrayList;

/**
 * Created by Manisha on 07/04/2019.
 * A fragment containing our recycler view to be populated with weather data
 */
public class WeatherFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mStatus;
    private String mSearchByCity;
    private Button mDone;
    private EditText mEdtSearchByCity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mStatus = view.findViewById(R.id.tv_status);
        mDone = view.findViewById(R.id.btn_done);
        mRecyclerView = view.findViewById(R.id.recycler_view_for_weather);
        doneButtonClickListener(view);
    }

    private void doneButtonClickListener(final View view) {
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtSearchByCity = view.findViewById(R.id.edt_city);
                mSearchByCity = mEdtSearchByCity.getText().toString();

                weatherTextWatcher();

                //hide keyboard
                hideKeyboard(getActivity().getApplicationContext(), v);

                if (mSearchByCity.equals("")) {
                    status(getResources().getString(R.string.please_enter_valid_city_name), View.VISIBLE);
                    mRecyclerView.setAdapter(null);
                    return;
                }
                // set the required layout manager
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                makeGetCallToOpenWeatherAPI();
            }
        });
    }

    private void makeGetCallToOpenWeatherAPI() {
        // Get singleton connection manager
        ConnectionManager connectionManager = ConnectionManager.getInstance(getActivity().getApplicationContext(), mSearchByCity);
        connectionManager.setmCityBySearch(mSearchByCity);
        // make the GET call to the API passing in our success/failure listener
        connectionManager.GETWeather(new ConnectionListener<ArrayList>() {
            @Override
            public void onResult(ArrayList object) {
                onSuccess(object);
            }
        }, new ConnectionListener() {
            @Override
            public void onResult(Object object) {
                onError(object);
            }
        });
    }


    private void onSuccess(ArrayList object) {
        mStatus.setText("");
        mStatus.setVisibility(View.GONE);
        // Create an adapter using our result set
        WeatherAdapter weatherAdapter = new WeatherAdapter(getActivity(), object);
        // And then give it to the recycler view
        mRecyclerView.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();
    }

    private void onError(Object object) {
        if (object.toString().contains("com.android.volley.ClientError")) {
            status(getResources().getString(R.string.city_not_found), View.VISIBLE);
        } else if (object.toString().contains("com.android.volley.AuthFailureError")) {
            status(getResources().getString(R.string.please_put_your_weather_aPI_key_in_connectionManager), View.VISIBLE);
        }
        mRecyclerView.setAdapter(null);
    }

    private void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void status(String strMessage, int visibility) {
        mStatus.setVisibility(visibility);
        Toast.makeText(getActivity().getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        mStatus.setText(strMessage);
    }

    private void weatherTextWatcher() {
        mEdtSearchByCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStatus.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mStatus.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}