package com.example.app4.data.repository

import com.example.app4.data.api.PostApi
import com.example.app4.data.database.PostDao
import com.example.app4.data.model.Post
import kotlinx.coroutines.flow.Flow

class PostRepository(private val api: PostApi, private val dao: PostDao) {

    // 暴露给 UI 的单一数据源（Room 自动推送更新）
    val allPosts: Flow<List<Post>> = dao.getAllPosts()

    // 核心逻辑：刷新网络数据并更新数据库
    suspend fun refreshPosts() {
        try {
            val networkPosts = api.getPosts()
            dao.insertPosts(networkPosts)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 逻辑转换测试示例：将 Title 转为大写（用于单元测试）
    fun formatTitle(post: Post): String {
        return post.title.uppercase()
    }
}