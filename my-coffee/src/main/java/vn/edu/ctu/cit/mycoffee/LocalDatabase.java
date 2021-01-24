package vn.edu.ctu.cit.mycoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ctu.cit.mycoffee.model.Ban;
import vn.edu.ctu.cit.mycoffee.model.CtHoaDon;
import vn.edu.ctu.cit.mycoffee.model.HoaDon;
import vn.edu.ctu.cit.mycoffee.model.Mon;

public class LocalDatabase extends SQLiteOpenHelper {
    private final static String DB_NAME="ql_quan_cafe";
    private final static int DB_VERSION=1;

    public LocalDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table MON(" +
                "idmon number not null," +
                "mamon text," +
                "tenmon text," +
                "dvt text," +
                "dongia number," +
                "primary key(idmon))";
        db.execSQL(query);

        query="insert into MON values" +
                "(1611453077608,'M001','Cafe đen đá','Ly',13000)," +
                "(1611453158118,'M002','Cafe đen','Ly',12000)," +
                "(1611453190918,'M003','Hủ tiếu bò kho','Tô',37000)";

        db.execSQL(query);

        query="create table BAN(" +
                "idban number not null," +
                "maban text," +
                "tenban text," +
                "trangthai number," +
                "hdhientai number," +
                "primary key(idban))";
        db.execSQL(query);

        query="insert into BAN values" +
                "(1611453077608,'B001','Bàn 1',1,1611456103982)," +
                "(1611453158118,'B002','Bàn 2',0,0)," +
                "(1611453190918,'B003','Bàn 3',0,0)";
        db.execSQL(query);

        query="create table HOA_DON (" +
                "idhd number not null," +
                "sohd text," +
                "ngayxuat number," +
                "ghichu text," +
                "primary key(idhd))";
        db.execSQL(query);

        query="insert into HOA_DON values" +
                "(1611456103982,'H001',1611456103982,'')";
        db.execSQL(query);

        query="create table CT_HOA_DON (" +
                "idct number not null," +
                "idban number," +
                "idmon number," +
                "idhd number," +
                "soluong number," +
                "dongia number," +
                "primary key(idct))";
        db.execSQL(query);

        query="insert into CT_HOA_DON values" +
                "(1611456157926,1611453077608,1611453077608,1611456103982,3,13000)," +
                "(1611456258813,1611453077608,1611453158118,1611456103982,3,12000)," +
                "(1611456269284,1611453077608,1611453190918,1611456103982,3,37000)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Mon> layDsMon() {
        List<Mon> mons=new ArrayList<>();
        SQLiteDatabase reader=getReadableDatabase();
        Cursor cursor=reader.rawQuery("select * from MON",null);
        if (cursor.moveToNext()) {
            do {
                Mon mon=new Mon();
                mon.setIdMon(cursor.getLong(0));
                mon.setMaMon(cursor.getString(1));
                mon.setTenMon(cursor.getString(2));
                mon.setDvt(cursor.getString(3));
                mon.setDonGia(cursor.getDouble(4));

                mons.add(mon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        reader.close();
        return mons;
    }

    public Mon layMon(long id) {
        Mon mon=null;
        SQLiteDatabase reader=getReadableDatabase();
        Cursor cursor=reader.rawQuery("select * from MON where idmon=?",new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            do {
                mon=new Mon();
                mon.setIdMon(cursor.getLong(0));
                mon.setMaMon(cursor.getString(1));
                mon.setTenMon(cursor.getString(2));
                mon.setDvt(cursor.getString(3));
                mon.setDonGia(cursor.getDouble(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        reader.close();
        return mon;
    }

    public List<Ban> layDsBan() {
        List<Ban> bans=new ArrayList<>();
        SQLiteDatabase reader=getReadableDatabase();
        Cursor cursor=reader.rawQuery("select * from BAN",null);
        if (cursor.moveToNext()) {
            do {
                Ban ban=new Ban();
                ban.setIdBan(cursor.getLong(0));
                ban.setMaBan(cursor.getString(1));
                ban.setTenBan(cursor.getString(2));
                ban.setTrangThai(cursor.getInt(3));
                ban.setHdHienTai(cursor.getLong(4));

                bans.add(ban);
            } while (cursor.moveToNext());
        }
        cursor.close();
        reader.close();
        return bans;
    }

    public List<CtHoaDon> layChiTietHoaDon(long idHD) {
        List<CtHoaDon> ctHoaDons=new ArrayList<>();
        SQLiteDatabase reader=getReadableDatabase();
        Cursor cursor=reader.rawQuery("select * from CT_HOA_DON where idhd=?",new String[]{String.valueOf(idHD)});
        if (cursor.moveToNext()) {
            do {
                CtHoaDon ct=new CtHoaDon();
                ct.setIdChiTiet(cursor.getLong(0));
                ct.setIdBan(cursor.getLong(1));
                ct.setIdMon(cursor.getLong(2));
                ct.setIdHoaDon(cursor.getLong(3));
                ct.setSoLuong(cursor.getInt(4));
                ct.setDonGia(cursor.getDouble(5));

                ctHoaDons.add(ct);
            } while (cursor.moveToNext());
        }
        cursor.close();
        reader.close();
        return ctHoaDons;
    }

    public boolean addCtHoaDon(CtHoaDon ct) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("idct",ct.getIdChiTiet());
        cv.put("idban",ct.getIdBan());
        cv.put("idmon",ct.getIdMon());
        cv.put("idhd",ct.getIdHoaDon());
        cv.put("soluong",ct.getSoLuong());
        cv.put("dongia",ct.getDonGia());

        long id=writer.insert("CT_HOA_DON",null,cv);
        writer.close();
        return id!=-1;
    }

    public boolean addHoaDon(HoaDon hd) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("idhd",hd.getIdHoaDon());
        cv.put("sohd",hd.getSoHoaDon());
        cv.put("ngayxuat",hd.getNgayXuat());
        cv.put("ghichu",hd.getGhiChu());

        long id=writer.insert("HOA_DON",null,cv);
        writer.close();
        return id!=-1;
    }

    public boolean addBan(Ban ban) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("idban",ban.getIdBan());
        cv.put("maban",ban.getMaBan());
        cv.put("trangthai",ban.getTrangThai());
        cv.put("hdhientai",ban.getHdHienTai());
        cv.put("tenban",ban.getTenBan());

        long id=writer.insert("BAN",null,cv);
        writer.close();
        return id!=-1;
    }

    public boolean updateBan(Ban ban) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("maban",ban.getMaBan());
        cv.put("tenban",ban.getTenBan());

        long id=writer.update("BAN",cv,"idban=?",new String[]{String.valueOf(ban.getIdBan())});
        writer.close();
        return id!=-1;
    }

    public boolean updateBan(long idHoaDon, int tt, long idBan) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("hdhientai",idHoaDon);
        cv.put("trangthai",tt);
        int num=writer.update("BAN",cv,"idban=?",new String[]{String.valueOf(idBan)});
        writer.close();
        return num>0;
    }

    public boolean updateCtHoaDon(long idCt, int sl) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("soluong",sl);
        int num=writer.update("CT_HOA_DON",cv,"idct=?",new String[]{String.valueOf(idCt)});
        writer.close();
        return num>0;
    }

    public boolean addMon(Mon mon) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("idmon",mon.getIdMon());
        cv.put("mamon",mon.getMaMon());
        cv.put("tenmon",mon.getTenMon());
        cv.put("dvt",mon.getDvt());
        cv.put("dongia",mon.getDonGia());

        long id=writer.insert("MON",null,cv);
        writer.close();
        return id!=-1;
    }

    public boolean updateMon(Mon mon) {
        SQLiteDatabase writer=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("mamon",mon.getMaMon());
        cv.put("tenmon",mon.getTenMon());
        cv.put("dvt",mon.getDvt());
        cv.put("dongia",mon.getDonGia());

        long id=writer.update("MON",cv,"idmon=?",new String[]{String.valueOf(mon.getIdMon())});
        writer.close();
        return id!=-1;
    }

    public void removeCtHoaDon(long idCt) {
        SQLiteDatabase writer=getWritableDatabase();
        writer.delete("CT_HOA_DON","idct=?",new String[]{String.valueOf(idCt)});
        writer.close();
    }
}
