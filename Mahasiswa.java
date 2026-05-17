public class Mahasiswa {
    private String nim;
    private String nama;
    private double ipk;

    // Function constructor
    public Mahasiswa(String nim, String nama, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.ipk = ipk;
    }

    // Start setter & getter
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }
    // End setter & getter

    // Override function toString untuk memudahkan print data
    @Override
    public String toString() {
        return "NIM: " + nim + ", Nama: " + nama + ", IPK: " + ipk;
    }
}