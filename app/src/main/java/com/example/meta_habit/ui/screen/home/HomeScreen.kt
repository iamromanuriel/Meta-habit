package com.example.meta_habit.ui.screen.home

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.R
import com.example.meta_habit.ui.components.CardNoteBasic
import com.example.meta_habit.ui.components.DropdownSelectDate
import com.example.meta_habit.ui.components.LayoutOptions
import com.example.meta_habit.ui.nav.TRANSFORM_KEY
import com.example.meta_habit.ui.state.ActionDeleteHabit
import com.example.meta_habit.ui.theme.bluePrimary
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    animatedVisibilityScope: AnimatedContentScope,
    onNavigateToDetail: () -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {}
) {
    var scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val snackBarHost = remember { SnackbarHostState() }
    var showButtonSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val listHabit = viewModel.listOfHabit.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    LaunchedEffect (key1 = Unit) {
        viewModel.delete.collect { action ->

            when(action){
                ActionDeleteHabit.Await -> {}
                is ActionDeleteHabit.Fail -> { Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show() }
                ActionDeleteHabit.Success -> { showDeleteDialog = false }
            }
        }
    }


    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    DropdownSelectDate(
                        stateFilter = selectedFilter,
                        onSelected = viewModel::onSelectFilter
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onNavigateToNotification()
                        }) {
                        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.sharedBounds(
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedContentState = rememberSharedContentState(TRANSFORM_KEY)
                ),
                containerColor = bluePrimary,
                contentColor = MaterialTheme.colorScheme.background,
                onClick = onNavigateToCreate
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        }
    ) { innerPadding ->

        LazyColumn (
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            items(listHabit.value) { habit ->
                CardNoteBasic(
                    habit = habit,
                    onClickOption = {
                        showButtonSheet = true
                        viewModel.onSelectNote(habit)
                    },
                    onClick = {
                        viewModel.onSelectNote(habit)
                        onNavigateToDetail()
                    }
                )
            }
        }

        if (showButtonSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showButtonSheet = false
                },
                sheetState = sheetState
            ) {
                LayoutOptions(
                    onNavDetail = onNavigateToDetail,
                    onDelete = { showDeleteDialog = true },
                    onPin = viewModel::onPin
                )
            }
        }

        if(showDeleteDialog){
            AlertDialog(
                title = { Text(text = stringResource(R.string.label_delete)) },
                text = { Text(text = stringResource(R.string.quest_confirm_delete)) },
                onDismissRequest = { showDeleteDialog = false },
                confirmButton = {
                    Button(onClick = viewModel::onDeleteNote){
                        Text(stringResource(R.string.option_ok))
                    }
                },
                dismissButton = {
                    OutlinedButton (onClick = { showDeleteDialog = false }) {
                        Text(stringResource(R.string.label_cancel))
                    }
                }
            )
        }


    }
}

@Composable
fun AnimateVectorDrawable() {
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeScreenPreview() {
}