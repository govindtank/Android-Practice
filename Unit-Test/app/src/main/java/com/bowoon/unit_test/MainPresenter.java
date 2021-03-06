package com.bowoon.unit_test;

/**
 * Created by 보운 on 2016-12-18.
 */

public class MainPresenter implements MainContract.UserAction {
    private MainContract.View mMainView;
    private MainModel mMainModel;

    public MainPresenter(MainContract.View view) {
        this.mMainModel = new MainModel();
        this.mMainView = view;
    }

    @Override
    public void sumButtonClick(int val1, int val2) {
        mMainView.setText(String.valueOf(getInteger(val1, val2)));
    }

    public int getInteger(int val1, int val2) {
        return mMainModel.getSumData(val1, val2);
    }
}
