package vn.edu.ctu.cit.mycoffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.edu.ctu.cit.mycoffee.LocalDatabase;
import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.adapter.CtHoaDonAdapter;
import vn.edu.ctu.cit.mycoffee.model.Ban;
import vn.edu.ctu.cit.mycoffee.model.CtHoaDon;
import vn.edu.ctu.cit.mycoffee.model.HoaDon;
import vn.edu.ctu.cit.mycoffee.model.Mon;

public class GoiMonActivity extends AppCompatActivity implements CtHoaDonAdapter.CtHoaDonAdapterEvents {
    private TextView lblAmount;
    private LocalDatabase db;
    private AlertDialog dialog;
    private Ban ban;
    private ArrayAdapter<Mon> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goi_mon);

        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lblAmount=findViewById(R.id.lbl_amount);

        db=new LocalDatabase(this);
        ban=getIntent().getParcelableExtra("BAN");
        if (ban.getHdHienTai()==0) {
            HoaDon hd=new HoaDon();
            hd.setIdHoaDon(new Date().getTime());
            hd.setSoHoaDon("0000/HD");
            hd.setNgayXuat(new Date().getTime());
            hd.setGhiChu("");

            if (db.addHoaDon(hd)) {
                ban.setHdHienTai(hd.getIdHoaDon());
                ban.setTrangThai(1);
                if(!db.updateBan(hd.getIdHoaDon(),1,ban.getIdBan())) {
                    Log.e("htdu87","Can not update table status");
                } else {
                    Intent i=new Intent("UPDATE_TABLE_STATUS");
                    i.putExtra("BAN",ban);
                    sendBroadcast(i);
                }
            }
        }

        final CtHoaDonAdapter adapter=new CtHoaDonAdapter(this);
        adapter.setOnCtHoaDonAdapterEventsListener(this);
        adapter.setData(db.layChiTietHoaDon(ban.getHdHienTai()));

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        final List<Mon> mons=db.layDsMon();
        adapter1= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mons);
        dialog=new AlertDialog.Builder(this)
                .setTitle("Chọn món")
                .setAdapter(adapter1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Mon m=mons.get(which);
                        boolean found=false;
                        for (CtHoaDon ct:adapter.getData()) {
                            if (ct.getIdMon()==m.getIdMon()) {
                                found=true;
                                break;
                            }
                        }

                        if (found) {
                            new AlertDialog.Builder(GoiMonActivity.this)
                                    .setTitle("Món đã tồn tại trong hóa đơn")
                                    .setMessage("Vui lòng cập nhật số lượng hoặc chọn món khác")
                                    .setPositiveButton("OK",null)
                                    .create()
                                    .show();
                        } else {
                            CtHoaDon ct=new CtHoaDon();
                            ct.setDonGia(m.getDonGia());
                            ct.setIdMon(m.getIdMon());
                            ct.setSoLuong(1);
                            ct.setIdHoaDon(ban.getHdHienTai());
                            ct.setIdChiTiet(new Date().getTime());
                            ct.setIdBan(ban.getIdBan());

                            adapter.add(ct);
                        }
                    }
                })
                .create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ct_hoa_don,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int mid=item.getItemId();
        if (mid==R.id.act_add) {
            adapter1.clear();
            adapter1.addAll(db.layDsMon());
            dialog.show();
            return true;
        } else if (mid==android.R.id.home) {
            onBackPressed();
            return true;
        } else if (mid==R.id.act_list) {
            startActivity(new Intent(this,MonActivity.class));
            return true;
        } else if (mid==R.id.act_tt) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận thanh toán")
                    .setMessage("Bạn chắc muốn thanh toán hóa đơn?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ban.setHdHienTai(0);
                            ban.setTrangThai(0);
                            if(db.updateBan(0,0,ban.getIdBan())) {
                                Intent i=new Intent("UPDATE_TABLE_STATUS");
                                i.putExtra("BAN",ban);
                                sendBroadcast(i);
                                finish();
                            }
                        }
                    })
                    .setNegativeButton("Không",null)
                    .create()
                    .show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onUpdateMount(double amount) {
        lblAmount.setText("Tổng tiền: "+ NumberFormat.getInstance(Locale.getDefault()).format(amount));
    }
}