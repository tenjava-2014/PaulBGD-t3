package com.tenjava.entries.PaulBGD.t3.utils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Timer {

    private Map<Long, String> times = new LinkedHashMap<>();

    public void time(String reason) {
        times.put(new Date().getTime(), reason);
    }

    public void print() {
        int i = 0;
        long first = 0;
        long last = 0;
        for (Map.Entry<Long, String> entry : times.entrySet()) {
            if (i++ == 0) {
                first = entry.getKey();
            } else {
                System.out.println("Took " + (entry.getKey() - last) + " to do " + entry.getValue());
            }
            last = entry.getKey();
        }
        System.out.println("Took " + (last - first) + "ms to execute!");
    }

}
