package com.censusanalyser;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public enum Country {INDIA,US}
    Map<String, CensusDAO> censusStateMap ;
    public CensusAnalyser() {}

    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {

        censusStateMap= CensusAdapterFactory.getCensusData(country,csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {

        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.state);
        List<CensusDAO> censusDAOS=censusStateMap.values().stream().
                                                  collect(Collectors.toList());
        this.sort(censusDAOS,censusComparator);
        String sortedStateCensusJson =new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDAO> censusDAOS,Comparator<CensusDAO> censusComparator) {

        for(int i = 0; i< censusDAOS.size()-1; i++){
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
               CensusDAO census1= censusDAOS.get(j);
                CensusDAO census2= censusDAOS.get(j+1);
                if(censusComparator.compare(census1,census2 )>0){
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j+1,census1);
                }
            }
        }
    }

    public String getStateCodeWiseSortedIndianStateCodeData() throws CensusAnalyserException {

        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> stateComparator =Comparator.comparing(stateCensus -> stateCensus.stateCode);
        List<CensusDAO> censusDAOS=censusStateMap.values().stream().
                                                  collect(Collectors.toList());
        this.sort(censusDAOS,stateComparator);
        String sortedStateCensusJson =new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {

        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.population);
        List<CensusDAO> censusDAOS=censusStateMap.values().stream().
                                                  collect(Collectors.toList());
        this.sort(censusDAOS,censusComparator);
        String sortedStateCensusJson =new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {

        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.populationDensity);
        List<CensusDAO> censusDAOS=censusStateMap.values().stream().
                                                  collect(Collectors.toList());
        this.sort(censusDAOS,censusComparator);
        String sortedStateCensusJson =new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {

        if (censusStateMap == null || censusStateMap.size() == 0) {
        throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.totalArea);
        List<CensusDAO> censusDAOS=censusStateMap.values().stream().
                                                  collect(Collectors.toList());
        this.sort(censusDAOS,censusComparator);
        String sortedStateCensusJson =new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }
}
