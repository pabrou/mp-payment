package com.pabrou.mppayment.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;

import com.pabrou.mppayment.R;
import com.pabrou.mppayment.data.DataManager;
import com.pabrou.mppayment.data.model.CardIssuer;
import com.pabrou.mppayment.data.model.PaymentData;
import com.pabrou.mppayment.ui.adapter.CardIssuerAdapter;
import com.pabrou.mppayment.ui.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pablo on 10/12/17.
 */

public class PaymentCardIssuerActivity extends BaseActivity implements
        CardIssuerAdapter.CardIssuerAdapterListener {

    private static final String TAG = PaymentCardIssuerActivity.class.getSimpleName();

    private static final String PAYMENT_DATA_EXTRA = "payment_data_extra";

    @BindView(R.id.content)
    CoordinatorLayout mContentView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.card_issuer_recycler_view)
    RecyclerView mCardIssuerRecyclerView;

    @BindView(R.id.loading_view)
    View mLoadingView;

    private PaymentData mPaymentData;

    public static void start(Activity activity, PaymentData paymentData) {
        Intent intent = new Intent(activity, PaymentCardIssuerActivity.class);
        intent.putExtra(PAYMENT_DATA_EXTRA, paymentData);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card_issuer);

        setupToolbar();

        mPaymentData = getIntent().getParcelableExtra(PAYMENT_DATA_EXTRA);

        mCardIssuerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCardIssuerList();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadCardIssuerList() {
        mLoadingView.setVisibility(View.VISIBLE);

        DataManager.getInstance()
                .getCardIssuers(mPaymentData.getPaymentMethod())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardIssuers -> {

                    mCardIssuerRecyclerView.setAdapter(new CardIssuerAdapter(cardIssuers, this));

                    if (cardIssuers.isEmpty())
                        Snackbar.make(mContentView, R.string.no_option_available, Snackbar.LENGTH_INDEFINITE).show();

                    TransitionManager.beginDelayedTransition(mContentView);
                    mLoadingView.setVisibility(View.GONE);

                }, throwable -> {
                    throwable.printStackTrace();

                    TransitionManager.beginDelayedTransition(mContentView);
                    mLoadingView.setVisibility(View.GONE);

                    Snackbar.make(mContentView, R.string.error_loading_data, Snackbar.LENGTH_INDEFINITE).show();
                });
    }

    @Override
    public void onCardIssuerSelected(CardIssuer cardIssuer) {
        mPaymentData.setCardIssuer(cardIssuer);
        PaymentInstallmentsActivity.start(this, mPaymentData);
    }
}
