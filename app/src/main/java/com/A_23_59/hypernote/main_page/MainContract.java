package com.A_23_59.hypernote.main_page;

import android.content.Intent;
import android.view.View;

import com.A_23_59.hypernote.model.Task;

import java.util.List;

public interface MainContract {

    interface ViewLayer{

        /**
         * this method is for notify the View that the Task's list has been delivered from ModelLayer into Presenter
         * and it's ready to display in specified view
         * @param tasks Task's list received from database
         */

        void showTaskList(List<Task> tasks);

        void startEditActivity();

        void addTask(Task task);

        void deleteTask(Task task);

        void searchTask(List<Task> tasks);

        void deleteAll();

        void emptyState(boolean visibility);
    }

    interface PresenterLayer{

        /**
         * this method is for notify the Presenter that the specified view has been created or attached
         * @param view the target view that want to display in main page
         */
        void onAttach(MainContract.ViewLayer view);

        /**
         * this method is for notify the Presenter that the specified view has been destroyed or detached
         */
        void onDetach();

        void onAddButtonClicked();

        void onMoreButtonClicked();

        void onSearch(String query);

        void onSearch();

        void onResultIsReceived(int requestCode, int resultCode, Intent intent);

        void onDeleteAllButtonClicked();

        void onChecked(Task task);

    }
}
