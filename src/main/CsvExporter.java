import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvExporter {
    public static void export(DynamicArray<Putusan> data, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("caseId,nomorPerkara,pengadilan,tanggalPutusan,terdakwaKode,jenisNarkotika,beratBarangBuktiGram,pasalDakwaan,pasalTerbukti,tuntutanBulan,vonisBulan,dendaRupiah,barangBukti,keadaanMemberatkan,keadaanMeringankan,sourcePages,sourceFile");
            writer.newLine();
            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i).toCSV());
                writer.newLine();
            }
        }
    }
}
