package redblacktree

import Tree

class RBPrinter<K: Comparable<K>, V> {
    fun printTree(tree: Tree<K, V>) {
        printNode(node = (tree as RedBlackTree<K, V>).root)
    }

    fun printNode(height: Int = 0, node: RBNode<K, V>?) {
        if (node == null)
            return

        printNode(height + 1, node.right)

        for (i in 1..height)
            print(" |")

        if (!node.colorBlack)
            println(27.toChar() + "[31m${node.key}" + node.value + 27.toChar() + "[0m")
        else
            println(27.toChar() + "[30m${node.key}" + node.value + 27.toChar() + "[0m")

        printNode(height + 1, node.left)
    }
}