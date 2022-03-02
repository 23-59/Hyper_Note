package com.A_23_59.hypernote.edit_page;

import com.A_23_59.hypernote.model.Task;

public interface EditContract {

     interface ViewLayer{

         void showSnackBar();
    }

     interface PresenterLayer{

         void onAttach(EditContract.ViewLayer view);

         void onDetach();

         void onConfirmButtonClicked(String result, EditActivity editActivity, Task task);

         void onDeleteButtonClicked(Task task,EditActivity editActivity);
    }
}
