package main.models;

public class BenhAnVIP extends BenhAn {
    private String loaiVIP;
    private String thoiHanVIP;

    public BenhAnVIP(int soThuTu, String maBenhAn, String maBenhNhan, String tenBenhNhan, String ngayNhapVien, String ngayRaVien, String lyDoNhapVien, String loaiVIP, String thoiHanVIP) {
        super(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
        this.loaiVIP = loaiVIP;
        this.thoiHanVIP = thoiHanVIP;
    }

    public String getLoaiVIP() {
        return loaiVIP;
    }

    public void setLoaiVIP(String loaiVIP) {
        this.loaiVIP = loaiVIP;
    }

    public String getThoiHanVIP() {
        return thoiHanVIP;
    }

    public void setThoiHanVIP(String thoiHanVIP) {
        this.thoiHanVIP = thoiHanVIP;
    }

    @Override
    public String getDetails() {
        return super.toString() + "," + loaiVIP + "," + thoiHanVIP;
    }
}
