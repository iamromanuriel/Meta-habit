package com.example.meta_habit.ui.components

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class ColorSelection(
    val color: Color,
    var isSelected: Boolean
)

val optionColors = listOf(
    ColorSelection(Color.Red, false),
    ColorSelection(Color.Blue, false),
    ColorSelection(Color.Yellow, false),
    ColorSelection(Color.Green, false),
    ColorSelection(Color.Gray, false),
)

@Composable
fun SelectionColor(
    modifier: Modifier = Modifier
){
    Row (
        modifier = modifier.fillMaxWidth()
    ){
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth().padding(10.dp)
        ) {
            items(optionColors.size){
                ItemColorSelection(color = optionColors[it])
            }
        }
    }
}

@Composable
fun ItemColorSelection(
    modifier: Modifier = Modifier,
    color: ColorSelection
){

    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(50.dp)
            .background(color.color.copy(alpha = 0.3F))
            .border(width = 1.dp, color = color.color, shape = CircleShape)

    )
}

@Preview
@Composable
fun SelectionColorPreview(){
    SelectionColor()

}