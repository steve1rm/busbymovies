package me.androidbox.busbymovies.testrunner;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import di.AndroidTestBusbyMoviesMainApplication;

/**
 * Created by steve on 2/25/17.
 */

public class CustomTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        return super.newApplication(cl, AndroidTestBusbyMoviesMainApplication.class.getName(), context);
    }
}
