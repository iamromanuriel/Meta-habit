package com.example.meta_habit.ui.screen.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.svg.SvgDecoder
import com.example.meta_habit.R
import com.example.meta_habit.ui.components.ItemNotification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onOpenNotification: () -> Unit = {}
) {
    val state = viewModel.stateUi.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                title = { Text("Notificaciones") }
            )
        }
    ) { innerPadding ->

        if(state.value.isEmpty()){
            EmptyScreen(modifier = Modifier.padding(innerPadding))
        }else{
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
            ) {
                items(state.value){ notification ->
                    ItemNotification(
                        notification = notification
                    )
                }

            }
        }
    }
}

@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth().fillMaxHeight()
    ) {
        AsyncImage(
            model = R.drawable.undraw_add_notes_9xls,
            contentDescription = null,
            modifier = modifier.size(200.dp).padding(20.dp)
        )
        Spacer(modifier = Modifier.height(300.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyScreenPreview() {
    Scaffold {
        EmptyScreen(modifier = Modifier.padding(it))
    }
}