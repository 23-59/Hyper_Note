package com.A_23_59.hypernote.edit_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.main_page.MainActivity;
import com.A_23_59.hypernote.main_page.more_bottom_sheet.MoreBottomSheet;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class EditActivity extends AppCompatActivity implements EditContract.ViewLayer {

    EditContract.PresenterLayer presenterLayer=new EditPresenter();

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        presenterLayer.onAttach(this);

        EditText taskDetailEdit=findViewById(R.id.et_task_detail_edit);

        taskDetailEdit.setText(MoreBottomSheet.task.getTaskTitle());

        ImageButton delete=findViewById(R.id.btn_delete_edit);

        ExtendedFloatingActionButton confirmEdit=findViewById(R.id.btn_confirm_edit);

        confirmEdit.setOnClickListener(view -> presenterLayer.onConfirmButtonClicked(taskDetailEdit.getText().toString(),EditActivity.this, MoreBottomSheet.task));

        delete.setOnClickListener(view -> presenterLayer.onDeleteButtonClicked(MoreBottomSheet.task,EditActivity.this));
    }

    @Override
    public void showSnackBar() {

        view=findViewById(R.id.view);

        Snackbar.make(view,R.string.error_toast,Snackbar.LENGTH_SHORT)
                .setAnchorView(view)
                .setBackgroundTint(getResources().getColor(android.R.color.holo_red_dark))
                .setTextColor(getResources().getColor(android.R.color.white))
                .show();
    }
}