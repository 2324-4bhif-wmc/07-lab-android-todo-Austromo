package at.htl.todo.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore

//val TAG = DetailsView::class.simpleName

@Composable
fun TodoDetails(model: Model, modifier: Modifier = Modifier, todoId: String, store: ModelStore?) {
    val todos = model.todos

    LazyColumn(
        modifier = modifier.padding(
            start = 16.dp,
            top = 70.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        items(todos.size) { index ->
            if (todos[index].id.toString() == todoId) {
                val todo = todos[index]

                Card(
                    modifier = Modifier
                        .padding(10.dp).
                        fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    elevation =  CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF7F2F9),
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color(0xFFF7F2F9))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ChipView(
                                text = todo.id.toString(),
                                color = Color.Green,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = todo.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = todo.completed,
                                onCheckedChange = {
                                    store?.apply { m ->
                                        m.todos.first { t -> t.id == todo.id }.completed =
                                            !todo.completed
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChipView(text: String, color: Color) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(.08f))
    ) {
        Text(
            text = text, modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
            style = MaterialTheme.typography.titleSmall
        )
    }
}
