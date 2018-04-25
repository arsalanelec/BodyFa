package com.example.arsalan.mygym.retrofit;

/**
 * Created by Arsalan on 03-10-2017.
 */

import com.example.arsalan.mygym.Objects.RetCommentList;
import com.example.arsalan.mygym.Objects.RetGymList;
import com.example.arsalan.mygym.Objects.RetNewsDetail;
import com.example.arsalan.mygym.Objects.RetNewsList;
import com.example.arsalan.mygym.Objects.RetTrainerList;
import com.example.arsalan.mygym.Objects.RetUserProfile;
import com.example.arsalan.mygym.Objects.RetroResult;
import com.example.arsalan.mygym.Objects.Token;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface ApiInterface {


    @FormUrlEncoded
    @POST("/api/token")
    Call<Token> getToken(@Field("Grant_type") String grantType, @Field("password") String password, @Field("username") String usernam);

    @FormUrlEncoded
    @POST("/api/users/getprofile")
    Call<RetUserProfile> getProfile(@Header("Authorization") String token, @Field("username") String username);


    @Multipart
    @POST("/api/public/register")
    Call<RetroResult> registerOnServer(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("/api/public/getnewslist")
    Call<RetNewsList> getNewsList(@Part("offset") int offset, @Part("limit") int limit, @Part("TypeId") int TypeId );

    @Multipart
    @POST("/api/public/getnews")
    Call<RetNewsDetail> getNewsDetail(@Part("NewsId") long newsId );

    @Multipart
    @POST("/api/public/gettrainerlist")
    Call<RetTrainerList> getTrainerList(@Part("offset") int offset, @Part("limit") int limit, @Part("GymId") int gymId, @Part("CityId") int cityId, @Part("Sort") int sort );

    @Multipart
    @POST("/api/public/getgymlist")
    Call<RetGymList> getGymList(@Part("offset") int offset, @Part("limit") int limit, @Part("CityId") int cityId, @Part("Sort") int sort );

    @Multipart
    @POST("/api/users/GetNewsComment")
    Call<RetCommentList> getCommentList(@Header("Authorization") String token,@Part("offset") int offset, @Part("limit") int limit, @Part("NewsId") long newsId );

    @Multipart
    @POST("/api/users/SendNewsComment")
    Call<RetroResult> sendNewsComment(@Header("Authorization") String token,@Part("UserId") long userId ,@Part("NewsId") long newsId , @Part("Comment") String Comment );
/*NewsTypeId
UserId
GymId
Content
PictureUrl
ThumbUrl*/
    @Multipart
    @POST("/api/users/SendContent")
    Call<RetroResult> sendContent(@Header("Authorization") String token,@Part("UserId") long userId ,@Part("NewsTypeId") int newsTypeId ,@Part("Title") RequestBody title, @Part("Content") RequestBody content ,@Part MultipartBody.Part image,@Part MultipartBody.Part thumb);

    @Multipart
    @POST("/api/users/GetActivationCode")
    Call<RetroResult> getActivationCode(@Header("Authorization") String token,@Part("UserId") long userId ,@Part("Mobile") long mobileNo  );

}