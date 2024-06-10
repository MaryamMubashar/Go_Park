package com.example.gopark.apis;

import com.example.gopark.data.Slot;
import com.example.gopark.data.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<User> signup(@FieldMap Map<String, String> items, @Header("Password") String password);

    @POST("login.php")
    Call<User> login(@Body Map<String, String> credentials);

    @GET("parkingapi.php")
    Call<List<Slot>> getAvailableSlots();

    @FormUrlEncoded
    @POST("parkingapi.php")
    Call<Apiresponse> bookSlot(@Field("slot_id") String slotId);
}
