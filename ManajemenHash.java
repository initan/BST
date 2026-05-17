import java.util.HashMap;

public class ManajemenHash {
    // Deklarasi HashMap bawaan java
    private HashMap<String, Mahasiswa> tabelMhs;

    // Function constructor ManajemenHash
    public ManajemenHash() {
        this.tabelMhs = new HashMap<>();
    }

    // Function tambah data mahasiswa
    public void addMahasiswa(Mahasiswa mhs) {
        if (mhs != null) {
            tabelMhs.put(mhs.getNim(), mhs);
            System.out.println(
                    "Tambah Mahasiswa: NIM " + mhs.getNim() + ", Nama: " + mhs.getNama() + ", IPK: " + mhs.getIpk());
        }
    }

    // Function search data mahasiswa berdasarkan NIM
    public Mahasiswa searchMahasiswa(String nim) {
        return tabelMhs.get(nim);
    }

    // Function hapus data mahasiswa berdasarkkan NIM
    public void deleteMahasiswa(String nim) {
        if (tabelMhs.containsKey(nim)) {
            Mahasiswa terhapus = tabelMhs.remove(nim);
            System.out.println("Data dengan NIM " + nim + " (" + terhapus.getNama() + ") berhasil dihapus.");
        } else {
            System.out.println("Gagal menghapus! NIM " + nim + " tidak ditemukan.");
        }
    }

    // Function untuk menampilkan semua data mahasiswa
    public void showDataMahasiswa() {
        if (tabelMhs.isEmpty()) {
            System.out.println("Hash Table kosong.");
            return;
        }
        System.out.println("\n--- DAFTAR MAHASISWA DI HASH TABLE ---");
        for (Mahasiswa m : tabelMhs.values()) {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        ManajemenHash app = new ManajemenHash();

        System.out.println("== INPUT 15 DATA MAHASISWA ==");
        app.addMahasiswa(new Mahasiswa("20001", "Andi", 3.75));
        app.addMahasiswa(new Mahasiswa("20002", "Budi", 3.40));
        app.addMahasiswa(new Mahasiswa("20003", "Citra", 3.85));
        app.addMahasiswa(new Mahasiswa("20004", "Dewi", 3.20));
        app.addMahasiswa(new Mahasiswa("20005", "Eko", 3.65));
        app.addMahasiswa(new Mahasiswa("20006", "Ari", 3.85));
        app.addMahasiswa(new Mahasiswa("20007", "Hanum", 3.80));
        app.addMahasiswa(new Mahasiswa("20008", "Hana", 3.55));
        app.addMahasiswa(new Mahasiswa("20009", "Indra", 3.48));
        app.addMahasiswa(new Mahasiswa("20010", "Joko", 3.30));
        app.addMahasiswa(new Mahasiswa("20011", "Kania", 3.72));
        app.addMahasiswa(new Mahasiswa("20012", "Lutfi", 3.60));
        app.addMahasiswa(new Mahasiswa("20013", "Mita", 3.95));
        app.addMahasiswa(new Mahasiswa("20014", "Dini", 3.90));
        app.addMahasiswa(new Mahasiswa("20015", "Gilang", 3.10));

        System.out.println("\n == TES SEARCH MAHASISWA ==");
        String searchNim = "20007";

        long startTime = System.nanoTime();
        Mahasiswa hasilCari = app.searchMahasiswa(searchNim);
        long endTime = System.nanoTime();

        if (hasilCari != null) {
            System.out.println("Cari Mahasiswa dengan NIM " + searchNim + ": Ditemukan - " + hasilCari.getNama()
                    + ", IPK " + hasilCari.getIpk());
        } else {
            System.out.println("Cari Mahasiswa dengan NIM " + searchNim + ": Data Tidak Ditemukan.");
        }
        System.out.println("Waktu Eksekusi Pencarian Hash Table: " + (endTime - startTime) + " ns");

        System.out.println("\n== TES DELETE MAHASISWA ==");
        app.deleteMahasiswa("20013");

        app.showDataMahasiswa();
    }
}