package com.censusanalyser;

public class IndiaStateDAO {
    public String stateName;
    public String stateCode;
    public int tin;

    public IndiaStateDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        stateName=indiaStateCodeCSV.stateName;
        stateCode=indiaStateCodeCSV.stateCode;
        tin=indiaStateCodeCSV.tin;

    }

}
