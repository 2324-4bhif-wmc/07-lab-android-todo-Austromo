package at.htl.todo.ui.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import at.htl.todo.ui.theme.TodoTheme

@Composable
fun TodosList(model: Model, modifier: Modifier = Modifier, store: ModelStore?) {
    val todos = model.todos
    LazyColumn(
        modifier = modifier.padding(
            start = 16.dp,
            top = 40.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        items(todos.size) { index ->
            TodoRow(todo  = todos[index], store)
            HorizontalDivider()
        }
    }
}

@Composable
fun TodoRow(todo: Todo, store: ModelStore?) {
    val cardColor = if (todo.completed) Color(0xFFCEFAD0) else Color(0xFFFFE8E7)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(5.dp)),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 10.dp,
                    end = 16.dp,
                    bottom = 0.dp
                ),
            //.horizontalScroll(rememberScrollState())
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(8.dp))
            Spacer(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.completed,
                onCheckedChange = {
                    store?.apply { m ->
                        m.todos.first { t -> t.id == todo.id }.completed = !todo.completed
                    }
                }
            )
            IconButton(onClick = {
                store?.selectView(1)
                store?.setTodoDetailsId(todo.id)
            }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info button"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    val model = Model()
    val todo = Todo()
    todo.id = 1
    todo.title = "First Todo"
    model.todos = arrayOf(todo)

    TodoTheme {
        TodosList(model = model, store = null)
    }
}
