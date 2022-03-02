package com.A_23_59.hypernote.edit_page;

import android.view.View;
import com.A_23_59.hypernote.main_page.MainActivity;
import com.A_23_59.hypernote.main_page.MainPresenter;
import com.A_23_59.hypernote.model.Task;

public class EditPresenter  implements EditContract.PresenterLayer{

    EditContract.ViewLayer view;

    @Override
    public void onAttach(EditContract.ViewLayer view) {

        this.view=view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onConfirmButtonClicked(String result, EditActivity editActivity, Task task) {

        if (result.length()>0){

            task.setTaskTitle(result);

            MainPresenter.taskDao.updateTask(task);

            MainActivity.taskAdapter.editTask(task);

            editActivity.finish();
        }
        else {

            view.showSnackBar();
        }
    }

    @Override
    public void onDeleteButtonClicked(Task task,EditActivity editActivity) {

        MainPresenter.taskDao.deleteTask(task);

        MainActivity.taskAdapter.deleteTask(task);

        editActivity.finish();

        if (MainPresenter.taskDao.getTasks().size()==0)
        MainActivity.emptyStateLayout.setVisibility(View.VISIBLE);
    }
}
