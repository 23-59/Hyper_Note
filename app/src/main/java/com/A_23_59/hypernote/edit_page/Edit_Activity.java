package com.A_23_59.hypernote.edit_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.main_page.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class Edit_Activity extends AppCompatActivity implements EditContract.ViewLayer {

   private EditContract.PresenterLayer presenter =new EditPresenter();

    ImageButton delete;

    EditText taskDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        presenter.onAttach(this);

        delete=findViewById(R.id.btn_delete);

        MaterialToolbar toolbar=findViewById(R.id.toobar_edit);

        setSupportActionBar(toolbar);

        ImageButton confirm=findViewById(R.id.btn_confirm);

        taskDetail=findViewById(R.id.et_task_detail);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.onConfirmButtonClicked(taskDetail.getText().toString(),Edit_Activity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        presenter.onDetach();
    }

    @Override
    public void deleteStatus(boolean visibility) {

        delete=findViewById(R.id.btn_delete);

        if (!visibility)
            delete.setVisibility(View.INVISIBLE);
        else
            delete.setVisibility(View.VISIBLE);

        MaterialToolbar toolbar=findViewById(R.id.toobar_edit);

        toolbar.setTitle("Adding Task");
    }

    @Override
    public void showErrorSnackBar() {

        taskDetail=findViewById(R.id.et_task_detail);

        Snackbar.make(taskDetail,"text field cannot be empty",Snackbar.LENGTH_SHORT)
                .setAnchorView(taskDetail)
                .setTextColor(getResources().getColor(android.R.color.white))
                .setBackgroundTint(getResources().getColor(android.R.color.holo_red_dark))
                .show();
    }
}