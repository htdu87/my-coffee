package vn.edu.ctu.cit.mycoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.Locale;

public class Mon implements Parcelable {
    private long idMon;
    private String maMon;
    private String tenMon;
    private String dvt;
    private double donGia;

    public Mon() {
    }

    public Mon(long idMon, String maMon, String tenMon, String dvt, double donGia) {
        this.idMon = idMon;
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.dvt = dvt;
        this.donGia = donGia;
    }

    public long getIdMon() {
        return idMon;
    }

    public void setIdMon(long idMon) {
        this.idMon = idMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return maMon+" - "+tenMon+" ("+dvt+") - "+NumberFormat.getNumberInstance(Locale.getDefault()).format(donGia);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Mon)) {
            return false;
        }
        Mon m= (Mon) obj;
        return m.idMon==idMon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.idMon);
        dest.writeString(this.maMon);
        dest.writeString(this.tenMon);
        dest.writeString(this.dvt);
        dest.writeDouble(this.donGia);
    }

    protected Mon(Parcel in) {
        this.idMon = in.readLong();
        this.maMon = in.readString();
        this.tenMon = in.readString();
        this.dvt = in.readString();
        this.donGia = in.readDouble();
    }

    public static final Parcelable.Creator<Mon> CREATOR = new Parcelable.Creator<Mon>() {
        @Override
        public Mon createFromParcel(Parcel source) {
            return new Mon(source);
        }

        @Override
        public Mon[] newArray(int size) {
            return new Mon[size];
        }
    };
}
