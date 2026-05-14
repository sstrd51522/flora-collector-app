package com.example.app4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app4.ui.theme.FloraGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSiteScreen(onBack: () -> Unit, onStartRecord: (String, String) -> Unit) {
    var weather by remember { mutableStateOf("晴") }
    var habitat by remember { mutableStateOf("林下") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新建调查点", color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "返回", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FloraGreen)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, "位置", tint = FloraGreen)
                Text(" 正在获取地理位置中...", fontWeight = FontWeight.Bold)
            }
            Text("经度：113.8    纬度：22.9    海拔：50m", modifier = Modifier.padding(vertical = 8.dp))

            Spacer(modifier = Modifier.height(24.dp))
            Text("天气情况：", fontWeight = FontWeight.Bold)
            Row {
                listOf("晴", "阴", "雨").forEach {
                    FilterChip(
                        selected = weather == it,
                        onClick = { weather = it },
                        label = { Text(it) },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("生境类型：", fontWeight = FontWeight.Bold)
            Row {
                listOf("林下", "草甸", "灌丛").forEach {
                    FilterChip(
                        selected = habitat == it,
                        onClick = { habitat = it },
                        label = { Text(it) },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 核心：底部的“开始记录植物”大圆圈按钮
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { onStartRecord(weather, habitat) },
                    modifier = Modifier.size(80.dp).background(FloraGreen, CircleShape)
                ) {
                    Icon(Icons.Default.Add, "开始记录", tint = Color.White, modifier = Modifier.size(40.dp))
                }
                Text("开始记录植物", modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center)
            }
        }
    }
}