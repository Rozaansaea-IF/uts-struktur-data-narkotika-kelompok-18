import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final String CSV_PATH = "data/hasil_putusan.csv";
    private static DynamicArray<Putusan> data;
    private static final Stack<String> audit = new Stack<>();

    public static void main(String[] args) {
        CsvReader.ImportResult imported = CsvReader.load(CSV_PATH);
        data = imported.getRecords();
        System.out.println("\n=== SISTEM PENGELOLAAN DATA PUTUSAN NARKOTIKA ===");
        System.out.println("Record berhasil dimuat : " + data.size());
        System.out.println("Record ditolak         : " + imported.getRejected());
        System.out.println("Invariant DynamicArray : " + data.validateInvariant());
        audit.push("IMPORT: " + data.size() + " record dari " + CSV_PATH);
        if (data.size() != 50) {
            System.out.println("PERINGATAN: target tugas adalah 50 record valid. Periksa CSV dan pesan record ditolak.");
        }

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Pilih menu: ");
            switch (choice) {
                case 1 -> showAll(data);
                case 2 -> addRecord();
                case 3 -> removeRecord();
                case 4 -> searchRecord();
                case 5 -> filterRecords();
                case 6 -> showAggregation();
                case 7 -> showSorted();
                case 8 -> showAudit();
                case 9 -> exportRecords();
                case 10 -> runStructureTest();
                case 0 -> running = false;
                default -> System.out.println("Pilihan tidak tersedia.");
            }
            if (choice != 0) {
                System.out.println("Invariant DynamicArray: " + data.validateInvariant());
                System.out.println("Invariant Stack       : " + audit.validateInvariant());
            }
        }
        System.out.println("Program selesai.");
        INPUT.close();
    }

    private static void showMenu() {
        System.out.println("\n--------------- MENU ---------------");
        System.out.println("1. Tampilkan semua putusan");
        System.out.println("2. Tambah putusan");
        System.out.println("3. Hapus putusan berdasarkan indeks");
        System.out.println("4. Cari nomor perkara / caseId");
        System.out.println("5. Filter gabungan");
        System.out.println("6. Agregasi data");
        System.out.println("7. Tampilkan urutan berdasarkan vonis");
        System.out.println("8. Riwayat audit (Stack)");
        System.out.println("9. Ekspor data ke CSV");
        System.out.println("10. Jalankan uji operasi batas DynamicArray");
        System.out.println("0. Keluar");
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }
    private static String nullableText(String prompt) {
        String s = readText(prompt);
        return s.isEmpty() ? null : s;
    }
    private static int readInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(readText(prompt)); }
            catch (NumberFormatException e) { System.out.println("Masukkan bilangan bulat yang valid."); }
        }
    }
    private static long readLong(String prompt) {
        while (true) {
            try { return Long.parseLong(readText(prompt)); }
            catch (NumberFormatException e) { System.out.println("Masukkan bilangan bulat yang valid."); }
        }
    }
    private static double readDouble(String prompt) {
        while (true) {
            try { return Double.parseDouble(readText(prompt).replace(',', '.')); }
            catch (NumberFormatException e) { System.out.println("Masukkan angka yang valid, contoh 2.5."); }
        }
    }

    private static void showAll(DynamicArray<Putusan> list) {
        if (list.isEmpty()) { System.out.println("Tidak ada data."); return; }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("[" + i + "] " + list.get(i));
        }
        System.out.println("Total: " + list.size() + " putusan.");
        audit.push("TAMPIL: menampilkan " + list.size() + " putusan");
    }

    private static void addRecord() {
        System.out.println("Masukkan informasi putusan baru. Kolom opsional boleh dikosongkan.");
        String nomor = readText("Nomor perkara (wajib): ");
        if (nomor.isBlank()) { System.out.println("Gagal: nomor perkara wajib diisi."); return; }
        String court = nullableText("Pengadilan: ");
        LocalDate date = Putusan.parseDate(nullableText("Tanggal (contoh 26 Maret 2023 atau YYYY-MM-DD): "));
        String type = nullableText("Jenis narkotika: ");
        double weight = readDouble("Berat barang bukti gram (0 jika tidak diketahui): ");
        String charged = nullableText("Pasal dakwaan: ");
        String proven = nullableText("Pasal terbukti: ");
        int demand = readInt("Tuntutan bulan (0 jika tidak diketahui): ");
        int sentence = readInt("Vonis bulan (0 jika tidak diketahui): ");
        long fine = readLong("Denda rupiah (0 jika tidak diketahui): ");
        String evidence = nullableText("Ringkasan barang bukti: ");
        String aggravating = nullableText("Keadaan memberatkan: ");
        String mitigating = nullableText("Keadaan meringankan: ");
        String pages = nullableText("Source pages: ");
        String source = nullableText("Source file: ");
        if (weight < 0 || demand < 0 || sentence < 0 || fine < 0) {
            System.out.println("Gagal: nilai numerik tidak boleh negatif."); return;
        }
        String code = "putusan_" + nomor;
        Putusan p = new Putusan("P0001", nomor, court, date, code, type, weight,
            charged, proven, demand, sentence, fine, evidence, aggravating, mitigating, pages, source);
        data.add(p);
        audit.push("TAMBAH: " + nomor);
        System.out.println("Putusan berhasil ditambahkan pada indeks " + (data.size() - 1));
    }

    private static void removeRecord() {
        if (data.isEmpty()) { System.out.println("Data kosong."); return; }
        int index = readInt("Masukkan indeks yang akan dihapus (0 sampai " + (data.size() - 1) + "): ");
        try {
            Putusan removed = data.remove(index);
            audit.push("HAPUS: indeks " + index + ", perkara " + removed.getNomorPerkara());
            System.out.println("Berhasil menghapus " + removed.getNomorPerkara());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indeks tidak valid. Data tidak berubah.");
        }
    }

    private static void searchRecord() {
        String keyword = readText("Masukkan nomor perkara atau caseId: ");
        PutusanService.SearchResult result = PutusanService.search(data, keyword);
        System.out.println("Jumlah langkah pencarian: " + result.langkah);
        if (result.putusan == null) System.out.println("Data tidak ditemukan.");
        else {
            System.out.println("Ditemukan pada indeks " + result.index);
            System.out.println(result.putusan);
        }
        audit.push("CARI: " + keyword + " | langkah=" + result.langkah + " | " + (result.putusan == null ? "tidak ditemukan" : "ditemukan"));
    }

    private static void filterRecords() {
        PutusanService.FilterCriteria c = new PutusanService.FilterCriteria();
        c.jenisNarkotika = nullableText("Jenis narkotika (kosong = semua): ");
        c.minBerat = readDouble("Berat minimum gram: ");
        c.maxBerat = readDouble("Berat maksimum gram: ");
        c.pasalTerbukti = nullableText("Kata/pasal terbukti (kosong = semua): ");
        c.minVonis = readInt("Vonis minimum bulan: ");
        c.maxVonis = readInt("Vonis maksimum bulan: ");
        if (c.minBerat < 0 || c.maxBerat < c.minBerat || c.minVonis < 0 || c.maxVonis < c.minVonis) {
            System.out.println("Rentang filter tidak valid."); return;
        }
        DynamicArray<Putusan> result = PutusanService.filter(data, c);
        System.out.println("\nHasil filter:");
        for (int i = 0; i < result.size(); i++) System.out.println(result.get(i));
        System.out.println("Jumlah hasil filter: " + result.size());
        audit.push("FILTER: menghasilkan " + result.size() + " record");
        if (readText("Ekspor hasil filter? (y/n): ").equalsIgnoreCase("y")) {
            exportTo(result, "data/hasil_filter.csv");
        }
    }

    private static void showAggregation() {
        PutusanService.AggregateResult r = PutusanService.aggregate(data);
        System.out.println("\n===== AGREGASI =====");
        System.out.println("Jumlah perkara : " + r.jumlahPerkara);
        System.out.printf("Rata-rata vonis: %.2f bulan%n", r.rataRataVonis);
        System.out.println("Total denda    : Rp" + r.totalDenda);
        System.out.println("Distribusi jenis narkotika:");
        for (int i = 0; i < r.jenis.size(); i++) {
            System.out.println("- " + r.jenis.get(i) + ": " + r.jumlahPerJenis.get(i));
        }
        audit.push("AGREGASI: menghitung statistik " + data.size() + " record");
    }

    private static void showSorted() {
        DynamicArray<Putusan> sorted = PutusanService.sortedByVonis(data);
        System.out.println("\nUrutan vonis dari terkecil ke terbesar (salinan):");
        for (int i = 0; i < sorted.size(); i++) {
            Putusan p = sorted.get(i);
            System.out.println((i + 1) + ". " + p.getNomorPerkara() + " | " + p.getVonisBulan() + " bulan");
        }
        System.out.println("Invariant data utama setelah sorting: " + data.validateInvariant());
        audit.push("SORTING: tampilan terurut berdasarkan vonis");
    }

    private static void showAudit() {
        System.out.println("\n===== RIWAYAT AUDIT (lama ke baru) =====");
        if (audit.isEmpty()) { System.out.println("Belum ada riwayat."); return; }
        for (int i = 0; i < audit.size(); i++) System.out.println((i + 1) + ". " + audit.getFromBottom(i));
        String keyword = nullableText("Cari teks pada riwayat (kosong = lewati): ");
        if (keyword != null) {
            boolean found = audit.searchPreserve(keyword);
            System.out.println("Pencarian Stack non-destruktif: " + (found ? "ditemukan" : "tidak ditemukan"));
            System.out.println("Urutan Stack tetap: " + audit.validateInvariant());
        }
        System.out.println("Operasi terbaru (peek): " + audit.peek());
    }

    private static void exportRecords() {
        String path = readText("Nama/path file output (contoh data/laporan.csv): ");
        if (path.isBlank()) path = "data/laporan.csv";
        exportTo(data, path);
    }
    private static void exportTo(DynamicArray<Putusan> list, String path) {
        try {
            CsvExporter.export(list, path);
            System.out.println("Berhasil mengekspor " + list.size() + " record ke " + path);
            audit.push("EKSPOR: " + list.size() + " record ke " + path);
        } catch (IOException e) {
            System.out.println("Ekspor gagal: " + e.getMessage());
        }
    }

    /** Menguji hapus elemen awal/tengah/akhir, tambah kembali, dan invariant pada salinan. */
    private static void runStructureTest() {
        DynamicArray<Putusan> test = new DynamicArray<>();
        for (int i = 0; i < data.size(); i++) test.add(data.get(i));
        if (test.size() < 3) { System.out.println("Butuh minimal 3 record untuk pengujian."); return; }
        int originalSize = test.size();
        Putusan first = test.remove(0);
        Putusan middle = test.remove(test.size() / 2);
        Putusan last = test.remove(test.size() - 1);
        boolean afterRemove = test.validateInvariant() && test.size() == originalSize - 3;
        test.add(first); test.add(middle); test.add(last);
        boolean afterAdd = test.validateInvariant() && test.size() == originalSize;
        System.out.println("Hapus pertama/tengah/terakhir: " + (afterRemove ? "LULUS" : "GAGAL"));
        System.out.println("Tambah kembali tiga record  : " + (afterAdd ? "LULUS" : "GAGAL"));
        System.out.println("Urutan data utama tidak berubah: " + data.validateInvariant());
        audit.push("TEST: uji hapus batas dan tambah kembali " + (afterRemove && afterAdd ? "LULUS" : "GAGAL"));
    }
}
