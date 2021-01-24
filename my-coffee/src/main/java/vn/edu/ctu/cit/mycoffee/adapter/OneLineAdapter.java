package vn.edu.ctu.cit.mycoffee.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ctu.cit.mycoffee.model.Ban;

public class OneLineAdapter extends RecyclerView.Adapter<OneLineAdapter.OneLineViewHolder> implements View.OnClickListener {
    private List<Ban> data;
    private OneLineAdapterEvents events;

    public OneLineAdapter(List<Ban> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public OneLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OneLineViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OneLineViewHolder holder, int position) {
        Ban b=data.get(position);
        holder.textView.setText(String.format("%s - %s",b.getMaBan(),b.getTenBan()));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        int p= (int) v.getTag();
        if (events!=null)
            events.onItemSelected(data.get(p));
    }

    public void setData(List<Ban> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnOneLineAdapterEventsListener(OneLineAdapterEvents e) {
        events=e;
    }

    public interface OneLineAdapterEvents {
        void onItemSelected(Ban ban);
    }

    public static class OneLineViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public OneLineViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(android.R.id.text1);
        }
    }
}
