package com.A_23_59.hypernote.main_page;

import android.content.Intent;

import com.A_23_59.hypernote.edit_page.EditPresenter;
import com.A_23_59.hypernote.model.Task;
import com.A_23_59.hypernote.model.TaskDao;

public class MainPresenter implements MainContract.PresenterLayer{

    MainContract.ViewLayer view;

  public static TaskDao taskDao;

    MainPresenter(TaskDao taskDao){

     MainPresenter.taskDao =taskDao;
    }

    @Override
    public void onAttach(MainContract.ViewLayer view) {



        this.view=view;

        if (this.view!=null) {

            this.view.showTaskList(taskDao.getTasks());

            if (taskDao.getTasks().size()==0)
                this.view.emptyState(true);

        }
    }

    @Override
    public void onDetach() {

        this.view=null;
    }

    @Override
    public void onAddButtonClicked() {

    view.startEditActivity();


    }

    @Override
    public void onMoreButtonClicked() {

    }

    @Override
    public void onSearch(String query) {

        view.searchTask(taskDao.searchResult(query));
    }

    @Override
    public void onSearch() {

    view.searchTask(taskDao.getTasks());
    }

    @Override
    public void onResultIsReceived(int requestCode, int resultCode, Intent intent) {

        if (requestCode==1 && resultCode==MainActivity.RESULT_OK && intent!=null){

            view.emptyState(false);

          Task task= intent.getParcelableExtra("task");

           long result= taskDao.addTask(task);

           task.setId(result);

            view.addTask(task);

        }


    }

    @Override
    public void onDeleteAllButtonClicked() {

        taskDao.clearAll();

        view.deleteAll();

        view.emptyState(true);

    }

    @Override
    public void onChecked(Task task) {

        taskDao.updateTask(task);

    }
}
