package com.android.avanatest.products.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.avanatest.products.ProductsApp;
import com.android.avanatest.products.R;
import com.android.avanatest.products.di.component.DaggerFragmentComponent;
import com.android.avanatest.products.di.component.FragmentComponent;
import com.android.avanatest.products.utils.ResourceUtil;

import icepick.Icepick;

public abstract class BaseMvpFragment extends Fragment implements BaseView {

    FragmentComponent mFragmentComponent;
    BaseMvpActivity activity;
    private AlertDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        Icepick.restoreInstanceState(getPresenter(), savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
        Icepick.saveInstanceState(getPresenter(), outState);
    }

    @Override
    public void onAttach(Context context) {
        assert getPresenter() != null;
        super.onAttach(context);
        if (context instanceof BaseMvpActivity) {
            activity = (BaseMvpActivity) context;
        }
        getFragmentComponent().inject(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
        if (getArguments() != null) {
            passArgumentsToPresenter(getArguments());
        }
        getPresenter().onAttachView(this);
    }

    protected abstract void setUp(View view);

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().setViewPaused(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().setViewPaused(false);
    }


    @Override
    public void onDestroyView() {
        getPresenter().onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    protected abstract void passArgumentsToPresenter(Bundle arguments);

    protected abstract BaseMvpPresenter getPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void createComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((ProductsApp) getActivity().getApplication()).getComponent())
                .build();
    }

    public FragmentComponent getFragmentComponent() {
        if (mFragmentComponent == null) {
            createComponent();
        }
        return mFragmentComponent;
    }

    @Override
    public void showProgress() {
        if (!isActive()) {
            return;
        }
        if (activity != null) {
            activity.showProgress();
        } else if (getActivity() != null) {
            hideProgress();
            if (loadingDialog == null) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext(), ResourceUtil.getStyleId(getContext(), "transparentBackgroundDialog"));
                View loadingView = View.inflate(getContext(), R.layout.loading_layout, null);
                builder.setView(loadingView);
                builder.setCancelable(false);
                loadingDialog = builder.create();
            }
            try {
                loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                loadingDialog.show();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void hideProgress() {
        if (!isActive()) {
            return;
        }
        if (activity != null) {
            activity.hideProgress();
        } else if (getActivity() != null) {
            if (loadingDialog != null) {
                try {
                    loadingDialog.dismiss();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isActive() {
        return isAdded() && !isDetached();
    }

    @Override
    public void destroyView() {
        if (activity != null) {
            activity.destroyView();
        }
    }

    @Override
    public void showError(String message, int messageStyle) {
        if (activity != null) {
            activity.showError(message, messageStyle);
        }
    }

    @Override
    public void showError(int message, int messageStyle) {
        if (activity != null) {
            activity.showError(message, messageStyle);
        }
    }
}
