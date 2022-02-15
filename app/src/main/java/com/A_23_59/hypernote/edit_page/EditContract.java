package com.A_23_59.hypernote.edit_page;

import android.view.View;

import com.A_23_59.hypernote.model.Task;

public interface EditContract {

    interface ViewLayer{

        void deleteStatus(boolean visibility);

        void showErrorSnackBar();
    }

    interface PresenterLayer{

        void onAttach(EditContract.ViewLayer view);

        void onDetach();

        void onConfirmButtonClicked(String taskDetail,Edit_Activity edit_activity);

        void onDeleteButtonClicked();
    }
}
