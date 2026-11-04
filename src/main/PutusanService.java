import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PutusanService {

    public static Putusan search(
            DynamicArray<Putusan> data,
            String keyword) {

        int langkah = 0;

        for (int i = 0; i < data.size(); i++) {

            langkah++;

            Putusan p = data.get(i);

            boolean cocokNomor =
                p.getNomorPerkara() != null &&
                p.getNomorPerkara().equalsIgnoreCase(keyword);

            boolean cocokCaseId =
                p.getCaseId() != null &&
                p.getCaseId().equalsIgnoreCase(keyword);

            if (cocokNomor || cocokCaseId) {

                System.out.println("Data ditemukan!");
                System.out.println("Indeks: " + i);
                System.out.println("Jumlah langkah: " + langkah);

                return p;
            }
                public static DynamicArray<Putusan> filter(
            DynamicArray<Putusan> data,
            String jenis,
            double minBerat,
            double maxBerat,
            String pasal,
            int minVonis,
            int maxVonis) {

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {

            Putusan p = data.get(i);

            boolean cocokJenis =
                jenis == null ||
                (p.getJenisNarkotika() != null &&
                 p.getJenisNarkotika().equalsIgnoreCase(jenis));

            boolean cocokBerat =
                p.getBeratBarangBuktiGram() >= minBerat &&
                p.getBeratBarangBuktiGram() <= maxBerat;

            boolean cocokPasal =
                pasal == null ||
                (p.getPasalTerbukti() != null &&
                 p.getPasalTerbukti().toLowerCase()
                 .contains(pasal.toLowerCase()));

            boolean cocokVonis =
                p.getVonisBulan() >= minVonis &&
                p.getVonisBulan() <= maxVonis;

            if (cocokJenis &&
                cocokBerat &&
                cocokPasal &&
                cocokVonis) {

                hasil.add(p);
            }
        }

        return hasil;
    }
                public static void agregasi(DynamicArray<Putusan> data) {

        if (data.isEmpty()) {
            System.out.println("Data kosong.");
            return;
        }

        int jumlahPerkara = data.size();

        long totalVonis = 0;
        long totalDenda = 0;

        for (int i = 0; i < data.size(); i++) {

            Putusan p = data.get(i);

            totalVonis += p.getVonisBulan();
            totalDenda += p.getDendaRupiah();
        }

        double rataRataVonis =
            (double) totalVonis / jumlahPerkara;

        System.out.println("\n===== HASIL AGREGASI =====");

        System.out.println("Jumlah perkara: " + jumlahPerkara);

        System.out.println(
            "Rata-rata vonis: " + rataRataVonis + " bulan"
        );

        System.out.println(
            "Total denda: Rp" + totalDenda
        );

        System.out.println("\nDistribusi narkotika:");

        // Menghitung jenis narkotika tanpa HashMap
        for (int i = 0; i < data.size(); i++) {

            String jenis = data.get(i).getJenisNarkotika();

            if (jenis == null) {
                continue;
            }

            boolean sudahDihitung = false;

            for (int j = 0; j < i; j++) {

                String sebelumnya =
                    data.get(j).getJenisNarkotika();

                if (sebelumnya != null &&
                    sebelumnya.equalsIgnoreCase(jenis)) {

                    sudahDihitung = true;
                    break;
                }
            }

            if (!sudahDihitung) {

                int jumlah = 0;

                for (int j = 0; j < data.size(); j++) {

                    String pembanding =
                        data.get(j).getJenisNarkotika();

                    if (pembanding != null &&
                        pembanding.equalsIgnoreCase(jenis)) {

                        jumlah++;
                    }
                }

                System.out.println(jenis + " : " + jumlah);
            }
        }
    }
        }

        System.out.println("Data tidak ditemukan.");
        System.out.println("Jumlah langkah: " + langkah);

        return null;
    }
        public static DynamicArray<Putusan> sortingVonis(
            DynamicArray<Putusan> data) {

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        // Menyalin data
        for (int i = 0; i < data.size(); i++) {
            hasil.add(data.get(i));
        }

        // Insertion Sort berdasarkan vonis
        for (int i = 1; i < hasil.size(); i++) {

            Putusan key = hasil.get(i);

            int j = i - 1;

            while (j >= 0 &&
                   hasil.get(j).getVonisBulan() >
                   key.getVonisBulan()) {

                hasil.set(j + 1, hasil.get(j));

                j--;
            }

            hasil.set(j + 1, key);
        }

        return hasil;
    }
        private static String escapeCSV(String value) {

        if (value == null) {
            return "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    public static void exportCSV(
            DynamicArray<Putusan> data,
            String filePath) {

        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(filePath))) {

            writer.write(
                "caseId,nomorPerkara,pengadilan,tanggalPutusan," +
                "terdakwaKode,jenisNarkotika,beratBarangBuktiGram," +
                "pasalDakwaan,pasalTerbukti,tuntutanBulan," +
                "vonisBulan,dendaRupiah,barangBukti," +
                "keadaanMemberatkan,keadaanMeringankan," +
                "sourcePages,sourceFile"
            );

            writer.newLine();

            for (int i = 0; i < data.size(); i++) {

                Putusan p = data.get(i);

                String tanggal = p.getTanggalPutusan() == null
                    ? ""
                    : p.getTanggalPutusan().toString();

                String[] kolom = {
                    p.getCaseId(),
                    p.getNomorPerkara(),
                    p.getPengadilan(),
                    tanggal,
                    p.getTerdakwaKode(),
                    p.getJenisNarkotika(),
                    String.valueOf(p.getBeratBarangBuktiGram()),
                    p.getPasalDakwaan(),
                    p.getPasalTerbukti(),
                    String.valueOf(p.getTuntutanBulan()),
                    String.valueOf(p.getVonisBulan()),
                    String.valueOf(p.getDendaRupiah()),
                    p.getBarangBukti(),
                    p.getKeadaanMemberatkan(),
                    p.getKeadaanMeringankan(),
                    p.getSourcePages(),
                    p.getSourceFile()
                };

                for (int j = 0; j < kolom.length; j++) {

                    if (j > 0) {
                        writer.write(",");
                    }

                    writer.write(escapeCSV(kolom[j]));
                }

                writer.newLine();
            }

            System.out.println("Ekspor berhasil: " + filePath);

        } catch (IOException e) {

            System.out.println("Gagal ekspor: " + e.getMessage());
        }
    }
}
