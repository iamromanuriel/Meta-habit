package com.example.meta_habit.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemLazyCheck(
    modifier: Modifier = Modifier,
    description: String = ""
){
    Row (
        modifier = modifier
    ){
        Checkbox(
            checked = true,
            onCheckedChange = {}
        )


        TextField(
            modifier = modifier.fillMaxWidth(),
            value = description,
            label = { Text("Description") },
            onValueChange = {  },
        )
    }
}

@Preview
@Composable
private fun ItemLazyCheckPreview(){
        ItemLazyCheck()

}