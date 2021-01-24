package vn.edu.ctu.cit.mycoffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import vn.edu.ctu.cit.mycoffee.LocalDatabase;
import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.adapter.OneLineAdapter;
import vn.edu.ctu.cit.mycoffee.model.Ban;

public class BanActivity extends AppCompatActivity implements OneLineAdapter.OneLineAdapterEvents {
    private TextInputEditText txtMa;
    private TextInputEditText txtName;
    private LocalDatabase db;
    private OneLineAdapter adapter;
    private Ban ban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban);

        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db=new LocalDatabase(this);
        adapter=new OneLineAdapter(db.layDsBan());
        adapter.setOnOneLineAdapterEventsListener(this);

        txtMa=findViewById(R.id.txt_ma);
        txtName=findViewById(R.id.txt_ten);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int mid=item.getItemId();
        if (mid==android.R.id.home) {
            onBackPressed();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(Ban ban) {
        this.ban=ban;
        txtName.setText(ban.getTenBan());
        txtMa.setText(ban.getMaBan());
    }

    public void onSave(View view) {
        if (TextUtils.isEmpty(txtMa.getText()) || TextUtils.isEmpty(txtName.getText())) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage("Vui lòng nhập đầy đủ mã bàn và tên bàn")
                    .setPositiveButton("OK",null)
                    .create()
                    .show();
        } else {
            if (ban==null) {
                Ban b=new Ban();
                b.setTrangThai(0);
                b.setHdHienTai(0);
                b.setTenBan(txtName.getText().toString());
                b.setMaBan(txtMa.getText().toString());
                b.setIdBan(new Date().getTime());

                if(db.addBan(b)) {
                    Toast.makeText(this,"Thêm mới thành công",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent("UPDATE_TABLE");
                    sendBroadcast(i);
                    adapter.setData(db.layDsBan());
                } else {
                    Log.e("htdu87","Thêm bàn không thành công");
                }
            } else {
                ban.setTenBan(txtName.getText().toString());
                ban.setMaBan(txtMa.getText().toString());
                if (db.updateBan(ban)) {
                    Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent("UPDATE_TABLE");
                    sendBroadcast(i);
                    adapter.setData(db.layDsBan());
                } else {
                    Log.e("htdu87","Update bàn không thành công");
                }
            }
        }
    }

    public void onCancel(View view) {
        ban=null;
        txtMa.setText("");
        txtName.setText("");
    }
}