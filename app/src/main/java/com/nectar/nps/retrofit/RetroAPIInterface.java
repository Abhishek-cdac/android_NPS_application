package com.nectar.nps.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nectar.nps.LoginActivity;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface RetroAPIInterface {


    /*@GET("https://nt3.nectarinfotel.com/webservices/login.php")
    Call<JsonObject> callLoginAPI(@Query("auth_user") String username,
                                  @Query("auth_pwd") String password,
                                  @Query("device_token") String device_token);*/

    /*@POST("http://wfms.timesheet.nectarinfotel.com/login")
    Call<JsonObject> callLoginAPI(@Query("username") String username,
                                  @Query("password") String password,
                                  @Query("tokenid") String device_token,
                                    @Query("clientname") String oo);*/
    @FormUrlEncoded
    @POST("http://nps.nectarinfotel.com:8070/Token")
    Call<JsonObject> login(@FieldMap Map<String, String> options);


    @GET("http://nps.nectarinfotel.com:8070/Token")
    Call<JsonObject> callLoginAPI(@Query("grant_type") String username,
                                  @Query("username") String password,
                                  @Query("password") String device_token);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmesGetForDashboard?ts=1582056669022")
    Call<JsonArray> callDashboardAPI(@Header("Authorization") String auth);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/GetActiveAlarms")
    Call<JsonArray> callActiveAlarmAPI(@Header("Authorization") String auth);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmsLiveDashboardData")
    Call<JsonArray> callAlarmCOuntAPI(@Header("Authorization") String auth);


  //  @Headers({ "Content-Type: application/json;charset=UTF-8"})
 /*   @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmesGetForDashboard?ts=1582056669022")
    Call<JsonObject> callDashboardAPI();*/


    @GET("https://nt3.nectarinfotel.com/webservices/ticket_list.php")
    Call<JsonObject> callStatusDetailAPI(@Query("category") int category,
                                         @Query("subcategory") String subcategory,
                                         @Query("status") String status,
                                         @Query("userid") String userid,
                                         @Query("urgency") int urgency);

    @GET("https://nt3.nectarinfotel.com/webservices/ticket_list.php")
    Call<JsonObject> callAgentDetailAPI(@Query("category") int category,
                                        @Query("subcategory") String subcategory,
                                        @Query("agent") String agent,
                                        @Query("userid") String userid,
                                        @Query("urgency") int urgency);

    @GET("https://nt3.nectarinfotel.com/webservices/ticket_list.php")
    Call<JsonObject> callDepartmentDetailAPI(@Query("category") int category,
                                             @Query("subcategory") String subcategory,
                                             @Query("department") String department,
                                             @Query("userid") String userid,
                                             @Query("urgency") int urgency);

    @GET("https://nt3.nectarinfotel.com/webservices/ticket_info.php")
    Call<JsonObject> callTicketDetailAPI(@Query("category") int category,
                                         @Query("ticketid") String subcategory,
                                         @Query("userid") String department);

}
//https://nt3.nectarinfotel.com/webservices