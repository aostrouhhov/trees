package btree

class BNode<K: Comparable<K>> (var isLeaf: Boolean = true) {
    var keys = ArrayList<K>()

    var children = ArrayList<BNode<K>>()

    fun splitChildren(t: Int, i: Int) {
        val splitNode = this.children[i]
        val newNode = BNode<K>(splitNode.isLeaf)

        for (j in 0..t - 2) {
            newNode.keys.add(splitNode.keys[t])
            splitNode.keys.removeAt(t)
        }

        if (!splitNode.isLeaf)
            for (j in 0..t - 1) {
                newNode.children.add(splitNode.children[t])
                splitNode.children.removeAt(t)
            }

        this.children.add(i + 1, newNode)
        this.keys.add(i, splitNode.keys[t - 1])
        splitNode.keys.removeAt(t - 1)
    }

    fun mergeChildren(i: Int) {
        val left = children[i]
        val right = children[i + 1]
        val key = keys[i]

        left.keys.add(key)
        left.keys.addAll(right.keys)

        if (!right.isLeaf)
            left.children.addAll(right.children)

        keys.removeAt(i)
        children.removeAt(i + 1)
    }
}