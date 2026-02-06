package com.sahe.mrburguer.activity.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sahe.mrburguer.R
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sahe.mrburguer.activity.details.DetailEachFoodActivity

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavoriteScreen(
                ManagmentFavorite(this),
                onBackClick = { finish() }
            )
        }
    }
}

@Composable
fun FavoriteScreen(
    managmentFavorite: ManagmentFavorite = ManagmentFavorite(LocalContext.current),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val favoriteItems = remember { mutableStateOf(managmentFavorite.getListFavorite()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier.padding(top = 36.dp)
            ) {
                val (backBtn, favTxt) = createRefs()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(favTxt) {
                            centerTo(parent)
                        },
                    text = "My Favorites",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Image(
                    painter = painterResource(R.drawable.back_grey),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(backBtn) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .clickable { onBackClick() }
                )
            }
        }

        if (favoriteItems.value.isEmpty()) {
            item {
                Text(
                    text = "No favorites yet",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        } else {
            items(
                items = favoriteItems.value,
                key = { item -> item.Title }
            ) { item ->
                FoodItem(
                    item = item,
                    onItemClick = {
                        val intent = Intent(context, DetailEachFoodActivity::class.java)
                        intent.putExtra("object", item)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}