package com.hbrb.jiyun.mvp.bean;

public class BmjfDetail {

    private String serviceType;
    private String secServiceName;
    private String secServiceCode;
    private String serviceUrl;
    private String iconUrl;

    public BmjfDetail() {
        super();
    }

    public BmjfDetail(String serviceType, String secServiceName, String secServiceCode, String serviceUrl, String iconUrl) {
        this.serviceType = serviceType;
        this.secServiceName = secServiceName;
        this.secServiceCode = secServiceCode;
        this.serviceUrl = serviceUrl;
        this.iconUrl = iconUrl;
    }

    public String getSecServiceName() {
        return secServiceName;
    }

    public void setSecServiceName(String secServiceName) {
        this.secServiceName = secServiceName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSecServiceCode() {
        return secServiceCode;
    }

    public void setSecServiceCode(String secServiceCode) {
        this.secServiceCode = secServiceCode;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
