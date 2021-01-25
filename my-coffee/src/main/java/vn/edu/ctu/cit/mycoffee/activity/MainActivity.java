package vn.edu.ctu.cit.mycoffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vn.edu.ctu.cit.mycoffee.LocalDatabase;
import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.adapter.BanAdapter;
import vn.edu.ctu.cit.mycoffee.model.Ban;

public class MainActivity extends AppCompatActivity {
    private BanAdapter adapter;
    private LocalDatabase db;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("UPDATE_TABLE")) {
                adapter.setData(db.layDsBan());
            } else if (intent.getAction().equalsIgnoreCase("UPDATE_TABLE_STATUS")) {
                Ban ban = intent.getParcelableExtra("BAN");
                adapter.updateTrangThai(ban);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new LocalDatabase(this);
        adapter=new BanAdapter(db.layDsBan(),this);

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.act_list) {
            startActivity(new Intent(this,BanActivity.class));
            return true;
        } else if(item.getItemId()==R.id.act_statistic) {
            startActivity(new Intent(this,StatisticActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter i=new IntentFilter("UPDATE_TABLE_STATUS");
        i.addAction("UPDATE_TABLE");
        registerReceiver(receiver,i);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}