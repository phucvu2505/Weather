package com.example.phucv.weather.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phucv on 11/6/2016.
 */

public class CurrentWeatherAsyncTask extends AsyncTask<Void, Void, String>{
    public interface CallBack{
        public void onFinish(String body);
    }


    private CallBack callBack;

    private ProgressDialog mProgressDialog;

    private Location location;

    public CurrentWeatherAsyncTask(Context context) {
        this.location = location;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading.....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?lat=20.4507524&lon=-159.7498616&APPID=fee985a5cfe4764ab20deed98ff488aa")
                .addHeader("Accept", "application/json")
                .build();
        try{
            Thread.sleep(3000);
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                String body = response.body().string();
                return body;
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String body) {
        super.onPostExecute(body);
        mProgressDialog.dismiss();
        if(callBack != null){
            callBack.onFinish(body);
        }

    }
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
