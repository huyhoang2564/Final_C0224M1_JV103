package main.ultils;

import main.models.BenhAn;
import main.models.BenhAnThuong;
import main.models.BenhAnVIP;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    private static final String CSV_FILE_PATH = "src/data/medical_records.csv";

    public static List<BenhAn> loadRecordsFromFile() throws IOException {
        List<BenhAn> benhAnList = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(CSV_FILE_PATH));
        for (String line : lines) {
            String[] values = line.split(",");
            int soThuTu = Integer.parseInt(values[0]);
            String maBenhAn = values[1];
            String maBenhNhan = values[2];
            String tenBenhNhan = values[3];
            String ngayNhapVien = values[4];
            String ngayRaVien = values[5];
            String lyDoNhapVien = values[6];
            if (values.length == 8) {
                double phiNamVien = Double.parseDouble(values[7]);
                benhAnList.add(new BenhAnThuong(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, phiNamVien));
            } else if (values.length == 9) {
                String loaiVIP = values[7];
                String thoiHanVIP = values[8];
                benhAnList.add(new BenhAnVIP(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, loaiVIP, thoiHanVIP));
            }
        }
        return benhAnList;
    }

    public static void saveRecordsToFile(List<BenhAn> benhAnList) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
            for (BenhAn benhAn : benhAnList) {
                writer.write(benhAn.getDetails());
                writer.newLine();
            }
        }
    }
}
