package com.pabrou.mppayment.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pabrou.mppayment.R;
import com.pabrou.mppayment.data.model.CardIssuer;

import java.util.List;

/**
 * Created by pablo on 12/13/17.
 */

public class CardIssuerAdapter extends RecyclerView.Adapter<CardIssuerAdapter.CardIssuerHolder> {

    private List<CardIssuer> mCardIssuers;

    private CardIssuerAdapterListener mListener;

    public interface CardIssuerAdapterListener {
        void onCardIssuerSelected(CardIssuer cardIssuer);
    }

    public CardIssuerAdapter(List<CardIssuer> cardIssuers, CardIssuerAdapterListener listener) {
        mCardIssuers = cardIssuers;
        mListener = listener;
    }

    @Override
    public CardIssuerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_issuer, parent, false);
        return new CardIssuerHolder(v);
    }

    @Override
    public void onBindViewHolder(CardIssuerHolder holder, int position) {
        holder.bind(mCardIssuers.get(position));
    }

    @Override
    public int getItemCount() {
        return mCardIssuers.size();
    }

    public class CardIssuerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cardIssuerImage;
        TextView cardIssuerName;

        public CardIssuerHolder(View itemView) {
            super(itemView);

            cardIssuerImage = itemView.findViewById(R.id.card_issuer_image);
            cardIssuerName = itemView.findViewById(R.id.card_issuer_name);

            itemView.setOnClickListener(this);
        }

        public void bind(CardIssuer cardIssuer) {

            cardIssuerName.setText(cardIssuer.name);

            Glide.with(cardIssuerImage)
                    .load(cardIssuer.thumbnail)
                    .into(cardIssuerImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            CardIssuer selectedCardIssuer = mCardIssuers.get(position);

            if (mListener != null)
                mListener.onCardIssuerSelected(selectedCardIssuer);
        }
    }
}
