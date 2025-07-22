package com.example.meta_habit.ui.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.ui.theme.GrayLight20
import com.example.meta_habit.ui.theme.bluePrimary
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getColorToOrdinalEnum
import com.example.meta_habit.ui.utils.getDateReminderThreeDaysString
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.toLocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardNoteBasic(
    modifier: Modifier = Modifier,
    habit: HabitWithTasks,
    onClick: () -> Unit = {},
    onClickOption: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(1.dp, GrayLight20),
        shape = RoundedCornerShape(20.dp),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = ((habit.habit.color ?: 0).getColorToOrdinalEnum()?.value
                ?: Color.White).copy(alpha = 0.3f),
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray

        )
    ) {
        Column(modifier = modifier) {

            Box(
                modifier = Modifier,
                contentAlignment = Alignment.TopEnd,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onClickOption
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Option habit",
                            tint = Color.Gray
                        )
                    }

                    Text(
                        text = habit.habit.title ?: "Title",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                }
                if (habit.habit.isPinned == true) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val dateReminderNextDay = when(getRepeatType(ordinal = habit.habit.repetition?:0)){
                    RepeatType.ONLY_ONE -> ""
                    RepeatType.DAILY -> "Hoy"
                    RepeatType.WEEKLY -> ""
                    RepeatType.MONTHLY -> ""
                    RepeatType.THREE_DAYS -> (habit.habit.dateReminder?:0).toLocalDate().getDateReminderThreeDaysString()
                    null -> ""
                }

                Text(text = dateReminderNextDay)
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))   {
                habit.task.take(3).forEach { task ->
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .border(1.dp, Color.Blue.copy(alpha = 0.6F), RoundedCornerShape(4.dp))
                                .background(if (task.isCheck) bluePrimary else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {

                            if (task.isCheck) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = modifier.scale(0.9F)
                                )
                            }
                        }
                        Text(
                            text = task.description,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            textDecoration = if (task.isCheck) TextDecoration.LineThrough else TextDecoration.None,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

        }
    }
}

@SuppressLint("NewApi")
@Preview
@Composable
fun CardNoteBasicPreview() {
    Scaffold { innerPadding ->
        Row(modifier = Modifier.padding(innerPadding)) {
            CardNoteBasic(
                habit = HabitWithTasks(
                    habit = HabitEntity(dateUpdate = 0, dateCreate = 0),
                    task = listOf()
                )
            )
        }
    }
}