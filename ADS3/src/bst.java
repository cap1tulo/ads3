import java.util.*;

public class bst<Key extends Comparable<Key>, Value> {
    private TreeNode root;
    private int nodeCount;

    private class TreeNode {
        Key key;
        Value value;
        TreeNode left, right;

        public TreeNode(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public void insert(Key key, Value value) {
        root = insert(root, key, value);
        nodeCount++;
    }

    private TreeNode insert(TreeNode node, Key key, Value value) {
        if (node == null) return new TreeNode(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(node.left, key, value);
        else if (cmp > 0) node.right = insert(node.right, key, value);
        else node.value = value;
        return node;
    }

    public Value search(Key key) {
        TreeNode node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node.value;
        }
        return null;
    }

    public void remove(Key key) {
        root = remove(root, key);
        nodeCount--;
    }

    private TreeNode remove(TreeNode node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = remove(node.left, key);
        else if (cmp > 0) node.right = remove(node.right, key);
        else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            TreeNode temp = node;
            node = min(temp.right);
            node.right = removeMin(temp.right);
            node.left = temp.left;
        }
        return node;
    }

    private TreeNode min(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private TreeNode removeMin(TreeNode node) {
        if (node.left == null) return node.right;
        node.left = removeMin(node.left);
        return node;
    }

    public Iterable<Key> traverse() {
        List<Key> keys = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            keys.add(current.key);
            current = current.right;
        }

        return keys;
    }

    public int treeSize() {
        return nodeCount;
    }

    public static void main(String[] args) {
        bst<Integer, String> bst = new bst<>();
        bst.insert(5, "Five");
        bst.insert(3, "Three");
        bst.insert(7, "Seven");
        bst.insert(2, "Two");
        bst.insert(4, "Four");
        bst.insert(6, "Six");
        bst.insert(8, "Eight");

        System.out.println("Tree Size: " + bst.treeSize());

        System.out.println("Keys in order:");
        for (Integer key : bst.traverse()) {
            System.out.println("Key: " + key + ", Value: " + bst.search(key));
        }
    }
}
