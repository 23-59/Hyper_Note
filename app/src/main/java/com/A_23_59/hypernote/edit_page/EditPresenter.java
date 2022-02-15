package com.A_23_59.hypernote.edit_page;

import android.app.Activity;
import android.content.Intent;

import com.A_23_59.hypernote.model.Task;

public class EditPresenter implements EditContract.PresenterLayer {

    EditContract.ViewLayer viewLayer;

   public static long id=0;
    @Override
    public void onAttach(EditContract.ViewLayer view) {

        viewLayer=view;

        viewLayer.deleteStatus(false);
    }

    @Override
    public void onDetach() {

        this.viewLayer=null;
    }

    @Override
    public void onConfirmButtonClicked(String taskDetail,Edit_Activity edit_activity) {

        if (taskDetail.length()>0){

            Intent intent=new Intent();

            Task task=new Task();

            task.setTaskTitle(taskDetail);

            intent.putExtra("task",task);

            edit_activity.setResult(Activity.RESULT_OK,intent);

            edit_activity.finish();
        }

        else
            viewLayer.showErrorSnackBar();



    }

    @Override
    public void onDeleteButtonClicked() {

    }

}
