package btree

class BTree<K: Comparable<K>> (val t: Int) {
    var root: BNode<K>? = null

    fun insert(key: K) {
        if (root == null)
            root = BNode()

        if (root!!.keys.size == 2 * t - 1) {
            val newNode: BNode<K> = BNode(false)
            newNode.children.add(root!!)
            newNode.splitChildren(t, 0)
            root = newNode
            insertNonfull(key, newNode)
        }
        else
            insertNonfull(key, root!!)
    }

    private fun insertNonfull(key: K, node: BNode<K>) {
        var i = 0
        while (i < node.keys.size && key > node.keys[i])
            i++

        if (node.isLeaf)
            node.keys.add(i, key)
        else {
            if (node.children[i].keys.size == 2 * t - 1) {
                node.splitChildren(t, i)

                if (key > node.keys[i])
                    i++
            }
            insertNonfull(key, node.children[i])
        }
    }

    fun delete(key: K) {
        if (find(key) == null)
            return

        if (root == null)
            return

        deletePrivate(key, root!!)

        if (root!!.keys.size == 0)
            root = null
    }

    private fun deletePrivate(key: K, node: BNode<K>) {
        var i = 0
        while (i < node.keys.size && key > node.keys[i])
            i++

        if (node.keys[i] == key) {
            if ((node.isLeaf)) {
                node.keys.removeAt(i)
            } else if (node.children[i].keys.size > t - 1) {
                val prevNode = prevKey(key, node)
                node.keys[i] = prevNode.keys.last()
                deletePrivate(prevNode.keys.last(), node.children[i])
            } else if (node.children[i + 1].keys.size > t - 1) {
                val nextNode = nextKey(key, node)
                node.keys[i] = nextNode.keys.first()
                deletePrivate(nextNode.keys.first(), node.children[i + 1])
            } else {
                node.mergeChildren(i)

                if (node.keys.isEmpty()) {
                    root = node.children[i]
                }

                deletePrivate(key, node.children[i])
            }
        }
        else {
            if (node.children[i].keys.size < t) {
                when {
                    node.children[i] != node.children.last()
                            && node.children[i + 1].keys.size > t - 1 -> {
                        node.children[i].keys.add(node.keys[i])
                        node.keys[i] = node.children[i + 1].keys.first()
                        node.children[i + 1].keys.removeAt(0)

                        if (!node.children[i].isLeaf) {
                            node.children[i].children.add(node.children[i + 1].children.first())
                            node.children[i + 1].children.removeAt(0)
                        }
                    }

                    node.children[i] != node.children.first()
                            && node.children[i - 1].keys.size > t - 1 -> {
                        node.children[i].keys.add(0, node.keys[i - 1])
                        node.keys[i - 1] = node.children[i - 1].keys.last()
                        node.children[i - 1].keys.removeAt(node.children[i - 1].keys.size - 1)

                        if (!node.children[i].isLeaf) {
                            node.children[i].children.add(0, node.children[i - 1].children.last())
                            node.children[i - 1].children.removeAt(node.children[i - 1].children.size - 1)
                        }
                    }

                    node.children[i] != node.children.last() -> {
                        node.mergeChildren(i)

                        if (node.keys.isEmpty()) {
                            root = node.children[i]
                        }

                    }
                    node.children[i] != node.children.first() -> {
                        node.mergeChildren(i - 1)

                        if (node.keys.isEmpty()) {
                            root = node.children[i - 1]
                        }

                        i--
                    }
                }
            }
            deletePrivate(key, node.children[i])
        }
    }

    private fun prevKey(key: K, node: BNode<K>): BNode<K> {
        var currentNode = node.children[node.keys.indexOf(key)]

        while (!currentNode.isLeaf)
            currentNode = currentNode.children.last()

        return currentNode

    }

    private fun nextKey(key: K, node: BNode<K>): BNode<K> {
        var currentNode = node.children[node.keys.indexOf(key) + 1]

        while (!currentNode.isLeaf)
            currentNode = currentNode.children.first()

        return currentNode

    }

    fun find(key: K): K? {
        var node: BNode<K>? = root ?: return null

        var i = 0
        while (i < node!!.keys.size - 1 && key > node.keys[i])
            i++

        while (node!!.keys[i] != key) {
            if (node.isLeaf)
                return null

            when {
                key < node.keys[i] -> node = node.children[i]
                key > node.keys[i] -> node = node.children[i + 1]
            }

            i = 0
            while (i < node.keys.size - 1 && key > node.keys[i])
                i++
        }
        return key
    }
}