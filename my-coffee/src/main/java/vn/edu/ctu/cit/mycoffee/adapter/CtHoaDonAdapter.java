package vn.edu.ctu.cit.mycoffee.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.edu.ctu.cit.mycoffee.LocalDatabase;
import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.model.CtHoaDon;
import vn.edu.ctu.cit.mycoffee.model.Mon;

public class CtHoaDonAdapter extends RecyclerView.Adapter<CtHoaDonAdapter.CtHoaDonViewHolder> implements View.OnClickListener {
    private List<CtHoaDon> data;
    private NumberFormat numberFormat;
    private LocalDatabase db;
    private CtHoaDonAdapterEvents events;
    private Context context;

    public CtHoaDonAdapter(Context context) {
        numberFormat=NumberFormat.getNumberInstance(Locale.getDefault());
        db=new LocalDatabase(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CtHoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mon, parent, false);
        return new CtHoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CtHoaDonViewHolder holder, int position) {
        CtHoaDon ct=data.get(position);
        Mon m=db.layMon(ct.getIdMon());
        holder.lblTenMon.setText(m.getMaMon()+" - "+m.getTenMon());
        holder.lblSoLuong.setText(ct.getSoLuong()+" x "+numberFormat.format(ct.getDonGia())+" = "+numberFormat.format(ct.getSoLuong()*ct.getDonGia()));
        holder.btnDel.setOnClickListener(this);
        holder.btnDel.setTag(position);
        holder.btnRem.setOnClickListener(this);
        holder.btnRem.setTag(position);
        holder.btnAdd.setOnClickListener(this);
        holder.btnAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        int vid=v.getId();
        final int pos= (int) v.getTag();
        final CtHoaDon ct=data.get(pos);
        switch (vid) {
            case R.id.btn_add:
                ct.setSoLuong(ct.getSoLuong()+1);
                db.updateCtHoaDon(ct.getIdChiTiet(),ct.getSoLuong());
                break;
            case R.id.btn_del:
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn chắc muốn xóa món khỏi hóa đơn?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(pos);
                                notifyDataSetChanged();
                                updateAmount();
                                db.removeCtHoaDon(ct.getIdChiTiet());
                            }
                        })
                        .setNegativeButton("Không",null)
                        .create()
                        .show();
                break;
            case R.id.btn_rem:
                if (data.get(pos).getSoLuong()>1) {
                    ct.setSoLuong(ct.getSoLuong() - 1);
                    db.updateCtHoaDon(ct.getIdChiTiet(),ct.getSoLuong());
                }
                break;
        }
        notifyDataSetChanged();
        updateAmount();
    }

    public void setData(List<CtHoaDon> data) {
        this.data=data;
        notifyDataSetChanged();
        updateAmount();
    }

    public void setOnCtHoaDonAdapterEventsListener(CtHoaDonAdapterEvents e) {
        events=e;
    }

    public boolean add(CtHoaDon ct) {
        boolean suc=db.addCtHoaDon(ct);
        if(suc) {
            data.add(ct);
            notifyDataSetChanged();
            updateAmount();
        }
        return suc;
    }

    public List<CtHoaDon> getData() {
        return data;
    }

    private void updateAmount() {
        double amount=0;
        for (CtHoaDon ct:data) {
            amount+=ct.getDonGia()*ct.getSoLuong();
        }

        if (events!=null)
            events.onUpdateMount(amount);
    }


    public interface CtHoaDonAdapterEvents {
        void onUpdateMount(double amount);
    }

    public static class CtHoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView lblTenMon;
        TextView lblSoLuong;
        ImageButton btnAdd;
        ImageButton btnRem;
        ImageButton btnDel;

        public CtHoaDonViewHolder(@NonNull View itemView) {
            super(itemView);

            lblTenMon=itemView.findViewById(R.id.lbl_ten_mon);
            lblSoLuong=itemView.findViewById(R.id.lbl_so_luong);
            btnAdd=itemView.findViewById(R.id.btn_add);
            btnRem=itemView.findViewById(R.id.btn_rem);
            btnDel=itemView.findViewById(R.id.btn_del);
        }
    }
}
