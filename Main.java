import java.util.Scanner;

// Main Controller
public class Main {

  static Scanner input = new Scanner(System.in);

  // Objek dari tiap modul
  static BinarySearchTree bst = new BinarySearchTree();
  static HashTableManagement htm = new HashTableManagement();
  static NavigasiGraph graph = new NavigasiGraph();

  public static void main(String[] args) {

    int pilihan;

    do {
      System.out.println("\n===== MENU UTAMA =====");
      System.out.println("1. Binary Search Tree");
      System.out.println("2. Hash Table Mahasiswa");
      System.out.println("3. Graph Navigasi");
      System.out.println("4. Exit");
      System.out.print("Pilih menu: ");

      pilihan = input.nextInt();

      switch (pilihan) {
        case 1:
          menuBST();
          break;

        case 2:
          menuHashTable();
          break;

        case 3:
          menuGraph();
          break;

        case 4:
          System.out.println("Program selesai.");
          break;

        default:
          System.out.println("Pilihan tidak valid!");
      }

    } while (pilihan != 4);
  }

  // Binary search tree menu
  public static void menuBST() {

    int pilih;

    do {
      System.out.println("\n=== MENU BST ===");
      System.out.println("1. Insert");
      System.out.println("2. Search");
      System.out.println("3. Traversal Inorder");
      System.out.println("4. Delete");
      System.out.println("5. Kembali");

      System.out.print("Pilih: ");
      pilih = input.nextInt();

      switch (pilih) {

        case 1:
          System.out.print("Masukkan angka: ");
          int data = input.nextInt();
          bst.insert(data);
          break;

        case 2:
          System.out.print("Cari angka: ");
          int cari = input.nextInt();

          if (bst.search(cari)) {
            System.out.println("Data ditemukan");
          } else {
            System.out.println("Data tidak ditemukan");
          }
          break;

        case 3:
          System.out.println("Traversal inorder:");
          bst.inorder();
          break;

        case 4:
          System.out.print("Hapus angka: ");
          int del = input.nextInt();
          bst.delete(del);
          System.out.println("Setelah mengahapus " + del + ": ");
          bst.inorder();
          break;
      }

    } while (pilih != 5);
  }

  // ================= HASH TABLE MENU =================
  public static void menuHashTable() {

    int pilih;

    do {
      System.out.println("\n=== MENU HASH TABLE ===");
      System.out.println("1. Tampilkan Data Mahasiswa");
      System.out.println("2. Tambah Mahasiswa");
      System.out.println("3. Cari Mahasiswa");
      System.out.println("4. Hapus Mahasiswa");
      System.out.println("5. Kembali");

      System.out.print("Pilih: ");
      pilih = input.nextInt();
      input.nextLine();

      switch (pilih) {
        case 1:
          System.out.println("Data mahasiswa awal:");
          htm.displayAllStudents();
          break;

        case 2:
          System.out.print("Masukkan NIM: ");
          int nim = input.nextInt();
          input.nextLine();
          System.out.print("Masukkan Nama: ");
          String nama = input.nextLine();

          htm.addStudent(nim, nama);
          break;

        case 3:
          System.out.print("Masukkan NIM: ");
          nim = input.nextInt();
          System.out.println("\nCari mahasiswa dengan ID " + nim + ":");
          Student s = htm.searchStudent(nim);
          if (s != null) {
            System.out.println("Ditemukan: " + s);
          } else {
            System.out.println("Mahasiswa tidak ditemukan.");
          }
          break;

        case 4:
          System.out.print("Masukkan NIM: ");
          nim = input.nextInt();
          if (htm.deleteStudent(nim)) {
            System.out.println("Penghapusan berhasil.");
          } else {
            System.out.println("Mahasiswa tidak ditemukan.");
          }
          break;
      }

    } while (pilih != 5);
  }

  // ================= GRAPH MENU =================
  public static void menuGraph() {

    int pilih;

    do {
      System.out.println("\n=== MENU GRAPH ===");
      System.out.println("1. Tambah Jalur");
      System.out.println("2. BFS");
      System.out.println("3. DFS");
      System.out.println("4. DIJKSTRA");
      System.out.println("5. Kembali");

      System.out.print("Pilih: ");
      pilih = input.nextInt();
      input.nextLine();

      switch (pilih) {

        case 1:
          System.out.print("Node awal: ");
          String from = input.nextLine();

          System.out.print("Node tujuan: ");
          String to = input.nextLine();

          System.out.print("Jarak: ");
          int jarak = input.nextInt();

          graph.tambahJalur(from, to, jarak);
          break;

        case 2:
          System.out.print("Mulai BFS dari: ");
          String startBFS = input.nextLine();

          graph.penelusuranBFS(startBFS);
          break;

        case 3:
          System.out.print("Mulai DFS dari: ");
          String startDFS = input.nextLine();

          graph.penelusuranDFS(startDFS);
          break;
        case 4:
          System.out.print("Node awal: ");
          String dari = input.nextLine();

          System.out.print("Node tujuan: ");
          String ke = input.nextLine();
          graph.cariJalurTerpendek(dari, ke);
          break;
      }

    } while (pilih != 5);
  }
}