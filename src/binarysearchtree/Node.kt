package binarysearchtree

class Node<K: Comparable<K>, V>(var key: K, var value: V, var parent: Node<K, V>? = null) {
    var left: Node<K, V>? = null

    var right: Node<K, V>? = null
}