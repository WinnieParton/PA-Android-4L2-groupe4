package com.example.projetfinaljeu

import com.example.pa_android.AddFriendData
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

    @POST("friend/{iduser}")
    fun postaddFriend(@Body signupData: AddFriendData, @Path("iduser") iduser: String): Deferred<JsonObject>

}