package com.michaelszymczak.speccare.specminer.specificationprovider;


import com.michaelszymczak.speccare.specminer.domain.ResultLocator;
import com.michaelszymczak.speccare.specminer.domain.ResultSource;
import com.michaelszymczak.speccare.specminer.domain.SourceNotFound;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class LocatorBasedResultSource extends ResultSource {
    private final ResultLocator locator;

    public LocatorBasedResultSource(ResultLocator locator) {
        this.locator = locator;
    }

    @Override
    public List<Reader> getSources() throws SourceNotFound {
        try {
            Reader reader = new BufferedReader(new FileReader(locator.getResultFilePath().toFile()));
            return Arrays.asList(reader);
        } catch (FileNotFoundException e) {
            throw new SourceNotFound("Result file" + locator.getResultFilePath().toString() + "not found", e);
        }
    }
}
