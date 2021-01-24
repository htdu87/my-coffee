package vn.edu.ctu.cit.mycoffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import vn.edu.ctu.cit.mycoffee.LocalDatabase;
import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.adapter.TwoLineAdapter;
import vn.edu.ctu.cit.mycoffee.model.Mon;

public class MonActivity extends AppCompatActivity implements TwoLineAdapter.TwoLineAdapterEvents {
    private LocalDatabase db;
    private TwoLineAdapter adapter;
    private Mon mon;
    private TextInputEditText txtMa;
    private TextInputEditText txtName;
    private TextInputEditText txtGd;
    private TextInputEditText txtDvt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);

        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db=new LocalDatabase(this);
        adapter=new TwoLineAdapter(db.layDsMon());
        adapter.setOnTwoLineAdapterEventsListener(this);

        txtMa=findViewById(R.id.txt_ma);
        txtName=findViewById(R.id.txt_ten);
        txtGd=findViewById(R.id.txt_dg);
        txtDvt=findViewById(R.id.txt_dvt);
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
    public void onItemSelected(Mon mon) {
        this.mon=mon;
        txtDvt.setText(mon.getDvt());
        txtGd.setText(String.valueOf(mon.getDonGia()));
        txtMa.setText(mon.getMaMon());
        txtName.setText(mon.getTenMon());
    }

    public void onSave(View view) {
        if (mon==null) {
            Mon m=new Mon();
            m.setIdMon(new Date().getTime());
            m.setMaMon(txtMa.getText().toString());
            m.setTenMon(txtName.getText().toString());
            m.setDonGia(Double.parseDouble(txtGd.getText().toString()));
            m.setDvt(txtDvt.getText().toString());

            if (db.addMon(m)) {
                Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                adapter.setData(db.layDsMon());
            }
        } else {
            mon.setMaMon(txtMa.getText().toString());
            mon.setTenMon(txtName.getText().toString());
            mon.setDonGia(Double.parseDouble(txtGd.getText().toString()));
            mon.setDvt(txtDvt.getText().toString());

            if (db.updateMon(mon)) {
                Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                adapter.setData(db.layDsMon());
            }
        }
    }

    public void onCancel(View view) {
        mon=null;
        txtDvt.setText("");
        txtGd.setText("");
        txtMa.setText("");
        txtName.setText("");
    }
}