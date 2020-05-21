package com.guillermonarvaez;

public enum MonomerDimension {

    TWO_DIMENSIONAL_RECTANGULAR("Two Dimensional Rectangular"),
    TWO_DIMENSIONAL("Two Dimensional"),
    THREE_DIMENSIONAL("Three Dimensional");

    private String name;

    private MonomerDimension(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}