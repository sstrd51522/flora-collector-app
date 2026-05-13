package com.example.app4

import com.example.app4.data.api.PostApi
import com.example.app4.data.database.PostDao
import com.example.app4.data.model.Post
import com.example.app4.data.repository.PostRepository
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class PostRepositoryTest {

    private val api = mock(PostApi::class.java)
    private val dao = mock(PostDao::class.java)
    private val repository = PostRepository(api, dao)

    @Test
    fun testFormatTitleLogic() {
        val post = Post(1, 1, "dgut-research", "body")
        println("\n[原始 Title] : ${post.title}")

        val formatted = repository.formatTitle(post)
        println("[转换后 Title]: $formatted")

        // 验证逻辑
        assertEquals("DGUT-RESEARCH", formatted)
        println("✅ 逻辑验证通过：小写已成功转换为大写！\n")
    }


}