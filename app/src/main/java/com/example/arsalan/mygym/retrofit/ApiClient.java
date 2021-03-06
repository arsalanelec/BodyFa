package com.example.arsalan.mygym.retrofit;

/**
 * Created by Arsalan on 03-10-2017.
 */

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://bodyfa.ir";
    /*
        public static final String BASE_IMAGES_URL = "http://89.42.210.40/newshop1/public/user_files/";
        public static final String BASE_NEWS_IMAGES_URL = "http://89.42.210.40/newshop1/dristdata/news/";

        public static final int GROUP_SALAMAT_OMOMI = 15;
        public static final int GROUP_SALAMAT_TAKHASOSI = 16;
        public static final int GROUP_BAZAMOOZI = 11;
        public static final int GROUP_ZEMNEKHEDMAT_KARKONAN = 13;
        public static final int GROUP_ZEMNEKHEDMAT_KARKONAN_MOKHTALEF = 14;
        public static final int GROUP_DANESHKADE_PEZESHKI = 17;
        public static final int GROUP_DANESHKADE_PIRAPEZESHKI = 18;
        public static final int GROUP_DANESHKADE_FANAVARI = 19;
        public static final int GROUP_KHADAMAT_NOSKHE = 21;
        public static final int GROUP_KHADAMAT_MONSHI_DANDAN = 22;
        public static final int GROUP_KHADAMAT_DANDANSAZI = 23;
        public static final int GROUP_KHADAMAT_KOMAK_PHISIOLOGIST = 24;
        public static final int GROUP_KHADAMAT_BIMARBAR = 25;
        public static final int GROUP_KHADAMAT_PARASTARI = 26;

    */
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            // .setDateFormat(DateFormat.LONG)
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}