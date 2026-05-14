package com.example.app4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app4.ui.theme.FloraGreen

/**
 * 页面 2：录入植物标本
 * @param onBack 返回上一页（页面 1）的回调
 * @param onSave 保存数据并返回主页的回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSpecimenScreen(
    onBack: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    // 定义状态变量，对应数据库实体字段
    var plantName by remember { mutableStateOf("") }
    var lifeForm by remember { mutableStateOf("乔木") } // 默认生活型
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("录入植物标本", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FloraGreen)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 1. 图片占位区
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Place, contentDescription = "相机", modifier = Modifier.size(48.dp))
                    Text("拍摄或者上传图片")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. 中文名称输入框
            OutlinedTextField(
                value = plantName,
                onValueChange = { plantName = it },
                label = { Text("中文名称：") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. 生活类型选择
            Text("生活类型：", style = MaterialTheme.typography.labelLarge)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                listOf("乔木", "灌木", "草本").forEach { type ->
                    FilterChip(
                        selected = lifeForm == type,
                        onClick = { lifeForm = type },
                        label = { Text(type) },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FloraGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. 观察笔记输入框
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("观察笔记：") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.weight(1f))

            // 5. 保存按钮组
            Button(
                onClick = { onSave(plantName, lifeForm, notes) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = FloraGreen)
            ) {
                Text("保存，并录入下一条")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onSave(plantName, lifeForm, notes) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5E1A5)) // 浅绿色
            ) {
                Text("保存，并完成调查点", color = Color.Black)
            }
        }
    }
}
