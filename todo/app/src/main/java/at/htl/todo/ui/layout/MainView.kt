package at.htl.todo.ui.layout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import at.htl.todo.ui.theme.TodoTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView @Inject constructor() {

    @Inject
    lateinit var store: ModelStore

    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SimpleCenterAlignedTopAppBar()
                TodosList(model = viewModel, modifier = Modifier.padding(all = 32.dp), store)
            }
        }
    }
}

@Composable
fun TodosList(model: Model, modifier: Modifier = Modifier, store: ModelStore) {
    val todos = model.todos
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(todos.size) { index ->
            TodoRow(todo  = todos[index], store)
            HorizontalDivider()
        }
    }
}

@Composable
fun TodoRow(todo: Todo, store: ModelStore) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        onClick = {  }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ChipView(text = todo.id.toString(), color = Color.Green)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 0.dp,
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
                    store.apply { m ->
                        m.todos.first { t -> t.id == todo.id }.completed = !todo.completed
                    }
                }
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCenterAlignedTopAppBar() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Todos",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
            }
        }
    )
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
        //Todos(model)
    }
}
