package com.codelab.basics.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.codelab.basics.ui.theme.ui.theme.BasicsCodelabTheme


class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val typeUser = intent.getIntExtra("typeUser",1)
        enableEdgeToEdge()
        setContent {
            BasicsCodelabTheme {
                AppNavGraph(typeUser)
            }
        }
    }
}
