package Model;

public class AVLTree {
    private int size;
    private AVLNode root;

    public AVLTree() {
        this.size = 0;
        this.root = null;
    }

    public int height(AVLNode node) {
        return node == null ? 0 : node.getHeight();
    }

    public AVLNode search(Word word) {
        return search(this.root, word);
    }
    private AVLNode search(AVLNode node, Word word) {
        if (node == null) {
            return null;
        }
        if (node.equal(word)) {
            return node;
        } else if (node.lessThan(word)) {
            return search(node.getRight(), word);
        } else {
            return search(node.getLeft(), word);
        }
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    public boolean delete(Word word) {
        int oldSize = this.size;
        delete(this.root, word);
        if (this.size < oldSize) {
            return true;
        }
        return false;
    }
    private AVLNode delete(AVLNode root, Word word) {
        // STEP 1: PERFORM STANDARD BST DELETE

        if (root == null)
            return null;

        // If the value to be deleted is smaller than the root's value,
        // then it lies in left subtree
        if ( root.greaterThan(word) )
            root.setLeft(delete(root.getLeft(), word));

            // If the value to be deleted is greater than the root's value,
            // then it lies in right subtree
        else if( root.lessThan(word) )
            root.setRight(delete(root.getRight(), word));

            // if value is same as root's value, then This is the node
            // to be deleted
        else {
            // node with only one child or no child
            if( (root.getLeft() == null) || (root.getRight() == null) ) {

                AVLNode temp;
                if (root.getLeft() != null)
                    temp = root.getLeft();
                else
                    temp = root.getRight();

                // No child case
                if(temp == null) {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of the non-empty child

                temp = null;
            }
            else {
                // node with two children: Get the inorder successor (smallest
                // in the right subtree)
                AVLNode temp = minValueNode(root.getRight());

                // Copy the inorder successor's data to this node
                root.setWord(temp.getWord());

                // Delete the inorder successor
                root.setRight(delete(root.getRight(), temp.getWord()));
            }
            this.size--;
        }

        // If the tree had only one node then return
        if (root == null) {
            return null;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight(max(height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotation(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotation(root.getLeft()));
            return rightRotation(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotation(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotation(root.getRight()));
            return leftRotation(root);
        }

        return root;
    }

    public boolean add(Word word) {
        int oldSize = this.size;
        this.root = add(this.root, word);
        if (this.size > oldSize) {
            return true;
        }
        return false;
    }
    private AVLNode add(AVLNode node, Word word) {
        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            this.size++;
            return(new AVLNode(word));
        }

        if (node.equal(word)) {
            return null;
        }

        if (node.greaterThan(word))
            node.setLeft(add(node.getLeft(), word));
        else if (node.lessThan(word))
            node.setRight(add(node.getRight(), word));

        /* 2. Update height of this ancestor node */
        node.setHeight(max(height(node.getLeft()), height(node.getRight())) + 1);

        /* 3. Get the balance factor of this ancestor node to check whether
           this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && node.getLeft().greaterThan(word))
            return rightRotation(node);

        // Right Right Case
        if (balance < -1 && node.getRight().lessThan(word))
            return leftRotation(node);

        // Left Right Case
        if (balance > 1 && node.getLeft().lessThan(word))
        {
            node.setLeft(leftRotation(node.getLeft()));
            return rightRotation(node);
        }

        // Right Left Case
        if (balance < -1 && node.getRight().greaterThan(word))
        {
            node.setRight(rightRotation(node.getRight()));
            return leftRotation(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }


    private int max(int h1, int h2) {
        return h1 > h2? h1 : h2;
    }

    private int getBalance(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode leftRotation(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode T2 = y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        //  Update heights
        x.setHeight(max(height(x.getLeft()), height(x.getRight()))+1);
        y.setHeight(max(height(y.getLeft()), height(y.getRight()))+1);

        // Return new root
        return y;
    }

    private AVLNode rightRotation(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode T2 = x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        // Update heights
        y.setHeight(max(height(y.getLeft()), height(y.getRight()))+1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight()))+1);

        // Return new root
        return x;
    }

    public AVLNode getRoot() {
        return root;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }

    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void inOrder(AVLNode root) {
        if (root != null) {
            inOrder(root.getLeft());
            System.out.println(root.getWord().getInfo());
            inOrder(root.getRight());
        }
    }

}
