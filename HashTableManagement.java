import java.util.HashMap;

class Student {
  int id;
  String name;

  // Constructor
  public Student(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "Student ID: " + id + ", Name: " + name;
  }
}

public class HashTableManagement {
  // HashMap untuk menyimpan data mahasiswa, key adalah ID
  private HashMap<Integer, Student> studentMap;

  public HashTableManagement() {
    studentMap = new HashMap<>();
  }

  // Menambahkan data mahasiswa ke HashMap
  public void addStudent(int id, String name) {
    Student student = new Student(id, name);
    studentMap.put(id, student);
  }

  // Mencari mahasiswa berdasarkan ID
  public Student searchStudent(int id) {
    return studentMap.get(id);
  }

  // Menghapus mahasiswa berdasarkan ID
  public boolean deleteStudent(int id) {
    if (studentMap.containsKey(id)) {
      studentMap.remove(id);
      return true;
    }
    return false;
  }

  // Menampilkan semua mahasiswa
  public void displayAllStudents() {
    if (studentMap.isEmpty()) {
      System.out.println("Tidak ada data mahasiswa.");
    } else {
      for (Integer id : studentMap.keySet()) {
        System.out.println(studentMap.get(id));
      }
    }
  }

  public static void main(String[] args) {
    HashTableManagement htm = new HashTableManagement();

    // Menambahkan data mahasiswa
    htm.addStudent(101, "Andi");
    htm.addStudent(102, "Budi");
    htm.addStudent(103, "Citra");

    System.out.println("Data mahasiswa awal:");
    htm.displayAllStudents();

    // Mencari mahasiswa
    System.out.println("\nCari mahasiswa dengan ID 102:");
    Student s = htm.searchStudent(102);
    if (s != null) {
      System.out.println("Ditemukan: " + s);
    } else {
      System.out.println("Mahasiswa tidak ditemukan.");
    }

    // Menghapus mahasiswa
    System.out.println("\nMenghapus mahasiswa dengan ID 101:");
    if (htm.deleteStudent(101)) {
      System.out.println("Penghapusan berhasil.");
    } else {
      System.out.println("Mahasiswa tidak ditemukan.");
    }

    System.out.println("\nData mahasiswa setelah penghapusan:");
    htm.displayAllStudents();
  }
}