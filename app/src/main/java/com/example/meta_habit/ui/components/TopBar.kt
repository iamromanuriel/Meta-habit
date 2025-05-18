package com.example.meta_habit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopBarDialogBasic(
    onClose: () -> Unit = {},
    onDone: () -> Unit = {},
    modifier: Modifier = Modifier,
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        IconButton(
            onClick = onClose,
        ) {
            Icon( imageVector = Icons.Default.Close, contentDescription = "")
        }

        Text(text="Edita tu habito", style = MaterialTheme.typography.titleMedium)

        IconButton(
            onClick = onDone,
        ) {
            Icon( imageVector = Icons.Default.Done, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun TopBarDialogBasicPreview(){
    TopBarDialogBasic()
}