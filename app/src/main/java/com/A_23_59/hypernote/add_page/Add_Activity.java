package com.A_23_59.hypernote.add_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.A_23_59.hypernote.R;
import com.A_23_59.hypernote.main_page.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Add_Activity extends AppCompatActivity implements AddContract.ViewLayer {

   private AddContract.PresenterLayer presenter =new AddPresenter();

    EditText taskDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MainActivity.setDefaultLocale(this,"fa");

        setContentView(R.layout.activity_add);

        presenter.onAttach(this);

        MaterialToolbar toolbar=findViewById(R.id.toobar_edit);

        setSupportActionBar(toolbar);

        ExtendedFloatingActionButton confirm=findViewById(R.id.btn_confirm_add);

        taskDetail=findViewById(R.id.et_task_detail_add);

        confirm.setOnClickListener(view -> presenter.onConfirmButtonClicked(taskDetail.getText().toString(), Add_Activity.this));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        presenter.onDetach();
    }



    @Override
    public void showErrorSnackBar() {

        taskDetail=findViewById(R.id.et_task_detail_add);

        View view=findViewById(R.id.view2);

        Snackbar.make(taskDetail,R.string.error_toast,Snackbar.LENGTH_SHORT)
                .setAnchorView(view)
                .setTextColor(getResources().getColor(android.R.color.white))
                .setBackgroundTint(getResources().getColor(android.R.color.holo_red_dark))
                .show();
    }
}