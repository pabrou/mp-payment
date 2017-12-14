package com.pabrou.mppayment.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pabrou.mppayment.R;
import com.pabrou.mppayment.data.model.PaymentData;

/**
 * Created by pablo on 14/12/17.
 */

public class PaymentDataDialogFragment extends DialogFragment {

    public static final String TAG = PaymentDataDialogFragment.class.getSimpleName();

    private static final String PAYMENT_DATA_EXTRA = "payment_data_extra";

    public static PaymentDataDialogFragment newInstance(PaymentData paymentData) {
        PaymentDataDialogFragment dialog = new PaymentDataDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(PAYMENT_DATA_EXTRA, paymentData);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        PaymentData paymentData = getArguments().getParcelable(PAYMENT_DATA_EXTRA);

        View contentView = getContentView(paymentData);

        builder.setView(contentView);
        builder.setNegativeButton(R.string.close, (dialog, id) ->
                PaymentDataDialogFragment.this.getDialog().cancel());

        return builder.create();
    }

    @NonNull
    private View getContentView(PaymentData paymentData) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View contentView = inflater.inflate(R.layout.dialog_payment_data, null);

        TextView paymentAmount = contentView.findViewById(R.id.payment_amount);

        TextView cardIssuerName = contentView.findViewById(R.id.card_issuer_name);
        ImageView cardIssuerImage = contentView.findViewById(R.id.card_issuer_image);

        TextView paymentMethodName = contentView.findViewById(R.id.payment_method_name);
        ImageView paymentMethodImage = contentView.findViewById(R.id.payment_method_image);

        TextView installments = contentView.findViewById(R.id.installments);
        TextView installmentAmount = contentView.findViewById(R.id.installment_amount);
        TextView totalAmount = contentView.findViewById(R.id.total_amount);

        paymentAmount.setText(getContext().getString(R.string.payment_amount_message,
                String.valueOf(paymentData.getAmount())));

        cardIssuerName.setText(paymentData.getCardIssuer().getName());
        Glide.with(cardIssuerImage)
                .load(paymentData.getCardIssuer().getThumbnail())
                .into(cardIssuerImage);

        paymentMethodName.setText(paymentData.getPaymentMethod().getName());
        Glide.with(paymentMethodImage)
                .load(paymentData.getPaymentMethod().getThumbnail())
                .into(paymentMethodImage);

        installments.setText(getContext().getString(R.string.installments_count_message,
                String.valueOf(paymentData.getPayerCost().installments)));

        installmentAmount.setText(getContext().getString(R.string.installment_amount_message,
                String.valueOf(paymentData.getPayerCost().installmentAmount)));

        totalAmount.setText(getContext().getString(R.string.total_amount_message,
                String.valueOf(paymentData.getPayerCost().totalAmount)));

        return contentView;
    }

}
