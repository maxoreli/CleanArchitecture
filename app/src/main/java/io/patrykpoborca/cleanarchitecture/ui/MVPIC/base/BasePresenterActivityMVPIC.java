package io.patrykpoborca.cleanarchitecture.ui.MVPIC.base;

import android.os.Bundle;

import io.patrykpoborca.cleanarchitecture.ui.BaseCAActivity;

public abstract class BasePresenterActivityMVPIC<T extends BasePresenterMVPIC> extends BaseCAActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onAttach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onDettach();
    }

    protected abstract T getPresenter();
}
