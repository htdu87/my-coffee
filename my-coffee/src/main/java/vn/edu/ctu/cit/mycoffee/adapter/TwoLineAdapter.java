package vn.edu.ctu.cit.mycoffee.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.edu.ctu.cit.mycoffee.model.Mon;

public class TwoLineAdapter extends RecyclerView.Adapter<TwoLineAdapter.TwoLineViewHolder> implements View.OnClickListener {
    private List<Mon> data;
    private TwoLineAdapterEvents events;

    public TwoLineAdapter(List<Mon> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TwoLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TwoLineViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TwoLineViewHolder holder, int position) {
        Mon m=data.get(position);
        holder.textView1.setText(String.format("%s - %s",m.getMaMon(),m.getTenMon()));
        holder.textView2.setText(String.format(Locale.getDefault(), "%,.0f (%s)", m.getDonGia(),m.getDvt()));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (events!=null) {
            events.onItemSelected(data.get((Integer) v.getTag()));
        }
    }

    public void setOnTwoLineAdapterEventsListener(TwoLineAdapterEvents e) {
        events=e;
    }

    public void setData(List<Mon> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public interface TwoLineAdapterEvents {
        void onItemSelected(Mon mon);
    }

    public static class TwoLineViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;

        public TwoLineViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(android.R.id.text1);
            textView2=itemView.findViewById(android.R.id.text2);
        }
    }
}
