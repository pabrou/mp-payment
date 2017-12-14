package com.pabrou.mppayment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pabrou.mppayment.R;
import com.pabrou.mppayment.data.model.PayerCost;

import java.util.List;

/**
 * Created by pablo on 12/13/17.
 */

public class PayerCostAdapter extends RecyclerView.Adapter<PayerCostAdapter.PayerCostHolder> {

    private List<PayerCost> mPayerCosts;

    private PayerCostAdapterListener mListener;

    public interface PayerCostAdapterListener {
        void onPayerCostSelected(PayerCost payerCost);
    }

    public PayerCostAdapter(List<PayerCost> payerCosts, PayerCostAdapterListener listener) {
        mPayerCosts = payerCosts;
        mListener = listener;
    }

    @Override
    public PayerCostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_payer_cost, parent, false);
        return new PayerCostHolder(v);
    }

    @Override
    public void onBindViewHolder(PayerCostHolder holder, int position) {
        holder.bind(mPayerCosts.get(position));
    }

    @Override
    public int getItemCount() {
        return mPayerCosts.size();
    }

    public class PayerCostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recommendedMessage;
        TextView installments;
        TextView installmentAmount;
        TextView totalAmount;


        public PayerCostHolder(View itemView) {
            super(itemView);

            recommendedMessage = itemView.findViewById(R.id.recommended_message);
            installments = itemView.findViewById(R.id.installments);
            installmentAmount = itemView.findViewById(R.id.installment_amount);
            totalAmount = itemView.findViewById(R.id.total_amount);

            itemView.setOnClickListener(this);
        }

        public void bind(PayerCost payerCost) {

            Context context = itemView.getContext();

            recommendedMessage.setText(payerCost.getRecommendedMessage());
            installments.setText(context.getString(R.string.installments_count_message, String.valueOf(payerCost.installments)));
            installmentAmount.setText(context.getString(R.string.installment_amount_message, String.valueOf(payerCost.installmentAmount)));
            totalAmount.setText(context.getString(R.string.total_amount_message, String.valueOf(payerCost.totalAmount)));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            PayerCost selectedPayerCost = mPayerCosts.get(position);

            if (mListener != null)
                mListener.onPayerCostSelected(selectedPayerCost);
        }
    }
}
