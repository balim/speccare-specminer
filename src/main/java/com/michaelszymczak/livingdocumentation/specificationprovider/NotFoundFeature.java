package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.util.ArrayList;
import java.util.List;

public class NotFoundFeature extends Feature {
    @Override
    public String getName() {
        return "Scenario not found";
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public List<String> getContent() {
        return new ArrayList<>();
    }
}
