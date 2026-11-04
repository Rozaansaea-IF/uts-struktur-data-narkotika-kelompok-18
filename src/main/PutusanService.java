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
        }

        System.out.println("Data tidak ditemukan.");
        System.out.println("Jumlah langkah: " + langkah);

        return null;
    }
