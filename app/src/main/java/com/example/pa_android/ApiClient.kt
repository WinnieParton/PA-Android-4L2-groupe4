package com.example.projetfinaljeu

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.pa_android.*
import com.example.projetfinaljeuw.ApiInterface
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val api = Retrofit.Builder()
        .baseUrl("http:/192.168.1.99:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(createOkHttpClient())
        .build()
        .create(ApiInterface::class.java)
    private lateinit var sharedPreferences: SharedPreferences

    fun setSharedPreferences(pref: SharedPreferences) {
        sharedPreferences = pref
    }
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = getTokenFromLocalStorage()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    private fun getTokenFromLocalStorage(): String? {
        return sharedPreferences.getString("token", null)
    }
   suspend fun login(data: LoginData): JsonObject {
        return api.postLogin(data).await()
   }

    suspend fun signup(data: SignupData): JsonObject {
        return api.postSignup(data).await()
    }

    suspend fun researchByName(userId: String, textsearch: String): JsonArray {
        return api.getresearchByName(userId, textsearch).await()
    }

    suspend fun addFriend(data: AddFriendData, iduser: String): JsonObject {
        return api.postaddFriend(data, iduser).await()
    }

    suspend fun answerFriend(data: UpdateFriendData, iduser: String): JsonObject {
        return api.AnswerInvitation(data, iduser).await()
    }

    suspend fun listFriendSend(textsearch: String): JsonObject {
        return api.getfriendsSend(textsearch).await()
    }

    suspend fun listFriendReceived(textsearch: String): JsonObject {
        return api.getfriendsReceived(textsearch).await()
    }

    suspend fun listMyFriend(textsearch: String): JsonObject {
        return api.getMyfriends(textsearch).await()
    }

    suspend fun listGames(): JsonObject {
        return api.getGames().await()
    }
}