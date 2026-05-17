import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class QATester {

    // ===================== BINARY SEARCH TREE =====================
    static class BSTNode {
        int data;
        BSTNode left, right;

        BSTNode(int data) {
            this.data = data;
        }
    }

    static class BST {
        BSTNode root;

        void insert(int data) {
            root = insertRec(root, data);
        }

        BSTNode insertRec(BSTNode node, int data) {
            if (node == null) return new BSTNode(data);
            if (data < node.data) node.left = insertRec(node.left, data);
            else if (data > node.data) node.right = insertRec(node.right, data);
            return node;
        }

        boolean search(int data) {
            return searchRec(root, data);
        }

        boolean searchRec(BSTNode node, int data) {
            if (node == null) return false;
            if (node.data == data) return true;
            return data < node.data ? searchRec(node.left, data) : searchRec(node.right, data);
        }

        void delete(int data) {
            root = deleteRec(root, data);
        }

        BSTNode deleteRec(BSTNode node, int data) {
            if (node == null) return null;
            if (data < node.data) node.left = deleteRec(node.left, data);
            else if (data > node.data) node.right = deleteRec(node.right, data);
            else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                node.data = minValue(node.right);
                node.right = deleteRec(node.right, node.data);
            }
            return node;
        }

        int minValue(BSTNode node) {
            int min = node.data;
            while (node.left != null) {
                min = node.left.data;
                node = node.left;
            }
            return min;
        }

        int countInorder() {
            return countInorderRec(root);
        }

        int countInorderRec(BSTNode node) {
            if (node == null) return 0;
            return 1 + countInorderRec(node.left) + countInorderRec(node.right);
        }
    }

    // ===================== HASH TABLE =====================
    static class Student {
        int id;
        String name;

        Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student ID: " + id + ", Name: " + name;
        }
    }

    static class HashTableManagement {
        private final HashMap<Integer, Student> studentMap = new HashMap<>();

        void addStudent(int id, String name) {
            studentMap.put(id, new Student(id, name));
        }

        Student searchStudent(int id) {
            return studentMap.get(id);
        }

        boolean deleteStudent(int id) {
            return studentMap.remove(id) != null;
        }

        int size() {
            return studentMap.size();
        }
    }

    // ===================== GRAPH =====================
    static class Jalur {
        String tujuan;
        int jarak;

        Jalur(String tujuan, int jarak) {
            this.tujuan = tujuan;
            this.jarak = jarak;
        }
    }

    static class NavigasiGraph {
        private final Map<String, List<Jalur>> adj = new HashMap<>();

        void addTitik(String n) {
            adj.putIfAbsent(n, new ArrayList<>());
        }

        void tambahJalur(String a, String b, int jarak) {
            addTitik(a);
            addTitik(b);
            adj.get(a).add(new Jalur(b, jarak));
            // Anggap graph tidak berarah agar Dijkstra/BFS/DFS lebih bermakna saat pengujian.
            adj.get(b).add(new Jalur(a, jarak));
        }

        int bfsCount(String start) {
            if (!adj.containsKey(start)) return 0;
            Set<String> visited = new LinkedHashSet<>();
            Queue<String> queue = new LinkedList<>();
            visited.add(start);
            queue.add(start);
            while (!queue.isEmpty()) {
                String cur = queue.poll();
                for (Jalur j : adj.get(cur)) {
                    if (!visited.contains(j.tujuan)) {
                        visited.add(j.tujuan);
                        queue.add(j.tujuan);
                    }
                }
            }
            return visited.size();
        }

        int dfsCount(String start) {
            if (!adj.containsKey(start)) return 0;
            Set<String> visited = new LinkedHashSet<>();
            dfsHelper(start, visited);
            return visited.size();
        }

        void dfsHelper(String cur, Set<String> visited) {
            visited.add(cur);
            for (Jalur j : adj.get(cur)) {
                if (!visited.contains(j.tujuan)) dfsHelper(j.tujuan, visited);
            }
        }

        // Dijkstra. Mengembalikan jarak terpendek dari start ke goal,
        // atau Integer.MAX_VALUE bila tidak terhubung.
        int dijkstra(String start, String goal) {
            if (!adj.containsKey(start) || !adj.containsKey(goal)) return Integer.MAX_VALUE;
            Map<String, Integer> dist = new HashMap<>();
            for (String k : adj.keySet()) dist.put(k, Integer.MAX_VALUE);
            dist.put(start, 0);
            PriorityQueue<String[]> pq = new PriorityQueue<>(
                    Comparator.comparingInt(a -> Integer.parseInt(a[1])));
            pq.add(new String[] { start, "0" });
            Set<String> seen = new LinkedHashSet<>();
            while (!pq.isEmpty()) {
                String[] cur = pq.poll();
                String node = cur[0];
                if (seen.contains(node)) continue;
                seen.add(node);
                if (node.equals(goal)) break;
                for (Jalur j : adj.get(node)) {
                    if (seen.contains(j.tujuan)) continue;
                    int nd = dist.get(node) + j.jarak;
                    if (nd < dist.get(j.tujuan)) {
                        dist.put(j.tujuan, nd);
                        pq.add(new String[] { j.tujuan, String.valueOf(nd) });
                    }
                }
            }
            return dist.get(goal);
        }

        int sizeNodes() {
            return adj.size();
        }
    }

    // ===================== UTIL =====================
    static String fmtNs(long ns) {
        if (ns < 1_000) return ns + " ns";
        if (ns < 1_000_000) return String.format(Locale.US, "%.2f µs", ns / 1_000.0);
        if (ns < 1_000_000_000L) return String.format(Locale.US, "%.2f ms", ns / 1_000_000.0);
        return String.format(Locale.US, "%.2f s", ns / 1_000_000_000.0);
    }

    static void header(String title) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println(" " + title);
        System.out.println("============================================================");
    }

    // ===================== BENCHMARK: BST =====================
    static void benchBST(int n, long seed) {
        BST bst = new BST();
        Random rng = new Random(seed);

        // Siapkan data unik agar tinggi pohon mendekati rata-rata.
        int[] data = new int[n];
        for (int i = 0; i < n; i++) data[i] = rng.nextInt(n * 10);

        long t0 = System.nanoTime();
        for (int v : data) bst.insert(v);
        long tInsert = System.nanoTime() - t0;

        // Cari N kunci, separuh ada separuh tidak.
        long t1 = System.nanoTime();
        int hit = 0;
        for (int i = 0; i < n; i++) {
            int key = (i % 2 == 0) ? data[rng.nextInt(n)] : -i - 1;
            if (bst.search(key)) hit++;
        }
        long tSearch = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        int countedNodes = bst.countInorder();
        long tTraversal = System.nanoTime() - t2;

        long t3 = System.nanoTime();
        for (int i = 0; i < n; i++) bst.delete(data[i]);
        long tDelete = System.nanoTime() - t3;

        System.out.printf(Locale.US,
                "BST   n=%-6d insert=%-11s search(N)=%-11s inorder=%-11s delete=%-11s hits=%d nodes=%d%n",
                n, fmtNs(tInsert), fmtNs(tSearch), fmtNs(tTraversal), fmtNs(tDelete), hit, countedNodes);
    }

    // ===================== BENCHMARK: HASH TABLE =====================
    static void benchHash(int n, long seed) {
        HashTableManagement htm = new HashTableManagement();
        Random rng = new Random(seed);

        int[] ids = new int[n];
        for (int i = 0; i < n; i++) ids[i] = 100_000 + i;
        // Acak urutan agar tidak sequential.
        for (int i = n - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = ids[i]; ids[i] = ids[j]; ids[j] = tmp;
        }

        long t0 = System.nanoTime();
        for (int id : ids) htm.addStudent(id, "Mhs-" + id);
        long tAdd = System.nanoTime() - t0;

        long t1 = System.nanoTime();
        int found = 0;
        for (int i = 0; i < n; i++) {
            int key = (i % 2 == 0) ? ids[rng.nextInt(n)] : -i - 1;
            if (htm.searchStudent(key) != null) found++;
        }
        long tSearch = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        int deleted = 0;
        for (int id : ids) if (htm.deleteStudent(id)) deleted++;
        long tDelete = System.nanoTime() - t2;

        System.out.printf(Locale.US,
                "HASH  n=%-6d add=%-11s search(N)=%-11s delete=%-11s found=%d removed=%d%n",
                n, fmtNs(tAdd), fmtNs(tSearch), fmtNs(tDelete), found, deleted);
    }

    // ===================== BENCHMARK: GRAPH =====================
    static void benchGraph(int n, long seed) {
        NavigasiGraph g = new NavigasiGraph();
        Random rng = new Random(seed);

        // Bangun graph terhubung berbentuk rantai + sambungan acak.
        String[] node = new String[n];
        for (int i = 0; i < n; i++) node[i] = "N" + i;

        long t0 = System.nanoTime();
        for (int i = 1; i < n; i++) g.tambahJalur(node[i - 1], node[i], 1 + rng.nextInt(20));
        // Tambahan sambungan acak agar bukan sekedar linked list.
        int extra = Math.max(1, n / 2);
        for (int i = 0; i < extra; i++) {
            int a = rng.nextInt(n), b = rng.nextInt(n);
            if (a != b) g.tambahJalur(node[a], node[b], 1 + rng.nextInt(20));
        }
        long tBuild = System.nanoTime() - t0;

        long t1 = System.nanoTime();
        int visitedBfs = g.bfsCount(node[0]);
        long tBFS = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        int visitedDfs = g.dfsCount(node[0]);
        long tDFS = System.nanoTime() - t2;

        long t3 = System.nanoTime();
        int totalDist = g.dijkstra(node[0], node[n - 1]);
        long tDij = System.nanoTime() - t3;

        System.out.printf(Locale.US,
                "GRAPH n=%-6d build=%-11s BFS=%-11s DFS=%-11s Dijkstra=%-11s bfs=%d dfs=%d dist0->%d=%s%n",
                n, fmtNs(tBuild), fmtNs(tBFS), fmtNs(tDFS), fmtNs(tDij),
                visitedBfs, visitedDfs, n - 1,
                totalDist == Integer.MAX_VALUE ? "INF" : String.valueOf(totalDist));
    }

    // ===================== UJI FUNGSIONAL =====================
    static void uji_BSTFungsional() {
        header("UJI FUNGSIONAL - BST (20 data sampel)");
        BST bst = new BST();
        int[] sample = { 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85, 5, 15, 33, 66, 90 };
        for (int v : sample) bst.insert(v);
        System.out.println("Total node di tree (inorder count): " + bst.countInorder());
        System.out.println("Cari 40 -> " + bst.search(40) + " (harus true)");
        System.out.println("Cari 41 -> " + bst.search(41) + " (harus false)");
        bst.delete(30);
        System.out.println("Setelah delete 30, total node: " + bst.countInorder() + " (harus " + (sample.length - 1) + ")");
        bst.delete(50);
        System.out.println("Setelah delete root (50), total node: " + bst.countInorder() + " (harus " + (sample.length - 2) + ")");
    }

    static void uji_HashFungsional() {
        header("UJI FUNGSIONAL - HashTable Mahasiswa (15 data)");
        HashTableManagement htm = new HashTableManagement();
        String[] nama = { "Andi","Budi","Citra","Dewi","Eka","Fajar","Gita","Hari",
                "Indah","Joko","Kiki","Lina","Maya","Nina","Oscar" };
        for (int i = 0; i < nama.length; i++) htm.addStudent(20240000 + i, nama[i]);
        System.out.println("Jumlah mahasiswa: " + htm.size());
        Student s = htm.searchStudent(20240005);
        System.out.println("Cari NIM 20240005 -> " + (s == null ? "TIDAK ADA" : s));
        System.out.println("Cari NIM 99999999 -> " + (htm.searchStudent(99999999) == null ? "TIDAK ADA (benar)" : "ADA (salah)"));
        boolean ok = htm.deleteStudent(20240003);
        System.out.println("Hapus NIM 20240003 -> " + (ok ? "berhasil" : "gagal"));
        System.out.println("Jumlah setelah hapus: " + htm.size());
    }

    static void uji_GraphFungsional() {
        header("UJI FUNGSIONAL - Graph Navigasi (5 node A..E)");
        NavigasiGraph g = new NavigasiGraph();
        g.tambahJalur("A", "B", 4);
        g.tambahJalur("A", "C", 2);
        g.tambahJalur("B", "C", 1);
        g.tambahJalur("B", "D", 5);
        g.tambahJalur("C", "D", 8);
        g.tambahJalur("C", "E", 10);
        g.tambahJalur("D", "E", 2);
        System.out.println("BFS reachable dari A: " + g.bfsCount("A") + " node");
        System.out.println("DFS reachable dari A: " + g.dfsCount("A") + " node");
        int d = g.dijkstra("A", "E");
        System.out.println("Dijkstra A -> E: jarak total = " + d + " (ekspektasi 10 via A-C-B-D-E: 2+1+5+2)");
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" QA TESTER - Analisis Performa Struktur Data");
        System.out.println(" Tim TK-W10-S29-R0  |  Anggota 5: QA & Analis Performa");
        System.out.println("=============================================================");

        // 1) Uji fungsional ringan untuk memastikan operasi benar
        uji_BSTFungsional();
        uji_HashFungsional();
        uji_GraphFungsional();

        // 2) Benchmark dengan beragam skala data
        header("BENCHMARK SKALA KECIL (sesuai instruksi 10-20 data)");
        int[] kecil = { 10, 20 };
        for (int n : kecil) {
            benchBST(n, 42);
            benchHash(n, 42);
            benchGraph(n, 42);
        }

        header("BENCHMARK SKALA MENENGAH (perbandingan pertumbuhan)");
        int[] menengah = { 100, 1000, 10_000 };
        for (int n : menengah) {
            benchBST(n, 42);
            benchHash(n, 42);
            benchGraph(n, 42);
        }

        header("RINGKASAN KOMPLEKSITAS TEORITIS");
        System.out.println("BST  insert/search/delete : O(log n) rata-rata, O(n) terburuk (skewed)");
        System.out.println("BST  inorder traversal    : O(n)");
        System.out.println("Hash add/search/delete    : O(1) rata-rata, O(n) terburuk (collision)");
        System.out.println("Graph BFS / DFS           : O(V + E)");
        System.out.println("Graph Dijkstra (PQ)       : O((V + E) log V)");
        System.out.println();
        System.out.println("Selesai. Salin output di atas ke laporan jika diperlukan.");
    }
}
