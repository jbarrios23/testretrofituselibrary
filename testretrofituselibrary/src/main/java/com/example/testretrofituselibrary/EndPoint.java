package com.example.testretrofituselibrary;



import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndPoint {

    @Headers({
            "Content-Type: application/json"
    })
    //@POST("fcm/send")
    /*Call<MessageId> SendNotificationService(@Header("Authorization") String Authorization,
                                            @Body JSONObject param);*/

    @GET("/posts")
    Call<List<MydateConsult>> getParameter();


}
