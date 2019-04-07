package com.manisha.weatherforecast.helper;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void testGetInstance() {

        Utils util = new Utils();
        assert util.getDate("2019-04-07 17:00:00") != null;

    }
}



