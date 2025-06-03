package com.example.meta_habit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import com.example.meta_habit.ui.theme.RedLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemLazyCheck(
    modifier: Modifier = Modifier,
    habitTask: HabitTaskEntity? = null,
    description: String = "",
    onChangeTask: (Boolean) -> Unit = {},
    onEditDescription: (String) -> Unit = {},
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

    ListItem(
        headlineContent = {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = description,
                onValueChange = { text -> description = text },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onChangeTaskDescription(description)
                    focusManager.clearFocus()
                }),
                colors =  TextFieldDefaults.colors(
                        // Colores del contenedor (fondo)
                        focusedContainerColor =  MaterialTheme.colorScheme.background, // Un azul claro
                        unfocusedContainerColor = MaterialTheme.colorScheme.background, // Un verde muy claro
                        disabledContainerColor = Color(0xFFF5F5F5), // Gris claro para deshabilitado
                        errorContainerColor = Color(0xFFFFEBEE),   // Rosa muy claro para error

                        // Colores del texto de entrada
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                        disabledTextColor = Color.Gray,
                        errorTextColor = Color.Red,

                        // Colores del cursor
                        cursorColor = Color.Blue, // El cursor cuando está enfocado
                        errorCursorColor = Color.Red, // El cursor cuando hay un error

                        // Colores del label (etiqueta)
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Transparent,
                        disabledLabelColor = Color.LightGray,
                        errorLabelColor = Color.Red,

                        // Colores del placeholder
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray,
                        disabledPlaceholderColor = Color.LightGray,
                        errorPlaceholderColor = Color.Red,

                        // Colores de los íconos (leadingIcon y trailingIcon)
                        focusedLeadingIconColor = Color.Blue,
                        unfocusedLeadingIconColor = Color.Gray,
                        disabledLeadingIconColor = Color.LightGray,
                        errorLeadingIconColor = Color.Red,

                        focusedTrailingIconColor = Color.Blue,
                        unfocusedTrailingIconColor = Color.Gray,
                        disabledTrailingIconColor = Color.LightGray,
                        errorTrailingIconColor = Color.Red,

                        // Colores de la línea indicadora (subrayado)
                        focusedIndicatorColor = Color.Blue,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.LightGray,
                        errorIndicatorColor = Color.Red,

                        // Colores de la línea de ayuda (helper text)
                        focusedSupportingTextColor = Color.DarkGray,
                        unfocusedSupportingTextColor = Color.Gray,
                        disabledSupportingTextColor = Color.LightGray,
                        errorSupportingTextColor = Color.Red,

                        // Colores del prefijo y sufijo
                        focusedPrefixColor = Color.DarkGray,
                        unfocusedPrefixColor = Color.Gray,
                        disabledPrefixColor = Color.LightGray,
                        errorPrefixColor = Color.Red,

                        focusedSuffixColor = Color.DarkGray,
                        unfocusedSuffixColor = Color.Gray,
                        disabledSuffixColor = Color.LightGray,
                        errorSuffixColor = Color.Red
                    )
            )
        },
        supportingContent = {

        },
        trailingContent = {
            CustomCircularCheckbox(
                checked = habitTask?.isCheck ?: false,
                onCheckedChange = onChangeTaskCheck
            )
        },
        modifier = Modifier.
        border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
    )
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
                modifier = Modifier.size(16.dp) // Tamaño de la palomita
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

@Preview(showBackground = true)
@Composable
private fun ItemLazyCheckPreview() {
    TaskEditable()
}

