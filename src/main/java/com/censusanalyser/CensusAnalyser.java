package com.censusanalyser;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public CensusAnalyser(Country country) {
        this.country=country;
    }

    public enum Country {INDIA,US}
    private final Country country;
    Map<String, CensusDAO> censusStateMap ;


    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {

        censusStateMap= CensusAdapterFactory.getCensusData(country,csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.state);
        return sort(censusComparator);
    }

    public String getStateCodeWiseSortedIndianStateCodeData() throws CensusAnalyserException {
        Comparator<CensusDAO> stateComparator =Comparator.comparing(stateCensus -> stateCensus.stateCode);
        return sort(stateComparator);
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.population);
        return sort(censusComparator);
    }

    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.densityPerSqKm);
        return sort(censusComparator);
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.areaInSqKm);
        return sort(censusComparator);
    }

    public String sort(Comparator<CensusDAO> censusComparator) throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() ==0 ) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List sortedCensusData = censusStateMap.values()
                .stream()
                .sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedStateCensusDataInJson = new Gson().toJson(sortedCensusData);
        return sortedStateCensusDataInJson;
    }

}
