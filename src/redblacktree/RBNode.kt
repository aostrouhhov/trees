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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as RBNode<*, *>

        if (key != other.key) return false
        if (value != other.value) return false
        if (parent != other.parent) return false
        if (colorBlack != other.colorBlack) return false
        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + colorBlack.hashCode()
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }
}