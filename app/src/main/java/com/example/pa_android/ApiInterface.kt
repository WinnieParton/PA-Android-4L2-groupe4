package com.example.projetfinaljeu

import com.example.pa_android.LoginData
import com.example.pa_android.SignupData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiInterface {

    //liste  des jeux les plus joués
    @POST("user/login")
    fun postLogin(@Body loginData: LoginData): Deferred<JsonObject>

    @POST("user")
    fun postSignup(@Body signupData: SignupData): Deferred<JsonObject>

    @GET("user/name/{textsearch}")
    fun getresearchByName(@Path("textsearch") textsearch: String?): Deferred<JsonObject>


    //recuperer les details d'un jeu
    @GET("api/appdetails")
    fun getDetailGamesAsync(@Query("appids") appids: Int?, @Query("l") lang: String?): Deferred<JsonObject>

    //recuperer les avis d'un jeu
    @GET("appreviews/{apiId}?json=1")
    fun getWishGamesAsync(@Path("apiId") apiId: Int?): Deferred<JsonObject>

    //liste  des jeux sur lesquels la recherche s'applique

}