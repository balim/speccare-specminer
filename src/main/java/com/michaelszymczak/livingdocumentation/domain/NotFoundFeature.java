package com.michaelszymczak.livingdocumentation.domain;

import java.util.ArrayList;
import java.util.List;

public class NotFoundFeature extends Feature {
    @Override
    public String getName() {
        return "Feature not found";
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
