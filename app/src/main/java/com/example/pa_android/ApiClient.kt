package com.example.projetfinaljeu

import com.example.pa_android.AddFriendData
import com.example.pa_android.LoginData
import com.example.pa_android.SignupData
import com.example.pa_android.UpdateFriendData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val api = Retrofit.Builder()
        .baseUrl("http:/192.168.100.48:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiInterface::class.java)

   suspend fun login(data: LoginData): JsonObject {
        return api.postLogin(data).await()
   }

    suspend fun signup(data: SignupData): JsonObject {
        return api.postSignup(data).await()
    }

    suspend fun researchByName(textsearch: String): JsonObject {
        return api.getresearchByName(textsearch).await()
    }

    suspend fun addFriend(data: AddFriendData, iduser: String): JsonObject {
        return api.postaddFriend(data, iduser).await()
    }

    suspend fun answerFriend(data: UpdateFriendData, iduser: String): JsonObject {
        return api.putUpdateFriend(data, iduser).await()
    }

    suspend fun listFriendSend(textsearch: String): JsonArray {
        return api.getfriendsSend(textsearch).await()
    }

    suspend fun listFriendReceived(textsearch: String): JsonArray {
        return api.getfriendsReceived(textsearch).await()
    }
}