package com.censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public String stateId;
    public int population;
    public double populationDensity;
    public double totalArea;
    public int tin;


    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea=indiaCensusCSV.areaInSqKm;
        populationDensity=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state=indiaStateCodeCSV.state;
        stateCode=indiaStateCodeCSV.stateCode;
        tin=indiaStateCodeCSV.tin;
    }
    public CensusDAO(USCensusCSV  USCensusCSV ) {
        state=USCensusCSV.state;
        stateId=USCensusCSV.stateId;
        population=USCensusCSV.population;
        populationDensity=USCensusCSV.populationDensity;
        totalArea=USCensusCSV.totalArea;
    }

//    public IndiaCensusCSV getIndiaCensusCSV() {
//        return new IndiaCensusCSV(state,population,(int) populationDensity,(int)totalArea);
//    }
}

