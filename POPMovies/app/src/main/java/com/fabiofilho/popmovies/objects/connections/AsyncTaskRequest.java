package com.fabiofilho.popmovies.objects.connections;

import android.os.AsyncTask;
import android.util.Log;

import com.fabiofilho.popmovies.objects.Utils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by dialam on 25/01/17.
 */

public class AsyncTaskRequest extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... params) {

        try {
            return NetworkUtils.getResponse(params[0]);

        } catch (IOException e) {
            onNetworkUtilsGetsIOException(e);
            return null;
        }
    }

    public void onNetworkUtilsGetsIOException(IOException e){

        Log.e(Utils.getMethodName(), e.getMessage());
    }
}
