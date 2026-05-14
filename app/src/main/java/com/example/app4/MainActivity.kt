package com.example.app4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.app4.data.database.AppDatabase
import com.example.app4.data.model.SurveySite
import com.example.app4.data.model.PlantSpecimen
import com.example.app4.data.repository.FloraRepository
import com.example.app4.ui.screens.* // 确保导入了你的 screens 包
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val repository = FloraRepository(db.floraDao())

        setContent {
            val navController = rememberNavController()
            val sites by repository.allSites.collectAsState(initial = emptyList())
            val scope = rememberCoroutineScope()

            NavHost(navController = navController, startDestination = "login") {

                // 登录页
                composable("login") { LoginScreen { navController.navigate("main") } }

                // 主页：科考记录列表
                composable("main") {
                    MainScreen(
                        sites = sites,
                        onAddClick = { navController.navigate("page1") }, // 点击+去页面1
                        onSiteClick = { id -> navController.navigate("detail/$id") },
                        onDeleteSite = { site -> scope.launch { repository.deleteSite(site) } } // 处理长按删除
                    )
                }

                // 页面 1：新建调查点
                composable("page1") {
                    NewSiteScreen(
                        onBack = { navController.popBackStack() },
                        onStartRecord = { weather, habitat ->
                            scope.launch {
                                // 1. 创建站点并获取 UUID
                                val newSite = SurveySite(
                                    longitude = 113.8, latitude = 22.9, altitude = 50.0,
                                    weather = weather, habitatType = habitat
                                )
                                repository.createSite(newSite)
                                // 2. 带着 ID 冲向页面 2 (录入标本)
                                navController.navigate("page2/${newSite.siteId}")
                            }
                        }
                    )
                }

                // 页面 2：录入植物标本
                composable("page2/{siteId}") { backStackEntry ->
                    val siteId = backStackEntry.arguments?.getString("siteId") ?: ""
                    NewSpecimenScreen(
                        onBack = { navController.popBackStack() },
                        onSave = { name, type, note ->
                            scope.launch {
                                repository.addSpecimen(PlantSpecimen(
                                    siteId = siteId, plantName = name,
                                    lifeForm = type, notes = note, imagePath = ""
                                ))
                                // 完成后返回主页并清空中间栈
                                navController.navigate("main") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }
                        }
                    )
                }

                // 详情页
                composable("detail/{siteId}") { backStackEntry ->
                    val siteId = backStackEntry.arguments?.getString("siteId")
                    val site = sites.find { it.siteId == siteId }
                    site?.let { SiteDetailScreen(it) { navController.popBackStack() } }
                }
            }
        }
    }
}