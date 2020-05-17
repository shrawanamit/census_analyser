package com.censusanalyser;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<CensusDAO> stateCSVList;
    List<CensusDAO> censusCSVList;
    List<CensusDAO> usCensusCSVList;
    Map<String, CensusDAO> censusStateMap ;

    public CensusAnalyser() {
        this.usCensusCSVList=new ArrayList<>();
        this.censusCSVList = new ArrayList<>();
        this.stateCSVList =new ArrayList<>();
        this.censusStateMap=new HashMap<>();

    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, USCensusCSV.class);
    }

    private <E> int loadCensusData(String csvFilePath,Class<E> censusCSVClass) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<E> csvIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCsv")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
        return censusStateMap.size();
    }
    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState ->censusStateMap.get(csvState.state)!=null)
                    .forEach(csvState -> censusStateMap.get(csvState.state).stateCode=csvState.stateCode);
         return censusStateMap.size();
        }catch (IOException  e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException  e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());}
    }
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {

        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson =new Gson().toJson(this.censusCSVList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<CensusDAO> censusComparator) {

        for(int i = 0; i< censusCSVList.size()-1; i++){
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
               CensusDAO census1= censusCSVList.get(j);
                CensusDAO census2= censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2 )>0){
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1,census1);
                }
            }
        }
    }
    public String getStateCodeWiseSortedIndianStateCodeData() throws CensusAnalyserException {

        if (stateCSVList == null || stateCSVList.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> stateComparator =Comparator.comparing(stateCensus -> stateCensus.stateCode);
        this.sort1(stateComparator);
        String sortedStateCodeJson =new Gson().toJson(this.stateCSVList);
        return sortedStateCodeJson;

    }

    private void sort1(Comparator<CensusDAO> stateComparator) {

        for(int i = 0; i< stateCSVList.size()-1; i++){
            for (int j = 0; j < stateCSVList.size() - i - 1; j++) {
                CensusDAO census1= stateCSVList.get(j);
                CensusDAO census2= stateCSVList.get(j+1);
                if(stateComparator.compare(census1,census2 )>0){
                    stateCSVList.set(j,census2);
                    stateCSVList.set(j+1,census1);
                }
            }
        }
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {

        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.population);
        this.sort(censusComparator);
        String sortedStateCensusJson =new Gson().toJson(this.censusCSVList);
        return sortedStateCensusJson;
    }


    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.populationDensity);
        this.sort(censusComparator);
        String sortedStateCensusJson =new Gson().toJson(this.censusCSVList);
        return sortedStateCensusJson;
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
        throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator =Comparator.comparing(census -> census.totalArea);
        this.sort(censusComparator);
        String sortedStateCensusJson =new Gson().toJson(this.censusCSVList);
        return sortedStateCensusJson;
    }
}
