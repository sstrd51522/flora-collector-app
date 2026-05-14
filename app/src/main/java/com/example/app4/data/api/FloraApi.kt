package com.example.app4.data.api

import com.example.app4.data.model.SurveySite
import retrofit2.http.*

interface FloraApi {
    // 方法 1：获取所有调查点 (GET)
    @GET("posts") // 借用 jsonplaceholder 的 posts 路径模拟站点获取
    suspend fun getRemoteSites(): List<SurveySite>

    // 方法 2：上传新的调查点 (POST)
    @POST("posts")
    suspend fun uploadSite(@Body site: SurveySite): SurveySite

    // 方法 3：根据 ID 获取特定站点详情 (GET)
    @GET("posts/{id}")
    suspend fun getSiteDetails(@Path("id") id: String): SurveySite
}