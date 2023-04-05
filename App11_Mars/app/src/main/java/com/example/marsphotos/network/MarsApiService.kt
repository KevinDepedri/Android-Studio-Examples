package com.example.marsphotos.network
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    @GET("photos") // the endpoint "/photos" will be added to the BASE_URL of the retrofit object above
    suspend fun getPhotos(): String
}

object MarsApi {
    val retrofitService : MarsApiService by lazy { // Initialize when first used, not at the startup
        retrofit.create(MarsApiService::class.java)
    }
}