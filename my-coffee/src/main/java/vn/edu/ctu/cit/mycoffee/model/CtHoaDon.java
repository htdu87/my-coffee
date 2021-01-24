package vn.edu.ctu.cit.mycoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CtHoaDon implements Parcelable {
    private long idChiTiet;
    private long idBan;
    private long idMon;
    private long idHoaDon;
    private int soLuong;
    private double donGia;

    public CtHoaDon() {
    }

    public long getIdChiTiet() {
        return idChiTiet;
    }

    public void setIdChiTiet(long idChiTiet) {
        this.idChiTiet = idChiTiet;
    }

    public long getIdBan() {
        return idBan;
    }

    public void setIdBan(long idBan) {
        this.idBan = idBan;
    }

    public long getIdMon() {
        return idMon;
    }

    public void setIdMon(long idMon) {
        this.idMon = idMon;
    }

    public long getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(long idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.idChiTiet);
        dest.writeLong(this.idBan);
        dest.writeLong(this.idMon);
        dest.writeLong(this.idHoaDon);
        dest.writeInt(this.soLuong);
        dest.writeDouble(this.donGia);
    }

    protected CtHoaDon(Parcel in) {
        this.idChiTiet = in.readLong();
        this.idBan = in.readLong();
        this.idMon = in.readLong();
        this.idHoaDon = in.readLong();
        this.soLuong = in.readInt();
        this.donGia = in.readDouble();
    }

    public static final Creator<CtHoaDon> CREATOR = new Creator<CtHoaDon>() {
        @Override
        public CtHoaDon createFromParcel(Parcel source) {
            return new CtHoaDon(source);
        }

        @Override
        public CtHoaDon[] newArray(int size) {
            return new CtHoaDon[size];
        }
    };
}
