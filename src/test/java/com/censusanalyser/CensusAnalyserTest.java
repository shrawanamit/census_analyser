package com.censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATES_CSV_FILE_PATH ="./src/test/resources/IndiaStateCode.csv" ;
    private static final String WRONG_EXTENSION_FILE_PATH = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String CENSUS_CSV_WRONG_DELIMITER = "./src/test/resources/indiaStatesensusWrongDelimeter.csv";
    private static final String CENSUS_CSV_WRONG_HEADER_CSV_FILE="./src/test/resources/indiaStateCensusWrongHeader.csv";
    private static final String STATES_CSV_WRONG_DELIMITER = "./src/test/resources/indiaStatesWrongDelimeter.csv";
    private static final String STATES_CSV_WRONG_HEADER_CSV_FILE="./src/test/resources/indiaStatesDataWrongHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATES_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenCorrect_butTypeIncorrectShouldThrowCustomException() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_EXTENSION_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenWrongDelimiter_shouldThrowCustomException() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,CENSUS_CSV_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() throws CensusAnalyserException {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,CENSUS_CSV_WRONG_HEADER_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCsv_whenSortedOnState_shouldReturnShortedResult() {

        try {
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndianStateCensusCsv_whenSortedOnPopulation_shouldReturnShortedResult() {

        try {
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(607688,censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndianStateCensusCsv_whenSortedOnDensity_shouldReturnShortedResult() {

        try {
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(50,censusCSV[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndianStateCensusCsv_whenSortedOnStateArea_shouldReturnShortedResult() {

        try {
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(3702,censusCSV[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndianStateCodeCsv_whenSortedOnStateCode_shouldReturnShortedResult() {

        try {
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATES_CSV_FILE_PATH);
            String sortedStateCsvData = censusAnalyser.getStateCodeWiseSortedIndianStateCodeData();
            IndiaStateCodeCSV[] stateCSV = new Gson().fromJson(sortedStateCsvData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AB",stateCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCsv_WhenCorrect_butTypeIncorrectShouldThrowCustomException() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_EXTENSION_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCsv_WhenWrongDelimiter_shouldThrowCustomException() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,STATES_CSV_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndianStateCsv_WithWrongHeader_ShouldThrowException() throws CensusAnalyserException {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,STATES_CSV_WRONG_HEADER_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenUSCensusCsv_whenSortedOnPopulation_shouldReturnShortedResult() throws CensusAnalyserException {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,US_CENSUS_CSV_FILE_PATH);
            String sortedUSCsvData = censusAnalyser.getPopulationyWiseSortedUSCensusData();
            USCensusCSV[] stateCSV = new Gson().fromJson(sortedUSCsvData, USCensusCSV[].class);
            Assert.assertEquals(4779736,stateCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUSCensuseCsv_whenSortedOnArea_shouldReturnShortedResult() throws CensusAnalyserException {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,US_CENSUS_CSV_FILE_PATH);
            String sortedUSCsvData = censusAnalyser.getAreaWiseSortedUSCensusData();
            USCensusCSV[] stateCSV = new Gson().fromJson(sortedUSCsvData,  USCensusCSV[].class);
            Assert.assertEquals(294207.53,stateCSV[0].totalArea);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUSCensusCsv_whenSortedOnPopulationDensity_shouldReturnShortedResult() throws CensusAnalyserException {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,US_CENSUS_CSV_FILE_PATH);
            String sortedUSCsvData = censusAnalyser.getPopulationDensityWiseSortedUSCensusData();
            USCensusCSV[] stateCSV = new Gson().fromJson(sortedUSCsvData, USCensusCSV[].class);
            Assert.assertEquals(21.74,stateCSV[0].populationDensity);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


}