package com.censusanalyser;
import java.util.*;

public class CensusAdapterFactory {
    public static Map<String, CensusDAO> getCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        else
            throw new CensusAnalyserException("Unknown Country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
