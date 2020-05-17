package com.censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV  {
    @CsvBindByName(column = "StateName", required = true)
    public String state;
    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;
    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "stateName='" + state + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", tin='" + tin + '\'' +
                '}';
    }
}

