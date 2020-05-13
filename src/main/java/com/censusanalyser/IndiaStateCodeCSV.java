package com.censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "StateName", required = true)
    public String stateName;
    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "state='" + stateName + '\'' +
                ", StateCode='" + stateCode + '\'' +
                '}';
    }
}
