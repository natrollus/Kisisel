package com.natrollus.kisisel.gorunum;

/**
 * Created by byram on 11/7/15.
 */
public class UzakStatic {
    private static String degisken = "";

    public static String getDegisken() {
        return degisken;
    }

    public static void setDegisken(String degisken) {
        UzakStatic.degisken = degisken;
    }
}
