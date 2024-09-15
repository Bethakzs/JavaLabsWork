package org.example.lab_2.task1;

public record TopOfGraph(String name, int value) {

    public TopOfGraph {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be greater than 0");
        }
    }

    public String getName() {
        return name;
    }
}
