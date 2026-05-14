package com.example.app4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app4.data.model.SurveySite
import com.example.app4.ui.theme.FloraGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteDetailScreen(site: SurveySite, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("详情数据", color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "返回", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FloraGreen)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(24.dp)) {
            Text("站点详情", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = FloraGreen)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("唯一标识:", site.siteId)
            DetailItem("地理经度:", site.longitude.toString())
            DetailItem("地理纬度:", site.latitude.toString())
            DetailItem("海拔高度:", "${site.altitude} 米")
            DetailItem("天气状况:", site.weather)
            DetailItem("生境类型:", site.habitatType)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = FloraGreen)) {
                Text("返回列表")
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
        Text(label, modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
        Text(value, color = Color.DarkGray)
    }
}