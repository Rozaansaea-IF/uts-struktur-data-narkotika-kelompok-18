
public class StrukturDataTest {
    public static void main(String[] args) {
        DynamicArray<Integer> a = new DynamicArray<>(2);
        a.add(10); a.add(20); a.add(30); // memaksa resize
        check(a.size() == 3, "size setelah add");
        check(a.get(0) == 10 && a.get(2) == 30, "get");
        a.insert(1, 15);
        check(a.get(1) == 15, "insert");
        check(a.remove(1) == 15, "remove");
        check(a.validateInvariant(), "invariant DynamicArray");

        Stack<String> s = new Stack<>();
        s.push("awal"); s.push("tengah"); s.push("akhir");
        check(s.searchPreserve("tengah"), "Stack searchPreserve menemukan data");
        check("akhir".equals(s.peek()), "urutan Stack tetap setelah searchPreserve");
        check(s.validateInvariant(), "invariant Stack");
        check("akhir".equals(s.pop()), "LIFO pop");

        CsvReader.ImportResult imported = CsvReader.load("data/hasil_putusan.csv");
        check(imported.getRecords().validateInvariant(), "invariant data CSV");
        PutusanService.SearchResult missing = PutusanService.search(imported.getRecords(), "NOMOR-TIDAK-ADA");
        check(missing.putusan == null, "search data tidak ditemukan");
        if (!imported.getRecords().isEmpty()) {
            String firstNumber = imported.getRecords().get(0).getNomorPerkara();
            check(PutusanService.search(imported.getRecords(), firstNumber).putusan != null, "search data ditemukan");
        }
        DynamicArray<Putusan> sorted = PutusanService.sortedByVonis(imported.getRecords());
        for (int i = 1; i < sorted.size(); i++)
            check(sorted.get(i - 1).getVonisBulan() <= sorted.get(i).getVonisBulan(), "sorting vonis");
        check(imported.getRecords().validateInvariant(), "data asli tetap valid setelah sorting");
        System.out.println("SEMUA PENGUJIAN LOLOS. Record valid dari CSV: " + imported.getRecords().size()
            + ", record ditolak: " + imported.getRejected());
    }

    private static void check(boolean condition, String description) {
        if (!condition) throw new AssertionError("GAGAL: " + description);
        System.out.println("LULUS: " + description);
    }
}
