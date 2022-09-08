package com.library.gallery.utils;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.library.gallery.R;

/**
 * Author CodeBoy722
 *
 * recyclerViewPagerImageIndicator's ViewHolder
 */
public class IndicatorHolder extends RecyclerView.ViewHolder{

    public ImageView image;
    private CardView card;
    View positionController;

    IndicatorHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageIndicator);
        card = itemView.findViewById(R.id.indicatorCard);
        positionController = itemView.findViewById(R.id.activeImage);
    }
}
