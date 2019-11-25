package com.hbrb.jiyun.mvp.bean;

import java.util.ArrayList;

public class Bmjf {

    private String priServiceName;

    private ArrayList<BmjfDetail> bmjfDetailList;

    public Bmjf(String priServiceName,ArrayList<BmjfDetail> bmjfDetailList){
        this.priServiceName = priServiceName;
        this.bmjfDetailList = bmjfDetailList;
    }

    public String getPriServiceName() {
        return priServiceName;
    }

    public void setPriServiceName(String priServiceName) {
        this.priServiceName = priServiceName;
    }

    public ArrayList<BmjfDetail> getBmjfDetailList() {
        return bmjfDetailList;
    }

    public void setBmjfDetailList(ArrayList<BmjfDetail> bmjfDetailList) {
        this.bmjfDetailList = bmjfDetailList;
    }
}
