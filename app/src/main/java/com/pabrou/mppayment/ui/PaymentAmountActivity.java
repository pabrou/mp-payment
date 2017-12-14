package com.pabrou.mppayment.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pabrou.mppayment.R;
import com.pabrou.mppayment.data.model.PaymentData;
import com.pabrou.mppayment.ui.base.BaseActivity;
import com.pabrou.mppayment.ui.dialog.PaymentDataDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentAmountActivity extends BaseActivity {

    private static final String TAG = PaymentAmountActivity.class.getSimpleName();

    private static final String PAYMENT_DATA_EXTRA = "payment_data_extra";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.next_button)
    AppCompatButton mNextButton;

    @BindView(R.id.amount_payment_edit)
    EditText mAmountPaymentEdit;

    public static void start(Activity activity, PaymentData paymentData) {
        Intent intent = new Intent(activity, PaymentAmountActivity.class);
        intent.putExtra(PAYMENT_DATA_EXTRA, paymentData);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_amount);

        setupToolbar();

        // If we have all the data we need for the payment, then show it
        showPaymentDataIfAvailable();

        mNextButton.setEnabled(false);
        mAmountPaymentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    float value = Float.parseFloat(charSequence.toString());
                    mNextButton.setEnabled(value > 0);
                } catch (NumberFormatException e) {
                    mNextButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void showPaymentDataIfAvailable() {
        PaymentData paymentData = getIntent().getParcelableExtra(PAYMENT_DATA_EXTRA);
        if (paymentData != null) {

            DialogFragment dialog = PaymentDataDialogFragment.newInstance(paymentData);
            dialog.show(getSupportFragmentManager(), PaymentDataDialogFragment.TAG);

            getIntent().removeExtra(PAYMENT_DATA_EXTRA);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.ic_logo_mercado_pago);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @OnClick(R.id.next_button)
    void onNextClicked() {

        // Create a PaymentData which we will pass along the rest of the screens
        // and collect all the necessary data for the payment
        PaymentData paymentData = createPaymentData();

        PaymentMethodActivity.start(this, paymentData);
    }

    private PaymentData createPaymentData() {

        try {
            float value = Float.parseFloat(mAmountPaymentEdit.getText().toString());
            return new PaymentData(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
