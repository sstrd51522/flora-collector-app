package com.example.app4

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.app4.data.database.AppDatabase
import com.example.app4.data.database.PostDao
import com.example.app4.data.model.Post
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // 使用 Robolectric 模拟安卓环境
class PostDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: PostDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.postDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun testInsertAndReadPost() = runBlocking {
        // 1. 准备数据
        val post = Post(1, 1, "莞工科研项目", "测试内容：KSP2 与 Room 兼容性验证")
        println("\n[准备插入数据] -> $post")

        // 2. 执行插入
        dao.insertPost(post)
        println(">>> 数据已写入内存数据库")

        // 3. 执行查询
        val result = dao.getPostById(1)
        println("[从数据库读取成功] -> $result")

        // 4. 断言验证
        assertEquals("莞工科研项目", result?.title)
        println("结果匹配：数据库层工作正常！\n")
    }

    @Test
    fun testDeletePost() = runBlocking {
        val post = Post(2, 1, "即将被删除的帖子", "测试内容：验证 Room 的 Delete 功能")

        // 1. 插入展示
        println("[1. 动作] 正在准备一条临时数据 -> ID: ${post.id}, 标题: ${post.title}")
        dao.insertPost(post)

        // 2. 确认插入成功
        val beforeDelete = dao.getPostById(2)
        println("[2. 确认] 插入后检索结果: ${if (beforeDelete != null) "✅ 已存在" else "❌ 插入失败"}")

        // 3. 执行删除
        println("[3. 动作] 正在执行 dao.deletePost(post) ...")
        dao.deletePost(post)

        // 4. 验证结果
        val result = dao.getPostById(2)
        println("[4. 验证] 再次检索 ID 为 2 的结果 -> $result (预期应为 null)")

        // 5. 断言
        Assert.assertEquals(null, result)
        println("结论：数据已成功从数据库中彻底抹除，逻辑验证通过！")
    }


}