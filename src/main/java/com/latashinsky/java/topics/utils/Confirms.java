package com.latashinsky.java.topics.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Confirms {
    private static final Logger logger = LoggerFactory.getLogger(Confirms.class);

    public static boolean confirm() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        logger.info("Are you want to delete(y/n)?\n");
        String str;
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                return true;
            }
        }
        return false;
    }
}
