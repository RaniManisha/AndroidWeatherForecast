package com.manisha.weatherforecast.connection;

/**
 * Created by Manisha on 07/04/2019.
 */

public interface ConnectionListener<T> {
    void onResult(T object);

}
