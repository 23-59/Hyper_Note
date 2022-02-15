package com.A_23_59.hypernote.main_page.more_bottom_sheet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.A_23_59.hypernote.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MoreBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.more_layout,container,false);
    }

}
