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
import com.pabrou.mppayment.data.model.PayerCost;
import com.pabrou.mppayment.data.model.PaymentData;
import com.pabrou.mppayment.ui.adapter.PayerCostAdapter;
import com.pabrou.mppayment.ui.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pablo on 10/12/17.
 */

public class PaymentInstallmentsActivity extends BaseActivity implements PayerCostAdapter.PayerCostAdapterListener {

    private static final String TAG = PaymentCardIssuerActivity.class.getSimpleName();

    private static final String PAYMENT_DATA_EXTRA = "payment_data_extra";

    @BindView(R.id.content)
    CoordinatorLayout mContentView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.payer_cost_recycler_view)
    RecyclerView mPayerCostRecyclerView;

    @BindView(R.id.loading_view)
    View mLoadingView;

    private PaymentData mPaymentData;

    public static void start(Activity activity, PaymentData paymentData) {
        Intent intent = new Intent(activity, PaymentInstallmentsActivity.class);
        intent.putExtra(PAYMENT_DATA_EXTRA, paymentData);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_installments);

        setupToolbar();

        mPaymentData = getIntent().getParcelableExtra(PAYMENT_DATA_EXTRA);

        mPayerCostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadInstallmentsList();
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

    private void loadInstallmentsList() {
        mLoadingView.setVisibility(View.VISIBLE);

        DataManager.getInstance()
                .getPayerCosts(
                        mPaymentData.getAmount(),
                        mPaymentData.getPaymentMethod(),
                        mPaymentData.getCardIssuer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payerCosts -> {

                    mPayerCostRecyclerView.setAdapter(new PayerCostAdapter(payerCosts, this));

                    if (payerCosts.isEmpty())
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
    public void onPayerCostSelected(PayerCost payerCost) {
        mPaymentData.setPayerCost(payerCost);

        PaymentAmountActivity.start(this, mPaymentData);
    }
}
