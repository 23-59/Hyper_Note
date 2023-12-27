package com.A_23_59.hypernote.add_page;

import android.app.Activity;
import android.content.Intent;

import com.A_23_59.hypernote.model.Task;

public class AddPresenter implements AddContract.PresenterLayer {

    AddContract.ViewLayer viewLayer;

    public static long id = 0;

    @Override
    public void onAttach(AddContract.ViewLayer view) {

        viewLayer = view;
    }

    @Override
    public void onDetach() {

        this.viewLayer = null;
    }

    @Override
    public void onConfirmButtonClicked(String taskDetail, Add_Activity add_activity) {

        if (taskDetail.length() > 0) {

            Intent intent = new Intent();

            Task task = new Task();

            task.setTaskTitle(taskDetail);

            intent.putExtra("task", task);

            add_activity.setResult(Activity.RESULT_OK, intent);

            add_activity.finish();
        } else
            viewLayer.showErrorSnackBar();


    }


}
