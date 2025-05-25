package com.example.meta_habit.ui.components

import android.content.ClipData.Item
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.screen.home.FilterTypeAndLabel
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.FilterType
import com.example.meta_habit.ui.utils.NoteType
import com.example.meta_habit.ui.utils.getNameMouthSpanish
import java.util.Date


@Composable

fun SelectionColor(
    modifier: Modifier = Modifier,
    stateColorSelected: State<ColorType>,
    onSelected: (ColorType) -> Unit = {},
){
    Row (
        modifier = modifier.fillMaxWidth()
    ){
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth().padding(10.dp)
        ) {
            items(ColorType.entries.size){
                ItemColorSelection(
                    color = ColorType.entries[it],
                    stateColorSelected = stateColorSelected,
                    onSelected = onSelected)
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DropdownSelectDate(
    modifier: Modifier = Modifier,
    stateFilter: FilterTypeAndLabel,
    onSelected: (FilterType)-> Unit = {}
){
    var isExpanded by  remember { mutableStateOf(false) }

    Column {
        TextButton(
            onClick = { isExpanded = !isExpanded },
        ) {
            Text(text= stateFilter.label?: "", color = Color.Black, style = MaterialTheme.typography.headlineSmall)

        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = !isExpanded },
        ) {

            DropdownMenuItem(
                text = { Text(text = "Hoy") },
                onClick = {
                    onSelected(FilterType.TODAY)
                    isExpanded = !isExpanded
                },
            )

            DropdownMenuItem(
                text = { Text(text = "3 Dias") },
                onClick = {
                    onSelected(FilterType.TREE_DAYS)
                    isExpanded = !isExpanded
                },
            )

            DropdownMenuItem(
                text = { Text(text = "Semana") },
                onClick = {
                    onSelected(FilterType.WEEK)
                    isExpanded = !isExpanded
                },
            )
        }
    }
}

@Composable
fun ItemColorSelection(
    modifier: Modifier = Modifier,
    stateColorSelected: State<ColorType>,
    color: ColorType,
    onSelected: (ColorType) -> Unit = {}
){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(50.dp)
            .background(if(stateColorSelected.value.value == color.value) color.value else color.value.copy(alpha = 0.3F))
            .border(width = if(stateColorSelected.value == color) 2.dp else 1.dp, color = if(stateColorSelected.value == color) Color.Black else color.value, shape = CircleShape)
            .clickable {
                onSelected(color)
            }
    )
}

@Preview
@Composable
fun SelectionColorPreview(){
    SelectionColor(
        stateColorSelected = remember { mutableStateOf(ColorType.GRAY) }
    )

}