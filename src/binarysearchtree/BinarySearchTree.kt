package binarysearchtree

import Tree

class BinarySearchTree<K: Comparable<K>, V>: Tree<K, V> {
    var root: Node<K, V>? = null

    override fun insert(key: K, value: V) {
        var father: Node<K, V>? = null
        var current: Node<K, V>? = root

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
            root = Node(key, value)
            return
        }

        if (key < father.key)
            father.left = Node(key, value, father)
        else
            father.right = Node(key, value, father)
    }

    override fun delete(key: K) {
        val delNode: Node<K, V> = findNode(key) ?: return

        val delParent: Node<K, V>? = delNode.parent

        if(delNode.left == null && delNode.right == null) {
            if (delNode.parent == null) {
                root = null
                return
            }

            if(delNode == delParent?.left)
                delParent.left = null

            if(delNode == delParent?.right)
                delParent.right = null
        }

        else if (delNode.left == null || delNode.right == null) {
            if (delNode.left == null) {
                if (delParent?.left == delNode)
                    delParent.left = delNode.right
                else
                    delParent?.right = delNode.right

                delNode.right?.parent = delParent
            }

            else {
                if (delParent?.left == delNode)
                    delParent.left = delNode.left
                else
                    delParent?.right = delNode.left

                delNode.left?.parent = delParent
            }
        }

        else {
            val successor: Node<K, V> = max(delNode.left)!!
            delNode.key = successor.key

            if (successor.parent?.left == successor) {
                successor.parent?.left = successor.right

                if (successor.right != null)
                    successor.right!!.parent = successor.parent
            }
            else {
                successor.parent?.right = successor.right

                if (successor.right != null)
                    successor.right!!.parent = successor.parent
            }
        }
    }

    override fun find(key: K): Pair<K, V>? {
        val result = findNode(key)

        if (result == null)
            return null
        else
            return Pair(result.key, result.value)
    }

    private fun findNode(key: K): Node<K, V>? {
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

    private fun max(rootNode: Node<K, V>?): Node<K, V>? {
        if (rootNode?.right == null)
            return rootNode
        else
            return max(rootNode.right)
    }
}