package com.A_23_59.hypernote.main_page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.edit_page.Edit_Activity;
import com.A_23_59.hypernote.main_page.more_bottom_sheet.MoreBottomSheet;
import com.A_23_59.hypernote.model.AppDataBase;
import com.A_23_59.hypernote.model.Task;
import com.A_23_59.hypernote.model.TaskDao;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.ViewLayer, TaskAdapter.SendMoreDataToMainActivity {

    private MainContract.PresenterLayer presenter;

    RecyclerView recyclerView;

    TaskAdapter taskAdapter;

    TaskDao taskDao;

    LinearLayout emptyStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        taskDao=AppDataBase.getAppDataBase(this).getTaskDao();

        presenter=new MainPresenter(taskDao);

        presenter.onAttach(this);

        ExtendedFloatingActionButton floatingActionButton=findViewById(R.id.fab_add_task);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.onAddButtonClicked();
            }
        });

        findViewById(R.id.btn_delete_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.onDeleteAllButtonClicked();
            }
        });

        EditText searchEditText=findViewById(R.id.et_search_task);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()>0)
               presenter.onSearch(charSequence.toString());

                else
                    presenter.onSearch();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void showTaskList(List<Task> tasks) {

        recyclerView=findViewById(R.id.rv_tasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));

        taskAdapter=new TaskAdapter(tasks,this);

        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void startEditActivity() {

        Intent intent=new Intent(MainActivity.this, Edit_Activity.class);

        startActivityForResult(intent,1);
    }


    @Override
    public void addTask(Task task) {

        taskAdapter.addTask_Adapter(task);
    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void searchTask(List<Task> tasks) {

        taskAdapter.searchTask(tasks);
    }

    @Override
    public void deleteAll() {

        taskAdapter.deleteAll();

    }

    @Override
    public void emptyState(boolean visibility) {

        emptyStateLayout=findViewById(R.id.empty_state_layout);

        if (visibility)
            emptyStateLayout.setVisibility(View.VISIBLE);
        else
            emptyStateLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        presenter.onDetach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        presenter.onResultIsReceived(requestCode,resultCode,data);
    }

  //listeners calling from adapter

    @Override
    public void onMoreClicked(Task clickedTask) {

        MoreBottomSheet bottomSheet=new MoreBottomSheet();

        bottomSheet.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onChecked(Task task) {

     presenter.onChecked(task);
    }
}