package com.hbrb.jiyun.mvp.bean;

public class City {

    private String cityCode;
    private String cityName;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public City(String cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }
}
