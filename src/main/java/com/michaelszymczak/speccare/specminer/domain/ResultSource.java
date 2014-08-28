package com.michaelszymczak.speccare.specminer.domain;

import java.io.Reader;
import java.util.Collection;

public interface ResultSource {
    public Collection<Reader> getSources();
}
