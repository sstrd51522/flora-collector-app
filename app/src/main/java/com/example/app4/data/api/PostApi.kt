package com.example.app4.data.api

import com.example.app4.data.model.Post
import retrofit2.http.*

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    // 新增方法 2：根据 ID 获取单个帖子
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    // 新增方法 3：POST 提交
    @POST("posts")
    suspend fun createPost(@Body post: Post): Post
}