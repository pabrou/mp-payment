package com.pabrou.mppayment.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pabrou.mppayment.R;
import com.pabrou.mppayment.data.model.PaymentMethod;

import java.util.List;

/**
 * Created by pablo on 12/13/17.
 */

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodHolder> {

    private List<PaymentMethod> mPaymentMethods;

    private PaymentMethodAdapterListener mListener;

    public interface PaymentMethodAdapterListener {
        void onPaymentMethodSelected(PaymentMethod paymentMethod);
    }

    public PaymentMethodAdapter(List<PaymentMethod> paymentMethods, PaymentMethodAdapterListener listener) {
        mPaymentMethods = paymentMethods;
        mListener = listener;
    }

    @Override
    public PaymentMethodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_payment_method, parent, false);
        return new PaymentMethodHolder(v);
    }

    @Override
    public void onBindViewHolder(PaymentMethodHolder holder, int position) {
        holder.bind(mPaymentMethods.get(position));
    }

    @Override
    public int getItemCount() {
        return mPaymentMethods.size();
    }

    public class PaymentMethodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView paymentImage;
        TextView paymentName;

        public PaymentMethodHolder(View itemView) {
            super(itemView);

            paymentImage = itemView.findViewById(R.id.payment_method_image);
            paymentName = itemView.findViewById(R.id.payment_method_name);

            itemView.setOnClickListener(this);
        }

        public void bind(PaymentMethod paymentMethod) {

            paymentName.setText(paymentMethod.name);

            Glide.with(paymentImage)
                    .load(paymentMethod.thumbnail)
                    .into(paymentImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            PaymentMethod selectedPaymentMethod = mPaymentMethods.get(position);

            if (mListener != null)
                mListener.onPaymentMethodSelected(selectedPaymentMethod);
        }
    }
}
