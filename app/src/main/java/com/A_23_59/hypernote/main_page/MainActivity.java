package com.A_23_59.hypernote.main_page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.add_page.Add_Activity;
import com.A_23_59.hypernote.main_page.more_bottom_sheet.MoreBottomSheet;
import com.A_23_59.hypernote.model.AppDataBase;
import com.A_23_59.hypernote.model.Task;
import com.A_23_59.hypernote.model.TaskDao;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.ViewLayer, TaskAdapter.SendMoreDataToMainActivity, TaskAdapter.SelectedItems {

    private MainContract.PresenterLayer presenter;

   static  RecyclerView recyclerView;

   public static TaskAdapter taskAdapter;

    TaskDao taskDao;

   public static ConstraintLayout emptyStateLayout;

   TextView tvSelectedItems;

   TextView tvAppName;

   TextView tv_all;

   CheckBox checkBoxSelectAll;

   ImageButton btnSetChecked;

   ImageButton btnDeleteSelected;

   ImageButton btnResetCheckBoxAll;

   ImageButton btnClose;

   Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MainActivity.setDefaultLocale(this,"fa");

        setContentView(R.layout.activity_main);

        tvSelectedItems = findViewById(R.id.tv_selected_items);

        tvAppName =findViewById(R.id.tv_app_name);

        tv_all=findViewById(R.id.tv_all);

        checkBoxSelectAll =findViewById(R.id.checkBox_all);

        btnSetChecked =findViewById(R.id.btn_check);

        btnDeleteSelected = findViewById(R.id.btn_toolbar_delete);

        btnResetCheckBoxAll = findViewById(R.id.btn_reset);

        btnClose = findViewById(R.id.btn_close);

        btnSelect =findViewById(R.id.btn_select);

        taskDao=AppDataBase.getAppDataBase(this).getTaskDao();

        presenter=new MainPresenter(taskDao);

        presenter.onAttach(this);

        ExtendedFloatingActionButton floatingActionButton = findViewById(R.id.fab_add_task);

        floatingActionButton.setOnClickListener(view -> presenter.onAddButtonClicked());

        btnSelect.setOnClickListener(view -> {

            presenter.prepareSelectionMode();

        });

        btnDeleteSelected.setOnClickListener(view -> presenter.onDeleteSelectionClicked());

        checkBoxSelectAll.setOnCheckedChangeListener((compoundButton, b) -> {

            if (compoundButton.isChecked()) {

                btnSetChecked.setVisibility(View.VISIBLE);

                btnResetCheckBoxAll.setVisibility(View.VISIBLE);

                taskAdapter.selectAll();


            }
            else

            {

                btnResetCheckBoxAll.setVisibility(View.GONE);

                btnSetChecked.setVisibility(View.GONE);

                taskAdapter.deselectAll();
            }
        });


        EditText searchEditText = findViewById(R.id.et_search_task);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()>0)
               presenter.onSearch(charSequence.toString()); //returns the list of items that was equal with searched text

                else
                    presenter.onSearch(); //returns the list of items before searching

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnClose.setOnClickListener(view -> {

            presenter.cancelSelectionMode();

        });

        btnResetCheckBoxAll.setOnClickListener(view -> {


            presenter.onResetSelectionClicked(taskAdapter.getSelectedTasks());

        });

        btnSetChecked.setOnClickListener(view -> {

            presenter.onCheckAllSelectionClicked(taskAdapter.getSelectedTasks());

        });

    }

    @Override
    public void showTaskList(List<Task> tasks) {

        recyclerView = findViewById(R.id.rv_tasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));

        taskAdapter=new TaskAdapter(tasks,this,this);

        recyclerView.setAdapter(taskAdapter);


    }

    @Override
    public void startEditActivity() {

        Intent intent=new Intent(MainActivity.this, Add_Activity.class);

        startActivityForResult(intent,1);
    }


    @Override
    public void addTask(Task task) {

        taskAdapter.addTask_Adapter(task);

        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void deleteTask(Task task) {

        taskAdapter.deleteTask(task);
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void startSelectionMode() {

        if (taskAdapter.taskList.size()==0)
            return;


            tvAppName.setVisibility(View.INVISIBLE);  // 1: set selection mode visibility

            btnSelect.setVisibility(View.INVISIBLE);

            tvSelectedItems.setVisibility(View.VISIBLE);

            btnClose.setVisibility(View.VISIBLE);

            btnDeleteSelected.setVisibility(View.VISIBLE);

            btnResetCheckBoxAll.setVisibility(View.GONE);

            btnSetChecked.setVisibility(View.GONE);

             checkBoxSelectAll.setVisibility(View.VISIBLE);

            tv_all.setVisibility(View.VISIBLE);

            taskAdapter.startSelection(); // 2: start selection mode in adapter




    }


    /**
     * this call back will call from presenter when items successfully removed from database
     */
    @Override
    public void deleteSelectionFromRecyclerView() {
        
        taskAdapter.deleteSelectedItems();

    }

    @Override
    public void resetSelectionFromRecyclerView() {

        taskAdapter.resetAll();

        taskAdapter.resetStatus=false;

        onSelectionIsFinished();

    }

    @Override
    public void checkAllSelectionFromRecyclerView() {

        taskAdapter.checkAll();

        taskAdapter.checkAllStatus=false;

        onSelectionIsFinished();

    }

    @Override
    public void exitSelectionMode() {

        taskAdapter.exitSelection(); // this method will turn selection status to false in adapter

        checkBoxSelectAll.setChecked(false);

        tvAppName.setVisibility(View.VISIBLE);

        btnSelect.setVisibility(View.VISIBLE);

        tvSelectedItems.setVisibility(View.INVISIBLE);

        btnClose.setVisibility(View.INVISIBLE);

        checkBoxSelectAll.setVisibility(View.INVISIBLE);

        btnSetChecked.setVisibility(View.GONE);

        btnResetCheckBoxAll.setVisibility(View.GONE);

        tv_all.setVisibility(View.INVISIBLE);

        btnDeleteSelected.setVisibility(View.INVISIBLE);






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

        emptyStateLayout = findViewById(R.id.empty_state_layout);

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

        Bundle bundle=new Bundle();

        bundle.putParcelable("clickedTask",clickedTask);

        MoreBottomSheet moreBottomSheet=new MoreBottomSheet(this);

        moreBottomSheet.setArguments(bundle);

        moreBottomSheet.show(getSupportFragmentManager(),null);
    }

    @Override
    public void updateCheckInDataBase(Task task) {

     presenter.onChecked(task);
    }

    public static void setDefaultLocale(Activity activity,String languageCode){

        Locale locale=new Locale(languageCode);

        Locale.setDefault(locale);

        Resources resources=activity.getResources();

        Configuration configuration=resources.getConfiguration();

        configuration.setLocale(locale);

        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    @Override
    public void onItemAddedToAdapterSelection(String itemsSize) {

        tvSelectedItems.setText(itemsSize);  // showing to user the count of selected tasks in the toolbar

    }

    @Override
    public void onItemAddedToAdapterSelection(Task task) { // receive items Size and selected task when one item added to selected list

        presenter.updateSelectedTasks(task); // send updated task from adapter to database in order to commit changes into database



    }

    @Override
    public void onItemAddedToAdapterSelection(String itemsSize, Task task) {

        tvSelectedItems.setText(itemsSize);

        presenter.updateSelectedTasks(task);

    }

    @Override
    public void onSelectionIsFinished() {

        taskAdapter.exitSelection(); // this method will turn selection status to false in adapter

        tvAppName.setVisibility(View.VISIBLE);

        checkBoxSelectAll.setChecked(false);

        btnSelect.setVisibility(View.VISIBLE);

        tvSelectedItems.setVisibility(View.INVISIBLE);

        btnClose.setVisibility(View.INVISIBLE);

        btnSetChecked.setVisibility(View.GONE);

        btnResetCheckBoxAll.setVisibility(View.GONE);

        btnDeleteSelected.setVisibility(View.INVISIBLE);

        tv_all.setVisibility(View.INVISIBLE);

        checkBoxSelectAll.setVisibility(View.INVISIBLE);

    }


}