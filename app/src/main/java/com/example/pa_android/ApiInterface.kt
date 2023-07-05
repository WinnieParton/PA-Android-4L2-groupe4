package com.example.projetfinaljeuw

import com.example.pa_android.AddFriendData
import com.example.pa_android.LoginData
import com.example.pa_android.SignupData
import com.example.pa_android.UpdateFriendData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiInterface {

    @POST("auth/login")
    fun postLogin(@Body loginData: LoginData): Deferred<JsonObject>

    @POST("auth/signup")
    fun postSignup(@Body signupData: SignupData): Deferred<JsonObject>

    @GET("user/{userId}/name/{textsearch}")
    fun getresearchByName(@Path("userId") userId: String, @Path("textsearch") textsearch: String?): Deferred<JsonArray>

    @POST("friend/{iduser}")
    fun postaddFriend(@Body signupData: AddFriendData, @Path("iduser") iduser: String): Deferred<JsonObject>

    @PUT("friend/{iduser}/answer")
    fun AnswerInvitation(@Body signupData: UpdateFriendData, @Path("iduser") iduser: String): Deferred<JsonObject>

    @GET("friend/sent/{userId}")
    fun getfriendsSend(@Path("userId") userId: String?): Deferred<JsonObject>

    @GET("friend/received/{userId}")
    fun getfriendsReceived(@Path("userId") userId: String?): Deferred<JsonObject>

    @GET("friend/{userId}")
    fun getMyfriends(@Path("userId") userId: String?): Deferred<JsonObject>

    @GET("game")
    fun getGames(): Deferred<JsonObject>

    @GET("user/chat/list/{userId}")
    fun getListConversation(@Path("userId") userId: String): Deferred<JsonArray>

}