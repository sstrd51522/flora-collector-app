package com.example.app4.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app4.data.model.SurveySite
import com.example.app4.ui.theme.FloraGreen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    sites: List<SurveySite>,
    onAddClick: () -> Unit,
    onSiteClick: (String) -> Unit,
    onDeleteSite: (SurveySite) -> Unit // 删除回调
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("科考记录", fontWeight = FontWeight.Bold) }) },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, "首页") }, label = { Text("首页") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Share, "生成文件") }, label = { Text("生成文件") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Person, "我的") }, label = { Text("我的") })
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick, containerColor = FloraGreen, contentColor = Color.White) {
                Icon(Icons.Default.Add, "新建")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            items(sites) { site ->
                var showDeleteDialog by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        // 核心：区分单击和长按
                        .combinedClickable(
                            onClick = { onSiteClick(site.siteId) },
                            onLongClick = { showDeleteDialog = true }
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("大岭山", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(Date(site.createTime)), fontSize = 12.sp, color = Color.Gray)
                        Text("生境: ${site.habitatType} | 天气: ${site.weather}", fontSize = 12.sp)
                    }
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("删除记录") },
                        text = { Text("确定要删除这条科考调查点吗？对应植物标本也会一并删除。") },
                        confirmButton = {
                            TextButton(onClick = {
                                onDeleteSite(site)
                                showDeleteDialog = false
                            }) { Text("确认", color = Color.Red) }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) { Text("取消") }
                        }
                    )
                }
            }
        }
    }
}