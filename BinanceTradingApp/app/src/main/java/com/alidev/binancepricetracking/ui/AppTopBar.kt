package com.alidev.binancepricetracking.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTopBar() {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                "Markets",
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.W600,
            )
        },
        actions = {
            IconButton(
                onClick = {
//                    coroutineScope.launch {
//                    }
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        },
    )
}