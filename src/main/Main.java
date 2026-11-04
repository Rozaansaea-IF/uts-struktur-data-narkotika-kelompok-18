
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Membaca CSV
        DynamicArray<Putusan> data =
            CsvReader.loadCSV("data/hasil_putusan.csv");

        System.out.println("================================");
        System.out.println("   SISTEM DATA PUTUSAN NARKOTIKA");
        System.out.println("================================");

        System.out.println(
            "Jumlah data berhasil dimuat: " + data.size()
        );

        System.out.println(
            "Invariant: " + data.validateInvariant()
        );

        int pilihan;

        do {

            System.out.println("\n========== MENU ==========");
            System.out.println("1. Tampilkan semua putusan");
            System.out.println("2. Cari berdasarkan nomor perkara");
            System.out.println("3. Tambah data");
            System.out.println("4. Hapus data berdasarkan indeks");
            System.out.println("5. Tampilkan jumlah data");
            System.out.println("6. Filter jenis narkotika");
            System.out.println("7. Rata-rata vonis");
            System.out.println("0. Keluar");

            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1:

                    data.display();
                    break;

                case 2:

                    System.out.print("Masukkan nomor perkara: ");
                    String nomor = input.nextLine();

                    boolean ditemukan = false;

                    for (int i = 0; i < data.size(); i++) {

                        Putusan p = data.get(i);

                        if (p.getNomorPerkara() != null &&
                            p.getNomorPerkara().equalsIgnoreCase(nomor)) {

                            System.out.println("\nData ditemukan:");
                            System.out.println(p);

                            System.out.println(
                                "Ditemukan pada indeks: " + i
                            );

                            ditemukan = true;
                            break;
                        }
                    }

                    if (!ditemukan) {
                        System.out.println("Data tidak ditemukan.");
                    }

                    break;

                case 3: 
                    System.out.print("Nomor perkara: ");
    String nomorBaru = input.nextLine();

    System.out.print("Nama pengadilan: ");
    String pengadilanBaru = input.nextLine();

    System.out.print("Jenis narkotika: ");
    String jenisBaru = input.nextLine();

    System.out.print("Berat barang bukti (gram): ");
    double beratBaru = input.nextDouble();

    input.nextLine();

    System.out.print("Pasal terbukti: ");
    String pasalBaru = input.nextLine();

    System.out.print("Vonis (bulan): ");
    int vonisBaru = input.nextInt();

    input.nextLine();

    Putusan baru = new Putusan(
        "P0001",
        nomorBaru,
        pengadilanBaru,
        null,
        "putusan_" + nomorBaru,
        jenisBaru,
        beratBaru,
        null,
        pasalBaru,
        0,
        vonisBaru,
        0L,
        null,
        null,
        null,
        null,
        null
    );

    data.add(baru);

    System.out.println("Putusan berhasil ditambahkan!");

    break;
                    

                    

                case 4:

                    System.out.print("Masukkan indeks yang dihapus: ");
                    int index = input.nextInt();

                    try {

                        Putusan removed = data.remove(index);

                        System.out.println("Data berhasil dihapus:");
                        System.out.println(removed.getNomorPerkara());

                    } catch (IndexOutOfBoundsException e) {

                        System.out.println("Indeks tidak valid.");
                    }

                    break;

                case 5:

                    System.out.println(
                        "Jumlah putusan: " + data.size()
                    );

                    break;

                case 6:

                    System.out.print("Jenis narkotika: ");
                    String jenis = input.nextLine();

                    int jumlah = 0;

                    for (int i = 0; i < data.size(); i++) {

                        Putusan p = data.get(i);

                        if (p.getJenisNarkotika() != null &&
                            p.getJenisNarkotika().equalsIgnoreCase(jenis)) {

                            System.out.println(p);
                            System.out.println("----------------");

                            jumlah++;
                        }
                    }

                    System.out.println(
                        "Total ditemukan: " + jumlah
                    );

                    break;

                case 7:

                    if (data.isEmpty()) {
                        System.out.println("Data masih kosong.");
                        break;
                    }

                    long totalVonis = 0;

                    for (int i = 0; i < data.size(); i++) {
                        totalVonis += data.get(i).getVonisBulan();
                    }

                    double rataRata =
                        (double) totalVonis / data.size();

                    System.out.println(
                        "Rata-rata vonis: " + rataRata + " bulan"
                    );

                    break;

                case 0:

                    System.out.println("Program selesai.");
                    break;

                default:

                    System.out.println("Pilihan tidak valid.");
            }

            System.out.println(
                "\nInvariant: " + data.validateInvariant()
            );

        } while (pilihan != 0);

        input.close();
    }
}
