package redblacktree

import Tree

class RedBlackTree<K: Comparable<K>, V>: Tree<K, V>, Iterable<Pair<K, V>> {
    var root: RBNode<K, V>? = null

    override fun insert(key: K, value: V) {
        var father: RBNode<K, V>? = null
        var current: RBNode<K, V>? = root

        while (current != null) {
            father = current

            when {
                key < current.key -> current = current.left

                key > current.key -> current = current.right

                key == current.key -> {
                    current.value = value
                    return
                }
            }
        }

        if (father == null) {
            root = RBNode(key, value, father, true)
            return
        }

        if (key < father.key) {
            father.left = RBNode(key, value, father, false)
            insertFixup(father.left)
        }
        else {
            father.right = RBNode(key, value, father, false)
            insertFixup(father.right)
        }
    }

    private fun insertFixup(node: RBNode<K, V>?) {
        var uncle: RBNode<K, V>?
        var current: RBNode<K, V>? = node

        while (current?.parent?.colorBlack == false) {
            if (current.parent == current.parent?.parent?.left) {
                uncle = current.parent?.parent?.right

                when {
                    uncle?.colorBlack == false -> {
                        current.parent?.colorBlack = true
                        uncle.colorBlack = true
                        current.parent?.parent?.colorBlack = false
                        current = current.parent?.parent
                    }

                    current == current.parent?.right -> {
                        current = current.parent
                        if (current!!.parent!!.parent?.parent == null) root = current.parent
                        current.rotateLeft()
                    }

                    current == current.parent?.left -> {
                        if (current.parent?.parent?.parent == null) root = current.parent
                        current.parent?.parent?.rotateRight()
                    }
                }
            }

            else {
                uncle = current.parent?.parent?.left

                when {
                    uncle?.colorBlack == false -> {
                        current.parent?.colorBlack = true
                        uncle.colorBlack = true
                        current.parent?.parent?.colorBlack = false
                        current = current.parent?.parent
                    }

                    current == current.parent?.left -> {
                        current = current.parent
                        if (current?.parent?.parent?.parent == null) root = current!!.parent
                        current.rotateRight()
                    }

                    current == current.parent?.right -> {
                        if (current.parent?.parent?.parent == null) root = current.parent
                        current.parent?.parent?.rotateLeft()
                    }
                }
            }
        }
        root?.colorBlack = true
    }

    override fun find(key: K): Pair<K, V>? {
        val result = findNode(key)

        if (result == null)
            return null
        else
            return Pair(result.key, result.value)
    }

    private fun findNode(key: K): RBNode<K, V>? {
        var current = root

        while (current != null ) {
            if (key == current.key)
                return current

            if (key < current.key)
                current = current.left
            else
                current = current.right
        }
        return null
    }

    override fun delete(key: K) {
        val node = findNode(key) ?: return

        deleteNode(node)
    }

    private fun deleteNode(node: RBNode<K, V>) {
        val prev = max(node.left)

        when {
            (node.right != null && node.left != null) -> {
                node.key = prev!!.key
                node.value = prev.value
                deleteNode(prev)
                return
            }

            (node == root && node.isLeaf()) -> {
                root = null
                return
            }

            (!node.colorBlack && node.isLeaf()) -> {
                if (node == node.parent!!.left)
                    node.parent!!.left = null
                else
                    node.parent!!.right = null

                return
            }

            (node.colorBlack && node.left != null && !node.left!!.colorBlack) -> {
                node.key = node.left!!.key
                node.value = node.left!!.value
                node.left = null
                return
            }

            (node.colorBlack && node.right != null && !node.right!!.colorBlack) -> {
                node.key = node.right!!.key
                node.value = node.right!!.value
                node.right = null
                return
            }

            else -> {
                deleteCase1(node)
            }
        }

        if (node == node.parent!!.left)
            node.parent!!.left = null
        else
            node.parent!!.right = null
    }

    private fun deleteCase1(node: RBNode<K, V>) {
        if (node.parent != null)
            deleteCase2(node)
    }

    private fun deleteCase2(node: RBNode<K, V>) {
        val brother = node.brother()

        if (!brother!!.colorBlack) {
            if (node == node.parent!!.left)
                node.parent!!.rotateLeft()
            else if (node == node.parent!!.right)
                node.parent!!.rotateRight()

            if (root == node.parent)
                root = node.parent!!.parent
        }
        deleteCase3(node)
    }

    private fun deleteCase3(node: RBNode<K, V>) {
        val brother = node.brother()

        val a: Boolean = brother!!.left == null || brother.left!!.colorBlack
        val b: Boolean = brother.right == null || brother.right!!.colorBlack

        if (a && b && brother.colorBlack && node.parent!!.colorBlack) {
            brother.colorBlack = false
            deleteCase1(node.parent!!)
        }
        else
            deleteCase4(node)
    }

    private fun deleteCase4(node: RBNode<K, V>) {
        val brother = node.brother()

        val a: Boolean = brother!!.left == null || brother.left!!.colorBlack
        val b: Boolean = brother.right == null || brother.right!!.colorBlack

        if (a && b && brother.colorBlack && !node.parent!!.colorBlack) {
            brother.colorBlack = false
            node.parent!!.colorBlack = true
        }
        else
            deleteCase5(node)
    }

    private fun deleteCase5(node: RBNode<K, V>) {
        val brother = node.brother()

        val a: Boolean = brother!!.left == null || brother.left!!.colorBlack
        val b: Boolean = brother.right == null || brother.right!!.colorBlack

        if (brother.colorBlack) {
            if (brother.left?.colorBlack == false && b && node == node.parent?.left)
                brother.rotateRight()

            else if (brother.right?.colorBlack == false && a && node == node.parent?.right)
                brother.rotateLeft()
        }
        deleteCase6(node)
    }

    private fun deleteCase6(node: RBNode<K, V>) {
        val brother = node.brother()

        if (node == node.parent!!.left) {
            brother?.right?.colorBlack = true
            node.parent?.rotateLeft()
        }
        else {
            brother?.left?.colorBlack = true
            node.parent?.rotateRight()
        }

        if (root == node.parent)
            root = node.parent!!.parent
    }

    override fun iterator(): Iterator<Pair<K, V>> {
        return (object: Iterator<Pair<K, V>> {
            var node = max(root)
            var next = max(root)
            val last = min(root)

            override fun hasNext(): Boolean {
                return node != null && node!!.key >= last!!.key
            }

            override fun next(): Pair<K, V> {
                next = node
                node = nextSmaller(node)
                return Pair(next!!.key, next!!.value)
            }
        })
    }

    private fun nextSmaller(node: RBNode<K, V>?): RBNode<K, V>? {
        var smaller = node ?: return null

        if (smaller.left != null)
            return max(smaller.left!!)

        else if (smaller == smaller.parent?.left) {
            while (smaller == smaller.parent?.left)
                smaller = smaller.parent!!
        }
        return smaller.parent
    }

    private fun min(node: RBNode<K, V>?): RBNode<K, V>? {
        if (node?.left == null)
            return node
        else
            return min(node.left)
    }

    private fun max(node: RBNode<K, V>?): RBNode<K, V>? {
        if (node?.right == null)
            return node
        else
            return max(node.right)
    }
}