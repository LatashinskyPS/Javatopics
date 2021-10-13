package by.latashinsky.utils;

import java.util.Scanner;

public class Confirms {
    public static boolean confirm() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Are you want to delete(y/n)?");
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
