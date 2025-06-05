package com.example.meta_habit.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
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
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.ui.theme.Gray50
import com.example.meta_habit.ui.theme.MetaHabitTheme
import com.example.meta_habit.ui.theme.RedLight
import com.example.meta_habit.ui.theme.getColorsTextField
import com.example.meta_habit.ui.utils.getReminderTimeDay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemLazyCheck(
    modifier: Modifier = Modifier,
    habitTask: HabitTaskEntity? = null,
    description: String = "",
    onChangeTask: (Boolean) -> Unit = {},
    onEditDescription: (String) -> Unit = {},
    onRemove: (() -> Unit)? = null,
    enabled: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    var stateDescription by remember { mutableStateOf(habitTask?.description ?: description) }

    Row(
        modifier = modifier
    ) {
        habitTask?.let {
            if (enabled) {
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
            value = stateDescription,
            onValueChange = {
                stateDescription = it
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            suffix = {
                if(!enabled){
                    IconButton(
                        onClick = onRemove!!
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "remove task")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onEditDescription(stateDescription)
                focusManager.clearFocus()
            })
        )


    }
}

@Composable
fun ItemListCheckEditable(
    modifier: Modifier = Modifier,
    habitTask: HabitTaskEntity? = null,
    onChangeTaskCheck: (Boolean) -> Unit = {},
    onChangeTaskDescription: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var description by remember { mutableStateOf(habitTask?.description ?: "") }
    var isCheck by remember { mutableStateOf(habitTask?.isCheck ?: false) }

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
                keyboardActions = KeyboardActions(onDone = {
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
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Blue.copy(alpha = 0.2F))
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "",
                tint = Color.Blue.copy(alpha = 0.5F),
                modifier = Modifier.padding()
            )
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 10.dp, vertical = 12.dp)
        ) {
            Text("Clase de ingles", fontWeight = FontWeight.Bold)
            Text(
                "Tu clase de ingles comienza en 4 horas",
                style = TextStyle(fontWeight = FontWeight.Light)
            )
        }

        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color.Blue)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditable(
    modifier: Modifier = Modifier,
    habitTask: HabitTaskEntity? = null,
    onChangeTaskCheck: (Boolean) -> Unit = {},
    onChangeTaskDescription: (String) -> Unit = {},
) {

    val focusManager = LocalFocusManager.current
    var description by remember { mutableStateOf(habitTask?.description ?: "") }
    var isCheck by remember { mutableStateOf(habitTask?.isCheck?: false) }
    val colorBackground = if(isCheck == true) Gray50 else MaterialTheme.colorScheme.background

    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardColors(
            containerColor = colorBackground,
            contentColor = Color.Blue,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.LightGray
        ),
        modifier = modifier
    ) {
        ListItem(
            headlineContent = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    enabled = isCheck.not(),
                    onValueChange = { text -> description = text },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onChangeTaskDescription(description)
                        focusManager.clearFocus()
                    }),
                    colors = getColorsTextField(baseColor = colorBackground)
                )
            },
            supportingContent = {
                AnimatedVisibility(visible = isCheck == true) {
                    habitTask?.dateCheck?.let {
                        Text(text= it.getReminderTimeDay())
                    }
                }
            },
            trailingContent = {
                CustomCircularCheckbox(
                    checked = habitTask?.isCheck ?: false,
                    onCheckedChange = {
                        isCheck = it;
                        onChangeTaskCheck(it)
                    }
                )
            },
            colors = ListItemColors(
                containerColor = colorBackground,
                headlineColor = Color.Gray,
                leadingIconColor = Color.Black,
                overlineColor = Color.Gray,
                supportingTextColor = Color.Gray,
                trailingIconColor = Color.Gray,
                disabledHeadlineColor = Color.Gray,
                disabledLeadingIconColor = Color.Gray,
                disabledTrailingIconColor = Color.Gray
            ),
            modifier = modifier
        )
    }
}


@Composable
fun CustomCircularCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(value = checked) }

    val circleColor = if (isChecked) RedLight else Color.Transparent
    val borderColor = if (isChecked) Color.Transparent else Color.Gray

    IconButton(
        modifier = modifier
            .size(30.dp)
            .border(width = 1.dp, color = borderColor, shape = CircleShape)
            .clip(CircleShape),
        onClick = {
            isChecked = isChecked.not()
            onCheckedChange(isChecked)
        },
        colors = IconButtonColors(
            containerColor = circleColor,
            contentColor = circleColor,
            disabledContainerColor = circleColor,
            disabledContentColor = circleColor
        )
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

    }
}

@Preview
@Composable
private fun CustomCircularCheckboxPreview() {
    CustomCircularCheckbox(
        onCheckedChange = {}
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun ItemLazyCheckPreview() {
    MetaHabitTheme {
        TaskEditable()
    }

}

