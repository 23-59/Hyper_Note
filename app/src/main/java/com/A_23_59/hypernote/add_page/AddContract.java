package com.A_23_59.hypernote.add_page;

public interface AddContract {

    interface ViewLayer{

        void showErrorSnackBar();
    }

    interface PresenterLayer{

        void onAttach(AddContract.ViewLayer view);

        void onDetach();

        void onConfirmButtonClicked(String taskDetail, Add_Activity add_activity);


    }
}
