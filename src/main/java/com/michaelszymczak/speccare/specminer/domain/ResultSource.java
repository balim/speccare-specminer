package com.michaelszymczak.speccare.specminer.domain;

import java.io.Reader;
import java.util.List;

public interface ResultSource {
    public List<Reader> getSources();
}
