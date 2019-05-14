package diploma.storytime.stolbysassistant.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import diploma.storytime.stolbysassistant.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView titleTextView;
    TextView descriptionTextView;
    ImageView imageView;
    ImageView newIconImageView;

    private RecyclerViewListener clickListener;

    ViewHolder(View itemView, RecyclerViewListener listener) {
        super(itemView);
        clickListener = listener;
        itemView.setOnClickListener(this);

        imageView = itemView.findViewById(R.id.image);
        titleTextView = itemView.findViewById(R.id.title);
        descriptionTextView = itemView.findViewById(R.id.description);
        newIconImageView = itemView.findViewById(R.id.icon_image_view);
    }

    @Override
    public void onClick(View view) {
        clickListener.onClick(view, getAdapterPosition());
    }

}