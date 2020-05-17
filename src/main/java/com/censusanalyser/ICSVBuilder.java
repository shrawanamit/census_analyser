package com.censusanalyser;

import java.io.Reader;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ICSVBuilder<E> {


   Iterator<E> getCSVFileIterator(Reader reader, Class csvClass)throws CSVBuilderException;
    List<E> getCSVFileList(Reader reader, Class csvClass)throws CSVBuilderException;
    //Map<E,E> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException;
}
