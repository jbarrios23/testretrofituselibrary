package com.example.testretrofituselibrary;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {


     public static EndPoint endPoint;

     public static String CLASS_TAG=MainController.class.getSimpleName();

     public static void makeGetPetition(final ServiceCallback context){
         Log.e(CLASS_TAG, "makeGetPetition "+context );
         endPoint=ApiUtils.getSendMessageFCM();

         endPoint.getParameter().enqueue(new Callback<List<MydateConsult>>() {
             @Override
             public void onResponse(Call<List<MydateConsult>> call, Response<List<MydateConsult>> response) {
                 Log.e(CLASS_TAG, "response 33: "+new Gson().toJson(response.body()) );
                 context.handleData(new Gson().toJson(response.body()));
                 context.handleIndividualData(response.body().get(0).getBody());
             }

             @Override
             public void onFailure(Call<List<MydateConsult>> call, Throwable t) {
                 Log.e(CLASS_TAG, "onFailure "+t.getCause());
             }
         });



     }


}
