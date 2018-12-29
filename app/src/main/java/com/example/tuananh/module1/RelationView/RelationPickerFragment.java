package com.example.tuananh.module1.RelationView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.People.IMainActivity;
import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.FragmentRelationPickerBinding;


public class RelationPickerFragment extends BottomSheetDialogFragment {
    FragmentRelationPickerBinding fragmentRelationPickerBinding;
    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRelationPickerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_relation_picker, container, false);
        fragmentRelationPickerBinding.setId(id);
        fragmentRelationPickerBinding.setIMainActivity((IMainActivity) getContext());
        fragmentRelationPickerBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return fragmentRelationPickerBinding.getRoot();
    }
}
