package com.juvera.androidcountry.core;

public interface Observer {
    void setStatus(int strId);
    void setCountryInfo(CountryInfo data);
    void setDefaultValues();
}
