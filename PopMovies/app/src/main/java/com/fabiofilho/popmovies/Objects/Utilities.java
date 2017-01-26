package com.fabiofilho.popmovies.Objects;

/**
 * Created by dialam on 25/01/17.
 */

public class Utilities {

    public static String getMethodNameForLog(){

        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];

        return stackTraceElement.getClassName()+"."+ stackTraceElement.getMethodName()+"()";
    }
}
