package com.censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public String stateId;
    public int population;
    public double areaInSqKm;
    public double densityPerSqKm;
    


    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public CensusDAO(USCensusCSV  USCensusCSV ) {
        state=USCensusCSV.state;
        stateId=USCensusCSV.stateId;
        population=USCensusCSV.population;
        densityPerSqKm=USCensusCSV.populationDensity;
        areaInSqKm=USCensusCSV.totalArea;
    }
    public Object getCensusDTO(CensusAnalyser.Country country) {

        if(country.equals(CensusAnalyser.Country.INDIA)){
            return new IndiaCensusCSV(state,population,(int)densityPerSqKm,(int)areaInSqKm);
        }return new USCensusCSV(state,stateCode,population,(int)densityPerSqKm,areaInSqKm);
    }


}

