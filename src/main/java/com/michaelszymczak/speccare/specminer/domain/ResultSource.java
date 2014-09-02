package com.michaelszymczak.speccare.specminer.domain;

import java.io.Reader;
import java.util.List;

public abstract class ResultSource {
    public abstract List<Reader> getSources() throws SourceNotFound;

    public Reader getFirstSource() throws SourceNotFound {
        return getSources().get(0);
    }
}
