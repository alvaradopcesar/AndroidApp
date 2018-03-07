package iosco.app.service;

import java.util.ArrayList;
import java.util.List;

import iosco.app.model.entity.AccommodationEntity;
import iosco.app.model.entity.Assistant;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;
import iosco.app.model.entity.SurveyItemResult;
import iosco.app.model.entity.TokenResponseEntity;
import iosco.app.model.entity.UserInfoEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by usuario on 16/02/2016.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("token")
    Call<TokenResponseEntity> getToken(@Field("grant_type") String grant_type,
                                       @Field("username") String username,
                                       @Field("password") String password);

    @GET("api/Event/MyCalendar")
    Call<ArrayList<CalendarEntity>> getCalendar(@Header("Authorization") String authorization);

    @GET("api/Event/Speakers")
    Call<ArrayList<Exhibitor>> getExhibitors(@Header("Authorization") String authorization);

    @GET("api/Event/Attendees")
    Call<ArrayList<Assistant>> getAssistants(@Header("Authorization") String authorization);

    @GET("api/speaker/{speakerid}/events")
    Call<ArrayList<CalendarEntity>> getEventsBySpeaker(@Header("Authorization") String authorization, @Path("speakerid") int spekaerid);

    @GET("api/Event/EveningEvents")
    Call<ArrayList<CalendarEntity>> getEveningEvents(@Header("Authorization") String authorization);

    @GET("api/Event/RegisteredHotels")
    Call<ArrayList<AccommodationEntity>> getHotels(@Header("Authorization") String authorization);

    @GET("api/Account/UserInfo")
    Call<UserInfoEntity> getUserInfo(@Header("Authorization") String authorization);

    @GET("{url}")
    Call<ResponseBody> getSVG(@Header("Authorization") String authorization, @Path("url") String url);

    @GET("api/event/sketchers")
    Call<ArrayList<String>> getSketchers();

    @GET("api/Account/ForgotPassword")
    Call<ResponseBody> forgotPassword(@Query("emailAddress") String emailAddress,@Query("lang") String lang);

    @GET("api/Event/AskQuestion")
    Call<ResponseBody> sendAskEvent(@Header("Authorization") String authorization, @Query("eventId") int eventId, @Query("question") String question);


    @POST("api/Event/Survey")
    Call<ResponseBody> sendSurvey(@Header("Authorization") String authorization, @Body List<SurveyItemResult> bodyListSurvey);

}
