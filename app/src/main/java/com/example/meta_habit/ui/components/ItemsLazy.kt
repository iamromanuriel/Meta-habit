package com.example.meta_habit.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemLazyCheck(
    modifier: Modifier = Modifier,
    description: String = "",
    onChangeTask: (String) -> Unit = {},
    enabled: Boolean = false
){
    var isChecked by remember { mutableStateOf(false) }
    var stateDescription by remember { mutableStateOf(description) }

    Row (
        modifier = modifier
    ){
        if(enabled){
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                }
            )
        }

        TextField(
            modifier = modifier.fillMaxWidth(),
            value = stateDescription,
            onValueChange = {
                stateDescription = it
            },
        )
    }
}

@Preview
@Composable
private fun ItemLazyCheckPreview(){
        ItemLazyCheck()

}