package com.pabrou.mppayment.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by pablo on 12/10/17.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void start(Fragment fragment, boolean addToBackStack,
                      Integer frameId, String fragmentTag) {

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(frameId, fragment, fragmentTag);
        if (addToBackStack) {
            tx.addToBackStack(fragment.getClass().getSimpleName());
        }
        tx.commit();
    }

    public void startWithTransition(Fragment fragment, boolean addToBackStack,
                                    Integer frameId, String fragmentTag) {

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tx.replace(frameId, fragment, fragmentTag);
        if (addToBackStack) {
            tx.addToBackStack(fragment.getClass().getSimpleName());
        }
        tx.commit();
    }
}
