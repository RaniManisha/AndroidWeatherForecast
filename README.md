Introduction

Weather forecast Android app uses http://openweathermap.org/ API to retrieve current 5 days weather forecast weather data by City name and display it in a RecyclerView.

Installation 

This app was built with Android Studio version 3.2.1. Before building and running, please insert your own Open Weather Api key in  connectionManager.java by altering the  OPEN_WEATHER_API_KEY String.

Third party libraries used 

	•	Volley
	•	Glide

More features to  be included in future :

	•	Use a different View for each of the 5 days. At present all the days are just displayed in one big view - we really need a date selector or something like 5 scroll views which can be scrolled horizontally not vertically. This would mean we could fit all 5 days on screen. 
	•	Add junit test cases 
	•	Epoxy -  Android library for building complex screens in a RecyclerView
	•	Add a daily notification with the forecast of the day
	•	use TDD , MVVM with Live data
	•	Formatting is a thrown together mess - get this designed properly - ConstraintLayout 
	•	Detect GPS 
	•	Implement nice error popups
	•	Add more comments
	•	tons more...


Screenshots :

 ![image](https://github.com/RaniManisha/AndroidWeatherForecast/blob/master/app/docs/Weather_forecast_data.png)



![image](https://github.com/RaniManisha/AndroidWeatherForecast/blob/master/app/docs/City_name_invalid.png)



 ![image](https://github.com/RaniManisha/AndroidWeatherForecast/blob/master/app/docs/When_city_name_is_empty.png)
