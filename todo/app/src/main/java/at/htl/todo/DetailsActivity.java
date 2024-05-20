package at.htl.todo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;

import javax.inject.Inject;

import at.htl.todo.model.ModelStore;
import at.htl.todo.ui.layout.DetailsView;
import at.htl.todo.util.Config;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsActivity extends ComponentActivity {
    static final String TAG = DetailsActivity.class.getSimpleName();

    @Inject
    DetailsView detailsView;

    @Inject
    ModelStore store;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.load(this);

        Log.i(TAG, "onLoad: DetailsActivity");
        var todoId = getIntent().getStringExtra("ID");
        Log.i(TAG, todoId);
        detailsView.buildContent(this, todoId);
    }
}
