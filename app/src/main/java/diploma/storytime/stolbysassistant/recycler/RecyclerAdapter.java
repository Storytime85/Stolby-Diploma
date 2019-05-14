package diploma.storytime.stolbysassistant.recycler;

import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import diploma.storytime.stolbysassistant.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecyclerItem> values = new ArrayList<RecyclerItem>();
    private final Context context;
    private RecyclerViewListener clickListener;

    public RecyclerAdapter(Context context, RecyclerViewListener listener) {
        this.context = context;
        clickListener = listener;
    }

    public void updateData(List<RecyclerItem> dataset) {
        values.clear();
        values.addAll(dataset);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_list_item, parent, false);

        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
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

}
