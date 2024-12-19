package org.example;

import org.example.HomeWork1.ArrayList_VladimirSeledtsov;

public class Main {
    public static void main(String[] args) {
        ArrayList_VladimirSeledtsov<Integer> list = new ArrayList_VladimirSeledtsov<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.remove(1);
        System.out.println("Size after removal: " + list.size());
        list.quickSort(Integer::compare);
        System.out.println("Sorted: " + list.isSorted());
    }
}