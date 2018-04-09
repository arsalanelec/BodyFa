package com.example.arsalan.mygym.retrofit;

/**
 * Created by Arsalan on 03-10-2017.
 */

import com.example.arsalan.mygym.Objects.RetNewsList;
import com.example.arsalan.mygym.Objects.RetroResult;
import com.example.arsalan.mygym.Objects.Token;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface ApiInterface {


    @FormUrlEncoded
    @POST("/token")
    Call<Token> getToken(@Field("Grant_type") String grantType, @Field("password") String password, @Field("username") String usernam);

    @Multipart
    @POST("/api/public/register")
    Call<RetroResult> registerOnServer(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("/api/public/getnewslist")
    Call<RetNewsList> getNewsList(@Part("offset") int offset, @Part("limit") int limit, @Part("TypeId") int TypeId );

    /*@Multipart
    @POST("CustomersWebservice/login")
    Call<UserResult> login(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("CustomersWebservice/registration")
    Call<ResultResponce> register(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("CustomersWebservice/editCustomer")
    Call<ResultResponce> editeProfile(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("CustomersWebservice/forgetPassword")
    Call<ResultResponce> forgotPassword(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("ProductsWebservice/getPackageList")
    Call<List<PackageListItem>> getPackageList(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("ProductsWebservice/getPackage")
    Call<PackageDetail> getPackageDetail(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("OrdersWebservice/payOrder")
    Call<PayResponse> payOrder(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("OrdersWebservice/getOrdersHistory")
    Call<HistoryResponse> getOrdersHistory(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("TestsWebservice/getTest")
    Call<Test> getTest(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("TestsWebservice/sendTestAnswer")
    Call<TestResult> sendTestAnswer(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("CustomersWebservice/getCustomersInfo")
    Call<CustomerDetail> getCustomerDetail(@Part("token") RequestBody token);//@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("NewsletterWebservice/getNewsLetterList")
    Call<List<NewsItem>> getNewsLetterList(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("NewsletterWebservice/getANewsLetter")
    Call<NewsItem> getANewsLetter(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("ProductsWebservice/getCertifications")
    Call<Certification> getCertification(@PartMap Map<String, RequestBody> params);

    @GET("movie/{id}")
    Call<City> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}