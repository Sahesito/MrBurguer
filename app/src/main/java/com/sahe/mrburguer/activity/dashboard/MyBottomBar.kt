package com.sahe.mrburguer.activity.dashboard

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sahe.mrburguer.R
import com.sahe.mrburguer.activity.cart.CartActivity
import com.sahe.mrburguer.activity.favorite.FavoriteActivity

@Composable
fun MyBottomBar() {
    val bottomMenuItemsList = prepareBottomMenu()
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }

    NavigationBar(
        containerColor = colorResource(R.color.grey)
    ) {
        bottomMenuItemsList.forEach { bottomMenuItem ->
            NavigationBarItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    when (bottomMenuItem.label) {
                        "Cart" -> {
                            context.startActivity(Intent(context, CartActivity::class.java))
                        }
                        "Favorite" -> {
                            context.startActivity(Intent(context, FavoriteActivity::class.java))
                        }
                        else -> {
                            Toast.makeText(context, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}

data class BottomMenuItem(
    val label: String,
    val icon: Painter
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    return listOf(
        BottomMenuItem(label = "Home", icon = painterResource(R.drawable.btn_1)),
        BottomMenuItem(label = "Cart", icon = painterResource(R.drawable.btn_2)),
        BottomMenuItem(label = "Favorite", icon = painterResource(R.drawable.btn_3)),
        BottomMenuItem(label = "Profile", icon = painterResource(R.drawable.btn_5))
    )
}