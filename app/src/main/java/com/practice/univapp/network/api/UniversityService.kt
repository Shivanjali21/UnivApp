package com.practice.univapp.network.api

import com.practice.univapp.network.data.UniversityItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityService {

  @GET("search")
  suspend fun getUniversity (
      @Query("country") country:String
  ) : Response<List<UniversityItem>>
}