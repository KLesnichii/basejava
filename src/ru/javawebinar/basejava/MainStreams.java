package ru.javawebinar.basejava;

import java.util.*;

public class MainStreams {
    public static void main(String[] args) {
        int[] intArray = {1, 2, 3, 3, 2, 3};
        int[] intArray2 = {9, 8};
        System.out.println(minValue(intArray));
        System.out.println(minValue(intArray2));
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        System.out.println(oddOrEven(integerList));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (left, right) -> left * 10 + right);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        final List<Integer> od = new LinkedList<>();
        final List<Integer> even = new LinkedList<>();
        return integers.stream().
                reduce(0, (integer, integer2) -> {
                    (integer2 % 2 == 0 ? even : od).add(integer2);
                    return integer + integer2;
                }) % 2 == 0 ? od : even;
    }
}
