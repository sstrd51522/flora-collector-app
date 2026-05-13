package com.example.app4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app4.data.api.RetrofitClient
import com.example.app4.data.database.AppDatabase
import com.example.app4.data.repository.PostRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 临时在这里初始化，后面建议用 ViewModel 和 Hilt
        val db = AppDatabase.getDatabase(this)
        val repository = PostRepository(RetrofitClient.instance, db.postDao())

        setContent {
            val posts by repository.allPosts.collectAsState(initial = emptyList())
            val scope = rememberCoroutineScope()

            Surface(color = MaterialTheme.colorScheme.background) {
                Column {
                    Button(
                        onClick = { scope.launch { repository.refreshPosts() } },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("刷新数据")
                    }

                    LazyColumn {
                        items(posts) { post ->
                            Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = post.body, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}