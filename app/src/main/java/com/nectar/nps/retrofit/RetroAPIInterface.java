package com.nectar.nps.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nectar.nps.LoginActivity;
import com.nectarinfotel.utils.AppConstants;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface RetroAPIInterface {



    @FormUrlEncoded
    @POST("http://nps.nectarinfotel.com:8070/Token")
    Call<JsonObject> login(@FieldMap Map<String, String> options);


   @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/GetAlarmTotalUpDownChart?ts=1582701274615")
    Call<JsonObject> callDashboardAPI(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/siteUpDownGraph?ts=1582708129227")
    Call<JsonArray> callDashboardSiteupDownAPI(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/GetActiveAlarms")
    Call<JsonArray> callActiveAlarmAPI(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmsLiveDashboardData")
    Call<JsonArray> callAlarmCOuntAPI(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/GetAlarmsWithDownHr")
    Call<JsonArray> callAlarmclearedCOuntAPI(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmsLiveDashboardByPerceivedSererityID?PerceivedSeverityID=1")
    Call<JsonArray> callCriticalAlarmcountAPI(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmsLiveDashboardByPerceivedSererityID?PerceivedSeverityID=2")
   Call<JsonArray> callMajorAlarmcountAPI(@Header("Authorization") String auth);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmsLiveDashboardByPerceivedSererityID?PerceivedSeverityID=3")
   Call<JsonArray> callMinarAlarmcountAPI(@Header("Authorization") String auth);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("http://nps.nectarinfotel.com:8070/api/Alarm/AlarmsLiveDashboardByPerceivedSererityID?PerceivedSeverityID=6")
   Call<JsonArray> callClearedAlarmcountAPI(@Header("Authorization") String auth);

    @FormUrlEncoded
    @Headers({ "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("http://nps.nectarinfotel.com:8070/api/KPI/GetAllKPIData?ts=1583139158173")
     // Call<JsonObject> calllivekpiAPI(@Body String body,@Header("Authorization") String auth);
   // Call<JsonObject> calllivekpiAPI(@Header("Authorization") String auth,@Query("FromTime") String FromTime,@Query("KPIDate") String KPIDate,@Query("ToTime") String ToTime);
    Call<JsonObject> calllivekpiAPI(@Header("Authorization") String auth,@FieldMap Map<String, String> options);

}
//https://nt3.nectarinfotel.com/webservices