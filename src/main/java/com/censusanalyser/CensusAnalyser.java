package com.censusanalyser;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaStateDAO> stateList = null;
    List<IndiaCensusDAO> censusList = null;

    public CensusAnalyser() {

        this.censusList = new ArrayList<>();
        this.stateList=new ArrayList<>();
    }



    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()){
                this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return this.censusList.size();
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
            while(stateCSVIterator.hasNext()){
                this.stateList.add(new IndiaStateDAO(stateCSVIterator.next()));
            }
            return this.stateList.size();
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

        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator =Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson =new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {

        for(int i=0;i<censusList.size()-1;i++){
            for (int j = 0; j < censusList.size() - i - 1; j++) {
               IndiaCensusDAO census1=censusList.get(j);
                IndiaCensusDAO census2=censusList.get(j+1);
                if(censusComparator.compare(census1,census2 )>0){
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }
    }
    public String getStateCodeWiseSortedIndianStateCodeData() throws CensusAnalyserException {

        if (stateList == null || stateList.size() == 0) {
            throw new CensusAnalyserException("no census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateDAO> stateComparator =Comparator.comparing((IndiaStateDAO stateCensus) -> stateCensus.stateCode);
        this.sort1(stateComparator);
        String sortedStateCodeJson =new Gson().toJson(this.stateList);
        return sortedStateCodeJson;

    }

    private void sort1(Comparator<IndiaStateDAO> stateComparator) {

        for(int i=0;i<stateList.size()-1;i++){
            for (int j = 0; j < stateList.size() - i - 1; j++) {
                IndiaStateDAO census1=stateList.get(j);
                IndiaStateDAO census2=stateList.get(j+1);
                if(stateComparator.compare(census1,census2 )>0){
                    stateList.set(j,census2);
                    stateList.set(j+1,census1);
                }
            }
        }
    }
}
