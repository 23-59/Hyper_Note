package com.A_23_59.hypernote.main_page.more_bottom_sheet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.edit_page.EditActivity;
import com.A_23_59.hypernote.main_page.MainActivity;
import com.A_23_59.hypernote.main_page.MainContract;
import com.A_23_59.hypernote.main_page.MainPresenter;
import com.A_23_59.hypernote.main_page.TaskAdapter;
import com.A_23_59.hypernote.model.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MoreBottomSheet extends BottomSheetDialogFragment {

   public static Task task;

    MainContract.ViewLayer viewLayer;

    public MoreBottomSheet(MainContract.ViewLayer viewLayer){

        this.viewLayer = viewLayer;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         task=getArguments().getParcelable("clickedTask");

        return inflater.inflate(R.layout.more_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ConstraintLayout deleteLayout=view.findViewById(R.id.delete);

        ConstraintLayout editLayout=view.findViewById(R.id.edit);

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               MainPresenter.taskDao.deleteTask(task);

               MainActivity.taskAdapter.deleteTask(task);

               if (MainPresenter.taskDao.getTasks().size()==0)
                   viewLayer.emptyState(true);

               dismiss();

            }
        });

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(),EditActivity.class);

                startActivity(intent);

                dismiss();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

}
