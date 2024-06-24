package main;

import main.models.*;
import main.exceptions.*;
import main.ultils.CSVUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class QuanLyBenhAn {
    private static List<BenhAn> benhAnList = new ArrayList<>();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        try {
            benhAnList = CSVUtils.loadRecordsFromFile();
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file CSV: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Thêm mới");
            System.out.println("2. Xóa");
            System.out.println("3. Xem danh sách");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        themMoi(scanner);
                    } catch (IOException e) {
                        System.out.println("Lỗi khi thêm mới: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        xoa(scanner);
                    } catch (IOException e) {
                        System.out.println("Lỗi khi xóa: " + e.getMessage());
                    }
                    break;
                case 3:
                    xemDanhSach();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 4);
    }

    private static void themMoi(Scanner scanner) throws IOException {
        int soThuTu = benhAnList.size() + 1;

        System.out.print("Nhập mã bệnh án (BA-XXX): ");
        String maBenhAn = scanner.nextLine();
        try {
            checkDuplicateMaBenhAn(maBenhAn);
        } catch (DuplicateMedicalRecordException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Nhập mã bệnh nhân (BN-XXX): ");
        String maBenhNhan = scanner.nextLine();

        System.out.print("Nhập tên bệnh nhân: ");
        String tenBenhNhan = scanner.nextLine();

        System.out.print("Nhập ngày nhập viện (dd/MM/yyyy): ");
        String ngayNhapVien = scanner.nextLine();

        System.out.print("Nhập ngày ra viện (dd/MM/yyyy): ");
        String ngayRaVien = scanner.nextLine();

        System.out.print("Nhập lý do nhập viện: ");
        String lyDoNhapVien = scanner.nextLine();

        System.out.print("Bệnh án loại thường hay VIP (T/V): ");
        String loaiBenhAn = scanner.nextLine();

        BenhAn benhAn = null;
        if ("T".equalsIgnoreCase(loaiBenhAn)) {
            System.out.print("Nhập phí nằm viện: ");
            double phiNamVien = scanner.nextDouble();
            scanner.nextLine();
            benhAn = new BenhAnThuong(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, phiNamVien);
        } else if ("V".equalsIgnoreCase(loaiBenhAn)) {
            System.out.print("Nhập loại VIP (VIP I, VIP II, VIP III): ");
            String loaiVIP = scanner.nextLine();
            System.out.print("Nhập thời hạn VIP (dd/MM/yyyy): ");
            String thoiHanVIP = scanner.nextLine();
            benhAn = new BenhAnVIP(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, loaiVIP, thoiHanVIP);
        }

        if (validateBenhAn(benhAn)) {
            benhAnList.add(benhAn);
            CSVUtils.saveRecordsToFile(benhAnList);
            System.out.println("Thêm mới bệnh án thành công.");
        } else {
            System.out.println("Thông tin bệnh án không hợp lệ.");
        }
    }

    private static void checkDuplicateMaBenhAn(String maBenhAn) throws DuplicateMedicalRecordException {
        for (BenhAn benhAn : benhAnList) {
            if (benhAn.getMaBenhAn().equals(maBenhAn)) {
                throw new DuplicateMedicalRecordException("Mã bệnh án đã tồn tại.");
            }
        }
    }

    private static boolean validateBenhAn(BenhAn benhAn) {
        if (benhAn == null) return false;

        if (!BenhAn.isValidDate(benhAn.ngayNhapVien) || !BenhAn.isValidDate(benhAn.ngayRaVien)) {
            return false;
        }

        return true;
    }

    private static void xoa(Scanner scanner) throws IOException {
        System.out.print("Nhập mã bệnh án cần xóa: ");
        String maBenhAn = scanner.nextLine();

        BenhAn benhAnToRemove = null;
        for (BenhAn benhAn : benhAnList) {
            if (benhAn.getMaBenhAn().equals(maBenhAn)) {
                benhAnToRemove = benhAn;
                break;
            }
        }

        if (benhAnToRemove != null) {
            benhAnList.remove(benhAnToRemove);
            CSVUtils.saveRecordsToFile(benhAnList);
            System.out.println("Xóa bệnh án thành công.");
        } else {
            System.out.println("Không tìm thấy bệnh án với mã đã nhập.");
        }
    }

    private static void xemDanhSach() {
        for (BenhAn benhAn : benhAnList) {
            System.out.println(benhAn.getDetails());
        }
    }
}
