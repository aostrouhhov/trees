package binarysearchtree

import Tree

class Printer<K: Comparable<K>, V> {
    fun printTree(tree: Tree<K, V>) {
        printNode(node = (tree as BinarySearchTree<K, V>).root)
    }

    fun printNode(height: Int = 0, node: Node<K, V>?) {
        if (node == null)
            return

        printNode(height + 1, node.right)

        for (i in 1..height)
            print(" |")

        println("${node.key}"  + node.value)

        printNode(height + 1, node.left)
    }
}