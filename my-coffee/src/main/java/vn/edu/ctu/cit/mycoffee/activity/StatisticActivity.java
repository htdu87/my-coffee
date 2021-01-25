package vn.edu.ctu.cit.mycoffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.ctu.cit.mycoffee.LocalDatabase;
import vn.edu.ctu.cit.mycoffee.R;
import vn.edu.ctu.cit.mycoffee.model.CtHoaDon;
import vn.edu.ctu.cit.mycoffee.model.CtHoaDonFull;

public class StatisticActivity extends AppCompatActivity implements OnSelectDateListener {
    private PieChart pieChartYear;
    private BarChart barChartMonth;
    private LocalDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        if (getSupportActionBar()!=null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db=new LocalDatabase(this);

        barChartMonth=findViewById(R.id.bar_chart_month);
        barChartMonth.getDescription().setEnabled(false);
        barChartMonth.getLegend().setEnabled(false);
        barChartMonth.setFitBars(true); // make the x-axis fit exactly all bars
        barChartMonth.animateY(2000, Easing.EaseInSine);

        pieChartYear=findViewById(R.id.bar_chart_year);
        pieChartYear.getDescription().setEnabled(false);
        pieChartYear.animateY(2000, Easing.EaseInSine);
        pieChartYear.setDrawEntryLabels(false);

        Calendar from=Calendar.getInstance();
        from.set(Calendar.DAY_OF_MONTH,1);
        from.set(Calendar.HOUR_OF_DAY,0);
        from.set(Calendar.MINUTE,0);
        from.set(Calendar.SECOND,0);

        Calendar to=Calendar.getInstance();
        to.set(Calendar.DAY_OF_MONTH,to.getActualMaximum(Calendar.DAY_OF_MONTH));
        to.set(Calendar.HOUR_OF_DAY,23);
        to.set(Calendar.MINUTE,59);
        to.set(Calendar.SECOND,59);

        showStatistic(from.getTime(),to.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistic,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.act_choice_date) {
            DatePickerBuilder builder = new DatePickerBuilder(this,this)
                    .date(Calendar.getInstance())
                    .pickerType(CalendarView.RANGE_PICKER)
                    .date(Calendar.getInstance())
                    .headerColor(R.color.colorPrimary)
                    .selectionColor(R.color.colorAccent)
                    .todayLabelColor(R.color.colorAccent);

            DatePicker picker=builder.build();
            picker.show();
            return true;
        } else if (item.getItemId()==android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelect(List<Calendar> calendar) {
        Calendar from=Calendar.getInstance();
        Calendar to=Calendar.getInstance();

        if (calendar.size()==1){
            from.setTime(calendar.get(0).getTime());
            from.set(Calendar.HOUR_OF_DAY,0);
            from.set(Calendar.MINUTE,0);
            from.set(Calendar.SECOND,0);

            to.setTime(calendar.get(0).getTime());
            to.set(Calendar.HOUR_OF_DAY,23);
            to.set(Calendar.MINUTE,59);
            to.set(Calendar.SECOND,59);
        } else {
            from.setTime(calendar.get(0).getTime());
            from.set(Calendar.HOUR_OF_DAY,0);
            from.set(Calendar.MINUTE,0);
            from.set(Calendar.SECOND,0);

            to.setTime(calendar.get(calendar.size()-1).getTime());
            to.set(Calendar.HOUR_OF_DAY,23);
            to.set(Calendar.MINUTE,59);
            to.set(Calendar.SECOND,59);
        }

        showStatistic(from.getTime(),to.getTime());
    }

    private void showStatistic(Date from, Date to) {
        List<CtHoaDonFull> data=db.thongKe(from.getTime(),to.getTime());
        List<BarEntry> barData=new ArrayList<>();
        List<PieEntry> pieData=new ArrayList<>();
        List<String> labels=new ArrayList<>();
        int xIndex=0;

        for (CtHoaDonFull ct:data) {
            // Bar
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(ct.getNgayBan());

            int barX=c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH);
            float barY=(float) (ct.getSoLuong()*ct.getDonGia());
            BarEntry barEntry=new BarEntry(xIndex,barY,barX);
            int pos=-1;

            for (int i=0;i<barData.size();i++) {
                if ((int)barData.get(i).getData()==barX) {
                    pos=i;
                    break;
                }
            }

            if (pos>=0) {
                barData.get(pos).setY(barData.get(pos).getY()+barY);
            } else {
                xIndex++;
                barData.add(barEntry);
                labels.add(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1));
            }

            // Pie
            float pieVal=(float) (ct.getSoLuong()*ct.getDonGia());
            PieEntry pieEntry=new PieEntry(pieVal,ct.getTenMon());

            pos=-1;
            for (int i=0;i<pieData.size();i++) {
                if (pieData.get(i).getLabel().equalsIgnoreCase(ct.getTenMon())) {
                    pos=i;
                    break;
                }
            }

            if (pos>=0) {
                pieData.get(pos).setY(pieData.get(pos).getValue()+pieVal);
            } else {
                pieData.add(pieEntry);
            }
        }

        XAxis xAxis=barChartMonth.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        BarDataSet monthDataset = new BarDataSet(barData, "Bar data");
        monthDataset.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData _barData = new BarData(monthDataset);
        _barData.setBarWidth(0.9f); // set custom bar width

        barChartMonth.setData(_barData);
        barChartMonth.invalidate(); // refresh

        PieDataSet yearDataset = new PieDataSet(pieData, "|Theo món");
        yearDataset.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData _pieData = new PieData(yearDataset);
        pieChartYear.setData(_pieData);
        pieChartYear.invalidate(); // refresh
    }
}