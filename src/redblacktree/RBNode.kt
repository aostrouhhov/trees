package redblacktree

class RBNode<K: Comparable<K>, V>(var key: K, var value: V, var parent: RBNode<K, V>? = null, var colorBlack: Boolean = false) {
    var left: RBNode<K, V>? = null

    var right: RBNode<K, V>? = null

    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)

    fun brother(): RBNode<K, V>? {
        if (this == this.parent?.left)
            return this.parent!!.right

        return this.parent?.left
    }

    fun rotateLeft() {
        val rightChild = this.right ?: return
        val dad = this.parent

        this.swapColors(rightChild)
        rightChild.left?.parent = this
        this.right = rightChild.left
        rightChild.left = this

        when {
            this == dad?.left -> dad.left = rightChild
            this == dad?.right -> dad.right = rightChild
        }

        this.parent = rightChild
        rightChild.parent = dad
    }

    fun rotateRight() {
        val leftChild = this.left ?: return
        val dad = this.parent

        this.swapColors(leftChild)
        leftChild.right?.parent = this
        this.left = leftChild.right
        leftChild.right = this

        when {
            this == dad?.left -> dad.left = leftChild
            this == dad?.right -> dad.right = leftChild
        }

        this.parent = leftChild
        leftChild.parent = dad
    }

    private fun swapColors(node2: RBNode<K, V>?) {
        val node1color = this.colorBlack

        if (node2 != null) {
            this.colorBlack = node2.colorBlack
            node2.colorBlack = node1color
        }
    }
}