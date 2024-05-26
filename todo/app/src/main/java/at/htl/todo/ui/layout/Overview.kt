package at.htl.todo.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import at.htl.todo.ui.theme.TodoTheme

@Composable
fun Overview(model: Model, store: ModelStore?) {
    val todoDetailsId = model.todoDetailsId
    val uiState = model.uiState
    val tabIndex = uiState.selectedView

    Column(modifier = Modifier.fillMaxWidth()) {
        when (tabIndex) {
            0 -> TodosList(model = model, modifier = Modifier.padding(all = 32.dp), store = store)
            1 -> TodoDetails(model = model, todoId = todoDetailsId.toString() , store = store)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverviewPreview() {
    fun todo(id: Long, title: String): Todo {
        val todo = Todo()
        todo.id = id
        todo.title = title
        return todo
    }

    val model = Model()
    model.uiState.selectedView = 0
    model.todos = arrayOf(todo(1, "homework"), todo(2, "this is a todo with a very long text which should be truncated"))

    TodoTheme {
        Overview(model = model, store = null)
    }
}
