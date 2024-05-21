package at.htl.todo.model;

public class Model {
    public static class UIState {
        public int selectedView = 0;
    }

    public Todo[] todos = new Todo[0];
    public Long todoDetailsId = null;
    public UIState uiState = new UIState();
}
