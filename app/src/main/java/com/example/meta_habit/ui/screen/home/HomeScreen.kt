package com.example.meta_habit.ui.screen.home

import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.ui.screen.create.CreateScreen
import com.example.meta_habit.ui.components.CardNoteBasic
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.DropdownSelectDate
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.components.LayoutOptions
import com.example.meta_habit.ui.nav.TRANSFORM_KEY
import com.example.meta_habit.ui.theme.bluePrimary
import kotlinx.coroutines.launch
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

    var showButtonSheet by remember { mutableStateOf(false) }
    val listHabit = viewModel.listOfHabit.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()


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
                    println("onDismissRequest bottomSheet")
                    showButtonSheet = false
                },
                sheetState = sheetState
            ) {
                LayoutOptions(
                    onNavDetail = onNavigateToDetail,
                    onDelete = viewModel::onDeleteNote,
                    onPin = viewModel::onPin
                )
            }
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