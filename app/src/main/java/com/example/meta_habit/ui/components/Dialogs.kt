package com.example.meta_habit.ui.components

import android.text.Layout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.meta_habit.ui.utils.RepeatType

@Composable
fun LayoutOptions(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
    onPin: () -> Unit = {},
    onNavDetail: () -> Unit = {}
){
    Column {

        TextButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "", modifier = modifier.padding(end = 10.dp))
            Text(text = "Eliminar", modifier = modifier.fillMaxWidth().padding(vertical = 10.dp))
        }

        TextButton(onClick = onNavDetail) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "", modifier = modifier.padding(end = 10.dp))
            Text(text = "Detalle", modifier = modifier.fillMaxWidth().padding(vertical = 10.dp))
        }

        TextButton(onClick = onPin) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "", modifier = modifier.padding(end = 10.dp))
            Text(text = "Anclar", modifier = modifier.fillMaxWidth().padding(vertical = 10.dp))
        }
    }
}

@Composable
fun DialogFullScreen(
    onDismiss: () -> Unit,
    onCreate: () -> Unit,
    showDialog: Boolean,
    layout: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Dialog (
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        AnimatedVisibility(
            visible = showDialog,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = FastOutSlowInEasing
                )
            ) + scaleIn(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ) + scaleOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.fillMaxWidth()
                    ){
                        IconButton (
                            onClick = { onDismiss() },
                            modifier = modifier.padding()

                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        }

                        Text(text="Crea tu nota", style = MaterialTheme.typography.titleMedium)

                        TextButton(
                            onClick = { onCreate() },
                            modifier = modifier.padding()
                        ) {
                            Text("Crear")
                        }
                    }

                    layout()
                }
            }
        }
    }
}

@Composable
fun DialogBasic(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,

    ){
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutOptionsActionPreview(){
    LayoutOptions(
        onDelete = {},
        onPin = {},
        onNavDetail = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LayoutOptionsPreview(){
    MaterialTheme {
        Scaffold { innerPadding ->
            DialogBasic(
                modifier = Modifier.padding(innerPadding),
                onDismiss = {},
                content = {
                    LayoutOptionRepeat(
                        options = RepeatType.entries.toTypedArray(),
                        onSelected = {},
                        itemToString = { it.value },
                        selected = RepeatType.MONTHLY
                    )
                }
            )
        }
    }

}