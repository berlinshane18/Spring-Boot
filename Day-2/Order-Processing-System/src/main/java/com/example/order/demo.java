package com.example.order;

public class demo {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Hello");

        sb.append(" World");

        sb.insert(5, ",");

        sb.replace(6, 11, "Java");

        sb.delete(5, 6);

        System.out.println(sb.toString());
    }
}
