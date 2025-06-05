package com.example.meta_habit.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.screen.home.FilterTypeAndLabel
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.FilterType


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
            .background(color.value)
            .border(width = if(stateColorSelected.value == color) 2.dp else 1.dp, color = if(stateColorSelected.value == color) Color.Gray else color.value, shape = CircleShape)
            .clickable {
                onSelected(color)
            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldBasic(
    value: String,
    onValueChange: (String) -> Unit,
    isCenterText: Boolean = false,
    label: String? = null,
    modifier: Modifier = Modifier,
){
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        textStyle = if(isCenterText) TextStyle(
            textAlign = TextAlign.Center
        ) else LocalTextStyle.current,
        shape = RoundedCornerShape(12.dp),
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun SelectionColorPreview(){
    SelectionColor(
        stateColorSelected = remember { mutableStateOf(ColorType.PURPLE) }
    )

}