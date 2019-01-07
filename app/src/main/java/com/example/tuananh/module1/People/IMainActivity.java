package com.example.tuananh.module1.People;


import com.example.tuananh.module1.Model.Model;

public interface IMainActivity {
    void onAddListener();
    void onEditListener(int id);

    void onAddNewRelative(int id);
    void onAddExistRelative(int id);
    void onAddRelativeListener(int id);
    void onDetailInfoShow(int id);
    void onShowRelation(int id);

    void onPeoplePicker(Model model);

    void notifySelectedMenuItem(int position);

    void getFragPeoplePicker();
}
