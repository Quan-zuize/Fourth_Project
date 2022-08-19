package com.example.project_4_admin.Remote;

import com.example.project_4_admin.model.DataMessage;
import com.example.project_4_admin.model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAzPAL9UE:APA91bFbD51-9zK4PdiBfLUh1bEFOwPpKype5Tny3bCTAbylLgqemb4Vx0lf1USSpcjAbNhUj4veEdZeZL2TxfBJKmbLoENQT6uPWmv2d_P0Rmj9x0xPzxxBxrsJ-METSXIGfSrbq9ms"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
