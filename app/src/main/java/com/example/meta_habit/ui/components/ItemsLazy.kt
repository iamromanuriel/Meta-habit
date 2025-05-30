package com.example.meta_habit.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemLazyCheck(
    modifier: Modifier = Modifier,
    habitTask: HabitTaskEntity? = null,
    description: String = "",
    onChangeTask: (Boolean) -> Unit = {},
    enabled: Boolean = false
){

    Row (
        modifier = modifier
    ){

        habitTask?.let {
            if(enabled){
                Checkbox(
                    checked = habitTask.isCheck,
                    onCheckedChange = {
                        onChangeTask(habitTask.isCheck.not())
                    }
                )
            }
        }


        TextField(
            modifier = modifier.fillMaxWidth(),
            value = habitTask?.description?: description,
            onValueChange = {
                habitTask?.description = it
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ItemListCheckEditable(
    modifier: Modifier = Modifier,
    habitTask: HabitTaskEntity? = null,
    onChangeTaskCheck: (Boolean) -> Unit = {},
    onChangeTaskDescription: (String) -> Unit = {},
){
    val focusManager = LocalFocusManager.current
    var description by remember { mutableStateOf(habitTask?.description?:"") }
    var isCheck by remember { mutableStateOf(habitTask?.isCheck?:false) }

    Row {
        habitTask?.let {
            Checkbox(
                checked = isCheck,
                onCheckedChange = {
                    isCheck = isCheck.not()
                    onChangeTaskCheck(habitTask.isCheck.not())
                }
            )

            TextField(
                modifier = modifier.fillMaxWidth(),
                value = description,
                onValueChange = { text -> description = text },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions (onDone = {
                    onChangeTaskDescription(description)
                    focusManager.clearFocus()
                })
            )
        }
    }
}

@Composable
fun ItemNotification(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Blue.copy(alpha = 0.2F))
        ){
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "",
                tint = Color.Blue.copy(alpha = 0.5F),
                modifier = Modifier.padding())
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 10.dp, vertical = 12.dp)
        ) {
            Text("Clase de ingles", fontWeight = FontWeight.Bold)
            Text("Tu clase de ingles comienza en 4 horas", style = TextStyle(fontWeight = FontWeight.Light))
        }

        Box(
            modifier = Modifier.size(10.dp)
                .clip(CircleShape)
                .background(Color.Blue)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemLazyCheckPreview(){
    ItemListCheckEditable()
}

