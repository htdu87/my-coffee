package vn.edu.ctu.cit.mycoffee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.activity.GoiMonActivity;
import vn.edu.ctu.cit.mycoffee.model.Ban;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.BanViewHolder> implements View.OnClickListener {
    private List<Ban> data;
    private Context context;

    public BanAdapter(List<Ban> data, Context context) {
        this.data=data;
        this.context=context;
    }

    @NonNull
    @Override
    public BanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ban, parent, false);
        return new BanViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull BanViewHolder holder, int position) {
        Ban ban=data.get(position);

        holder.lblTenBan.setText(ban.getMaBan()+" - "+ban.getTenBan());
        if (ban.getTrangThai()==1) {
            holder.lblTrangThai.setText("CÓ KHÁCH");
            holder.lblTrangThai.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bg_red,null));
        } else {
            holder.lblTrangThai.setText("BÀN TRỐNG");
            holder.lblTrangThai.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bg_green,null));
        }

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        Ban b=data.get((Integer) v.getTag());
        Intent i=new Intent(context, GoiMonActivity.class);
        i.putExtra("BAN",b);
        context.startActivity(i);
    }

    public void setData(List<Ban> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void updateTrangThai(Ban ban) {
        int p=data.indexOf(ban);
        if (p>=0) {
            data.set(p,ban);
            notifyDataSetChanged();
        }
    }

    public static class BanViewHolder extends RecyclerView.ViewHolder {
        TextView lblTenBan;
        TextView lblTrangThai;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenBan=itemView.findViewById(R.id.lbl_ten_ban);
            lblTrangThai=itemView.findViewById(R.id.lbl_tthai);
        }
    }
}
