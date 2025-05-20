package com.example.meta_habit.ui.components

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp




enum class ColorType(val value: Color){
    RED(Color.Red),
    Blue(Color.Blue),
    YELLOW(Color.Yellow),
    GREEN(Color.Green),
    GRAY(Color.Gray)
}


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