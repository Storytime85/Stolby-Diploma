package diploma.storytime.stolbysassistant.recycler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import diploma.storytime.stolbysassistant.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<RecyclerItem> values = new ArrayList<RecyclerItem>();
    private final Context context;
    private static ClickListener clickListener;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setDataSet(ArrayList<RecyclerItem> items){
        values.addAll(items);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int i) {
        RecyclerItem detailItem = values.get(i);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(context, detailItem.getImageUrl()));
        viewHolder.titleTextView.setText(context.getString(detailItem.getTitle()));
        viewHolder.descriptionTextView.setText(context.getString(detailItem.getDescription()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;
        ImageView newIconImageView;

        ViewHolder(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            newIconImageView = itemView.findViewById(R.id.icon_image_view);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
}
