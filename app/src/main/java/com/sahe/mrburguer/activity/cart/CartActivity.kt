package com.sahe.mrburguer.activity.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.sahe.mrburguer.R
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.colorResource
import com.sahe.mrburguer.activity.dashboard.MainActivity
import com.sahe.mrburguer.domain.ManagmentCart

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartScreen(
                ManagmentCart(this),
                onBackClick = { finish() },
                onOrderPlaced = {
                    Toast.makeText(this, "Order on the way", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun CartScreen(
    managmentCart: ManagmentCart = ManagmentCart(LocalContext.current),
    onBackClick: () -> Unit,
    onOrderPlaced: () -> Unit
) {
    val cartItem = remember { mutableStateOf(managmentCart.getListCart()) }
    val tax = remember { mutableStateOf(0.0) }
    calculatorCart(managmentCart, tax)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = 36.dp)
            ) {
                val (backBtn, cartTxt) = createRefs()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(cartTxt) {
                            centerTo(parent)
                        },
                    text = "Your Cart",
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

        if (cartItem.value.isEmpty()) {
            item {
                Text(
                    text = "Cart is empty",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(
                items = cartItem.value,
                key = { item -> item.Title }
            ) { item ->
                CartItem(
                    cartItems = cartItem.value,
                    item = item,
                    managmentCart = managmentCart,
                    onItemChange = {
                        calculatorCart(managmentCart, tax)
                        cartItem.value = ArrayList(managmentCart.getListCart())
                    }
                )
            }

            if (cartItem.value.isNotEmpty()) {
                item {
                    Text(
                        text = "Order Summary",
                        color = colorResource(R.color.darkPurple),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                }
                item {
                    CartSummary(
                        itemTotal = managmentCart.getTotalFee(),
                        tax = tax.value,
                        delivery = 10.0
                    )
                }
                item {
                    Text(
                        text = "Information",
                        color = colorResource(R.color.darkPurple),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                }
                item {
                    DeliveryInfoBox(
                        onPlaceOrder = { address ->
                            managmentCart.clearCart()
                            onOrderPlaced()
                        }
                    )
                }
            }
        }
    }
}

fun calculatorCart(
    managmentCart: ManagmentCart,
    tax: MutableState<Double>
) {
    val percentTax = 0.02
    tax.value = Math.round((managmentCart.getTotalFee() * percentTax) * 100) / 100.0
}