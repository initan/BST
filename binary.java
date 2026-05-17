 //menyimpan data pada BST 
class Node { 
    int data;
    Node left, right;
    //constructor
    public Node (int data){
        this.data = data;
        left = right = null;
    }
}
//kelas BST
class BinarySearchTree {
    Node root;
    
    //insert (menyisipkan)
    public void insert (int data){
        root = insertRecursive(root, data);
    }
    private Node insertRecursive (Node root, int data){
        if (root == null){
            root = new Node (data);
            return root;
        }
        if (data < root.data){
            root.left = insertRecursive (root.left, data);
        }
        else if (data > root.data){
            root.right = insertRecursive (root.right, data);
        }
        return root;
    }
    //search (mencari)
    public boolean search (int data){
        return searchRecursive (root, data);
    }
    private boolean searchRecursive (Node root, int data){
        if (root == null){
            return false;
        }
        if (root.data == data){
            return true;
        }
        //cari ke kiri atau ke kanan
        if (data < root.data){
            return searchRecursive (root.left, data);
        } else {
            return searchRecursive (root.right, data);
        }
    }
    //delete (menghapus)
    public void delete (int data){
        root = deleteRecursive(root, data);
    }
    private Node deleteRecursive (Node root, int data){
        if (root == null){
            return null;
        }
        
        //mencari node yang akan dihapus
        if (data < root.data){
            root.left = deleteRecursive (root.left, data);
        } else if (data > root.data){
            root.right = deleteRecursive (root.right, data);
        } else {
            //node tidak punya anak
            if (root.left == null && root.right == null){
                return null;
            }
            //node punya satu anak
            if (root.left == null){
                return root.right;
            }
            if (root.right == null){
                return root.left;
            }
            //node punya dua anak
            root.data = minValue (root.right);
            //hapus node pengganti
            root.right = deleteRecursive (root.right, root.data);
        }
        return root;
    }
    //mencari nilai terkecil
    private int minValue (Node root){
        int min = root.data;
        while (root.left != null){
            min = root.left.data;
            root = root.left;
        }
        return min;
    }
    //menampilkan data dengan urut
    public void inorder (){
        inorderRecursive(root);
        System.out.println();
    }
    private void inorderRecursive (Node root){
        if (root != null){
            inorderRecursive (root.left);
            System.out.println(root.data + " ");
            inorderRecursive (root.right);
        }
    }
}
public class Main {
    public static void main (String[]args){
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);
              
        System.out.println("Inorder Traversal : ");
        bst.inorder();
              
        System.out.println("Cari 40 : " + bst.search(40));
        System.out.println("Cari 90 : " + bst.search(90));
        
        bst.delete(30);
        
        System.out.println("Setelah mengahapus 30 : ");
        bst.inorder();
    }
}
