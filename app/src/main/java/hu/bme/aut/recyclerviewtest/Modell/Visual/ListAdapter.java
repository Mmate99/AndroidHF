package hu.bme.aut.recyclerviewtest.Modell.Visual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.recyclerviewtest.Modell.ListItem;
import hu.bme.aut.recyclerviewtest.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private List<ListItem> itemList;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        clickListener = listener;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public ImageView iView;
        public TextView tView1;
        public TextView tView2;

        public ListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            iView = itemView.findViewById(R.id.iv);
            tView1 = itemView.findViewById(R.id.tv1);
            tView2 = itemView.findViewById(R.id.tv2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }

    public ListAdapter(List<ListItem> il){
        itemList = new ArrayList<>();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder lvh = new ListViewHolder(v, clickListener);
        return lvh;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListItem currentItem = itemList.get(position);

        holder.iView.setImageResource(currentItem.imageResource);
        holder.tView1.setText(currentItem.lNev);
        holder.tView2.setText(currentItem.lVaros);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<ListItem> list){
        itemList = list;
        notifyDataSetChanged();
    }

    public void update(List<ListItem> listItems) {
        itemList.clear();
        itemList.addAll(listItems);
        notifyDataSetChanged();
    }

    public void addItem(ListItem li){
        itemList.add(li);
        notifyDataSetChanged();
    }
}
