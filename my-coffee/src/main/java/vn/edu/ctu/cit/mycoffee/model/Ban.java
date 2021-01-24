package vn.edu.ctu.cit.mycoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Ban implements Parcelable {
    private long idBan;
    private String maBan;
    private String tenBan;
    private int trangThai; //0: chua co khach, 1: co khach
    private long hdHienTai;

    public Ban() {
    }

    public Ban(long idBan, String maBan, String tenBan, int trangThai) {
        this.idBan = idBan;
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.trangThai=trangThai;
    }

    public long getIdBan() {
        return idBan;
    }

    public void setIdBan(long idBan) {
        this.idBan = idBan;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public long getHdHienTai() {
        return hdHienTai;
    }

    public void setHdHienTai(long hdHienTai) {
        this.hdHienTai = hdHienTai;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ban)) {
            return false;
        }
        Ban b= (Ban) obj;
        return b.idBan==idBan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.idBan);
        dest.writeString(this.maBan);
        dest.writeString(this.tenBan);
        dest.writeInt(this.trangThai);
        dest.writeLong(this.hdHienTai);
    }

    protected Ban(Parcel in) {
        this.idBan = in.readLong();
        this.maBan = in.readString();
        this.tenBan = in.readString();
        this.trangThai = in.readInt();
        this.hdHienTai = in.readLong();
    }

    public static final Creator<Ban> CREATOR = new Creator<Ban>() {
        @Override
        public Ban createFromParcel(Parcel source) {
            return new Ban(source);
        }

        @Override
        public Ban[] newArray(int size) {
            return new Ban[size];
        }
    };
}
