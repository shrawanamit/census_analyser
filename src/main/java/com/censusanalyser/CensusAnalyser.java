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
    Map<String, IndiaCensusCSV> censusCSVMap = null;
    Map<String, IndiaStateCodeCSV> stateCSVMap = null;

    public CensusAnalyser() {
        this.usCensusCSVList=new ArrayList<>();
        this.censusCSVList = new ArrayList<>();
        this.stateCSVList =new ArrayList<>();
        this.censusCSVMap=new HashMap<>();
        this.stateCSVMap=new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaCensusCSV> csvFileIterator=csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()){
                this.censusCSVList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.censusCSVList.size();

        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException  e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> csvFileIterator=csvBuilder.getCSVFileIterator(reader,USCensusCSV.class);
            while (csvFileIterator.hasNext()){
                this.usCensusCSVList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.usCensusCSVList.size();
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException  e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            while (stateCSVIterator.hasNext()){
                this.stateCSVList.add(new CensusDAO(stateCSVIterator.next()));
            }
            return this.stateCSVList.size();
        }catch (IOException  e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException  e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());}
    }

    private  <E> int getCount(Iterator<E> iterator) {

        Iterable<E> csvIterable = () -> iterator;
        int namOfEateries;
        namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return namOfEateries;
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
