package com.ponthaitay.listmovie.kotlin.javaexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filter {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 0, 9, 0, 5);
        List<Integer> positiveNumbers = new ArrayList<>();
        for (Integer value : list) {
            if (value > 0) {
                positiveNumbers.add(value);
            }
        }
    }

}
