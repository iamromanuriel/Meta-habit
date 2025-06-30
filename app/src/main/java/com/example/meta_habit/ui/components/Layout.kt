package com.example.meta_habit.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.theme.containerTextField
import com.example.meta_habit.ui.utils.LabelTypes



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCreateCheckList(
    modifier: Modifier = Modifier,
    listTask: List<String>? = null,
    onCreateNewTask: (String) -> Unit,
    onEditTask: (String, Int) -> Unit,
    onRemoveTask: (Int) -> Unit,
    content: @Composable () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var taskDescription by remember { mutableStateOf("") }
    LazyColumn {
        item {

            content()

            listTask?.let {
                TextField(
                    modifier = modifier.fillMaxWidth(),
                    value = taskDescription,
                    label = { Text("Tareas") },
                    onValueChange = { text -> taskDescription = text },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if(taskDescription.isEmpty()){
                            focusManager.clearFocus()
                        }else{
                            onCreateNewTask(taskDescription)
                            taskDescription = ""
                        }

                    }), shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = containerTextField,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = modifier.height(20.dp))
            }

        }
        listTask?.let {
            itemsIndexed(listTask) { index, item ->
                ItemLazyCheck(
                    description = item,
                    onEditDescription = { onEditTask(it, index) },
                    onRemove = { onRemoveTask(index) }
                )
            }
        }

    }
}


val optionRepeat = listOf<String>("Diario", "Semanal", "Mensual", "3 dias")

@Composable
fun <T> LayoutOptionRepeat(
    modifier: Modifier = Modifier,
    title: String,
    options: Array<T>,
    selected: T,
    onSelected: (T) -> Unit,
    itemToString: (T) -> String
) {
    val selectedState = remember { mutableStateOf(selected) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        options.forEach { item ->
            Row(
                modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = itemToString(item))
                RadioButton(
                    selected = selectedState.value == item,
                    onClick = { onSelected(item) }
                )
            }
        }

    }
}

@Preview
@Composable
fun LayoutOptionRepeatPreview() {
    Scaffold { innerPadding ->
        LayoutOptionRepeat(
            modifier = Modifier.padding(innerPadding),
            title = "Etiquetas",
            options = LabelTypes.entries.toTypedArray(),
            selected = LabelTypes.WORK,
            onSelected = {},
            itemToString = { it.value }
        )
    }
}


