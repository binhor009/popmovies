package com.fabiofilho.popmovies.Objects;

/**
 * Created by dialam on 25/01/17.
 */

public class Utils {

    /**
     * Gets and returns the complete "tree" - tree may mean the package and the method inside a class
     * that calls this method. Example of an output: com.your_complete_package.YourClass.yourMethod()
     * @return methodName
     */
    public static String getMethodName(){

        String methodName;

        // Gets the stack element which calls this method.
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];

        // Concatenates the class and its method that has been gotten.
        methodName = stackTraceElement.getClassName()+"."+ stackTraceElement.getMethodName()+"()";

        return methodName;
    }
}
