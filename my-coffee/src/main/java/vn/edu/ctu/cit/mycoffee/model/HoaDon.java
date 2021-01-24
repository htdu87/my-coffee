package vn.edu.ctu.cit.mycoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HoaDon implements Parcelable {
    private long IdHoaDon;
    private String soHoaDon;
    private long ngayXuat;
    private String ghiChu;

    public HoaDon() {
    }

    public HoaDon(long idHoaDon, String soHoaDon, long ngayXuat, String ghiChu) {
        IdHoaDon = idHoaDon;
        this.soHoaDon = soHoaDon;
        this.ngayXuat = ngayXuat;
        this.ghiChu = ghiChu;
    }

    public long getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(long idHoaDon) {
        IdHoaDon = idHoaDon;
    }

    public String getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(String soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public long getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(long ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.IdHoaDon);
        dest.writeString(this.soHoaDon);
        dest.writeLong(this.ngayXuat);
        dest.writeString(this.ghiChu);
    }

    protected HoaDon(Parcel in) {
        this.IdHoaDon = in.readLong();
        this.soHoaDon = in.readString();
        this.ngayXuat = in.readLong();
        this.ghiChu = in.readString();
    }

    public static final Parcelable.Creator<HoaDon> CREATOR = new Parcelable.Creator<HoaDon>() {
        @Override
        public HoaDon createFromParcel(Parcel source) {
            return new HoaDon(source);
        }

        @Override
        public HoaDon[] newArray(int size) {
            return new HoaDon[size];
        }
    };
}
