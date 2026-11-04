
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    // Membaca satu baris CSV dengan memperhatikan tanda kutip
    private static String[] parseLine(String line) {

        DynamicArray<String> fields = new DynamicArray<>();
        StringBuilder field = new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char c = line.charAt(i);

            if (c == '"') {

                if (insideQuotes &&
                    i + 1 < line.length() &&
                    line.charAt(i + 1) == '"') {

                    field.append('"');
                    i++;

                } else {
                    insideQuotes = !insideQuotes;
                }

            } else if (c == ',' && !insideQuotes) {

                fields.add(field.toString());
                field.setLength(0);

            } else {
                field.append(c);
            }
        }

        fields.add(field.toString());

        String[] result = new String[fields.size()];

        for (int i = 0; i < fields.size(); i++) {
            result[i] = fields.get(i);
        }

        return result;
    }

    // Mengubah nilai kosong menjadi null
    private static String getText(String[] row, int index) {

        if (index >= row.length) {
            return null;
        }

        String value = row[index].trim();

        if (value.isEmpty() ||
            value.equalsIgnoreCase("null") ||
            value.equalsIgnoreCase("nan")) {
            return null;
        }

        return value;
    }

    // Konversi angka
    private static double getDouble(String[] row, int index) {

        try {
            String value = getText(row, index);
            return value == null ? 0.0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static int getInt(String[] row, int index) {

        try {
            String value = getText(row, index);
            return value == null ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static long getLong(String[] row, int index) {

        try {
            String value = getText(row, index);
            return value == null ? 0L : Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    // Membaca seluruh CSV
    public static DynamicArray<Putusan> loadCSV(String filePath) {

        DynamicArray<Putusan> putusanList = new DynamicArray<>();

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(filePath))) {

            String line;

            // Melewati header CSV
            reader.readLine();

            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                try {

                    String[] row = parseLine(line);

                    if (row.length != 17) {
                        System.out.println(
                            "Record tidak valid pada baris " + lineNumber
                        );
                        continue;
                    }

                    String nomorPerkara = getText(row, 1);

                    if (nomorPerkara == null) {
                        System.out.println(
                            "Nomor perkara kosong pada baris " + lineNumber
                        );
                        continue;
                    }

                    Putusan p = new Putusan(

                        "P0001", // caseId sesuai permintaan

                        nomorPerkara,
                        getText(row, 2),

                        Putusan.parseTanggal(getText(row, 3)),

                        getText(row, 4),
                        getText(row, 5),
                        getDouble(row, 6),
                        getText(row, 7),
                        getText(row, 8),
                        getInt(row, 9),
                        getInt(row, 10),
                        getLong(row, 11),
                        getText(row, 12),
                        getText(row, 13),
                        getText(row, 14),
                        getText(row, 15),
                        getText(row, 16)
                    );

                    putusanList.add(p);

                } catch (Exception e) {

                    System.out.println(
                        "Gagal membaca baris " + lineNumber +
                        ": " + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                "Gagal membaca CSV: " + e.getMessage()
            );
        }

        return putusanList;
    }
}
