package at.htl.todo.model;

import javax.inject.Inject;
import javax.inject.Singleton;
import at.htl.todo.util.store.Store;

@Singleton
public class ModelStore extends Store<Model>  {

    @Inject
    ModelStore() {
        super(Model.class, new Model());
    }

    public void setTodos(Todo[] todos) {
        apply(model -> model.todos = todos);
    }

    public void selectView(int viewIndex) {
        apply(model -> model.uiState.selectedView = viewIndex);
    }

    public void setTodoDetailsId(Long newTodoDetailsId) {
        apply(model -> model.todoDetailsId = newTodoDetailsId);
    }
}
