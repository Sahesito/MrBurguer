package com.sahe.mrburguer.activity.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sahe.mrburguer.R

@Composable
fun DeliveryInfoBox(
    onPlaceOrder: (String) -> Unit
) {
    var deliveryAddress by remember { mutableStateOf("") }
    var isEditingAddress by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(
                color = colorResource(R.color.grey),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.clickable { isEditingAddress = true }
        ) {
            Text(
                text = "Your Delivery Address",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))

            if (isEditingAddress) {
                OutlinedTextField(
                    value = deliveryAddress,
                    onValueChange = { deliveryAddress = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter your address") },
                    singleLine = false,
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.orange),
                        unfocusedBorderColor = colorResource(R.color.orange),
                        cursorColor = colorResource(R.color.orange)
                    )
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.location),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (deliveryAddress.isEmpty()) "Tap to add address" else deliveryAddress,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (deliveryAddress.isEmpty()) Color.Gray else Color.Black
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        InfoItem(
            title = "Payment Method",
            content = "Cash",
            icon = painterResource(R.drawable.credit_card)
        )
    }

    Button(
        onClick = {
            if (deliveryAddress.isNotEmpty()) {
                onPlaceOrder(deliveryAddress)
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (deliveryAddress.isNotEmpty())
                colorResource(R.color.orange)
            else
                Color.Gray
        ),
        enabled = deliveryAddress.isNotEmpty(),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text = "Place Order",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun InfoItem(
    title: String,
    content: String,
    icon: Painter
) {
    Column {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = content,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}