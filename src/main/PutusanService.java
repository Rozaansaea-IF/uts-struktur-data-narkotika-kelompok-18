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
        }

        System.out.println("Data tidak ditemukan.");
        System.out.println("Jumlah langkah: " + langkah);

        return null;
    }
