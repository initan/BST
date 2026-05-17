import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class Jalur {
    private String lokasiTujuan;
    private int jarak;

    // Function constructor beserta set data
    public Jalur(String lokasiTujuan, int jarak) {
        this.lokasiTujuan = lokasiTujuan;
        this.jarak = jarak;
    }

    // Start getter
    public String getlokasiTujuan() {
        return lokasiTujuan;
    }

    public int getJarak() {
        return jarak;
    }
    // End Getter
}

class NavigasiGraph {
    private Map<String, List<Jalur>> adjacencyList;

    // Function constructor
    public NavigasiGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // Function menambahkan titik
    public void addTitik(String namaTitik) {
        adjacencyList.putIfAbsent(namaTitik, new ArrayList<>());
    }

    // Menambahkan jalur search dari titik asal ke tujuan beserta jaraknya
    public void tambahJalur(String asal, String tujuan, int jarak) {
        addTitik(asal);
        addTitik(tujuan);
        adjacencyList.get(asal).add(new Jalur(tujuan, jarak));
    }

    // Function penelusuran BFS, alurnya mengunjungi lokasi terdekat terlebih dahulu 
    public void penelusuranBFS(String titikAwal) {
        if (!adjacencyList.containsKey(titikAwal)) {
            System.out.println("Titik awal tidak ditemukan!");
            return;
        }

        System.out.print("Hasil Penelusuran BFS dari titik " + titikAwal + ": ");

        // Set untuk mencatat node yang sudah dikunjungi agar tidak terjadi loop
        Set<String> dikunjungi = new LinkedHashSet<>();
        // Queue sebagai struktur data utama BFS (First In First Out)
        Queue<String> queue = new LinkedList<>();

        // Mulai dari titik awal
        dikunjungi.add(titikAwal);
        queue.add(titikAwal);

        while (!queue.isEmpty()) {
            // Ambil elemen paling depan dari antrean
            String lokasiSaatIni = queue.poll();
            System.out.print(lokasiSaatIni + " ");

            // Ambil semua lokasi saat ini
            for (Jalur tetangga : adjacencyList.get(lokasiSaatIni)) {
                String namaTetangga = tetangga.getlokasiTujuan();

                // Jika lokasi belum pernah dikunjungi, masukkan ke antrean
                if (!dikunjungi.contains(namaTetangga)) {
                    dikunjungi.add(namaTetangga);
                    queue.add(namaTetangga);
                }
            }
        }
        System.out.println();
    }

    // Function penelusuran DFS, alurnya menelusuri salah satu jalur terlebih dahulu sampai mentok kembali lagi untuk menelusuri jalur lainnya
    public void penelusuranDFS(String titikAwal) {
        if (!adjacencyList.containsKey(titikAwal)) {
            System.out.println("Titik awal tidak ditemukan!");
            return;
        }

        System.out.print("Hasil Penelusuran DFS dari titik " + titikAwal + ": ");
        Set<String> dikunjungi = new LinkedHashSet<>();

        // Panggil fungsi rekursif utama
        dfsHelper(titikAwal, dikunjungi);
        System.out.println();
    }

    // Function rekursif untuk DFS
    private void dfsHelper(String lokasiSaatIni, Set<String> dikunjungi) {
        // Menandai lokasi saat ini sebagai sudah dikunjungi, lalu tampilkan
        dikunjungi.add(lokasiSaatIni);
        System.out.print(lokasiSaatIni + " ");

        // Telusuri mendalam ke setiap lokasi yang belum dikunjungi
        for (Jalur Lokasi : adjacencyList.get(lokasiSaatIni)) {
            String namaLokasi = Lokasi.getlokasiTujuan();

            if (!dikunjungi.contains(namaLokasi)) {
                // Rekursif: Langsung masuk ke lokasi tersebut sebelum menghabiskan lokasi lain di level ini
                dfsHelper(namaLokasi, dikunjungi);
            }
        }
    }

    // Function cariJalurTerpendek untuk implementasi DIJKSTRA
    public void cariJalurTerpendek(String titikAwal, String titikTujuan) {
        if (!adjacencyList.containsKey(titikAwal) || !adjacencyList.containsKey(titikTujuan)) {
            System.out.println("Titik awal atau tujuan tidak terdaftar!");
            return;
        }

        Map<String, Integer> jarakTerpendek = new HashMap<>();
        Map<String, String> pencatatRute = new HashMap<>();
        Set<String> sudahDikunjungi = new LinkedHashSet<>();

        for (String lokasi : adjacencyList.keySet()) {
            jarakTerpendek.put(lokasi, Integer.MAX_VALUE);
        }
        jarakTerpendek.put(titikAwal, 0);

        // PriorityQueue mendahulukan lokasi dengan total jarak terkecil
        PriorityQueue<String[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> Integer.parseInt(a[1])));
        queue.add(new String[] { titikAwal, "0" });

        while (!queue.isEmpty()) {
            String[] dataSaatIni = queue.poll();
            String lokasiSaatIni = dataSaatIni[0];

            if (sudahDikunjungi.contains(lokasiSaatIni))
                continue;
            sudahDikunjungi.add(lokasiSaatIni);

            if (lokasiSaatIni.equals(titikTujuan))
                break;

            for (Jalur tetangga : adjacencyList.get(lokasiSaatIni)) {
                String lokasiTetangga = tetangga.getlokasiTujuan();

                if (!sudahDikunjungi.contains(lokasiTetangga)) {
                    int jarakBaru = jarakTerpendek.get(lokasiSaatIni) + tetangga.getJarak();

                    if (jarakBaru < jarakTerpendek.get(lokasiTetangga)) {
                        jarakTerpendek.put(lokasiTetangga, jarakBaru);
                        pencatatRute.put(lokasiTetangga, lokasiSaatIni);
                        queue.add(new String[] { lokasiTetangga, String.valueOf(jarakBaru) });
                    }
                }
            }
        }

        printHasilDijkstra(titikAwal, titikTujuan, jarakTerpendek, pencatatRute);
    }

    private void printHasilDijkstra(String awal, String tujuan, Map<String, Integer> jarak, Map<String, String> rute) {
        if (jarak.get(tujuan) == Integer.MAX_VALUE) {
            System.out.println("Tidak ada jalur yang menghubungkan " + awal + " ke " + tujuan);
            return;
        }

        LinkedList<String> jalurRute = new LinkedList<>();
        String langkah = tujuan;
        jalurRute.addFirst(langkah);

        while (rute.containsKey(langkah)) {
            langkah = rute.get(langkah);
            jalurRute.addFirst(langkah);
        }

        System.out.println("Jalur terpendek ditemukan (Dijkstra): " + String.join(" -> ", jalurRute));
        System.out.println("Jarak total: " + jarak.get(tujuan) + " km");
    }
}