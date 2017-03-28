package redblacktree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RBNodeTest {
    var node: RBNode<Int, Int>? = null
    var nodeCheck: RBNode<Int, Int>? = null
    val RBTree = RedBlackTree<Int, Int>()

    fun checkStructure(rootFirst: RBNode<Int, Int>?, rootSecond: RBNode<Int, Int>?): Boolean {
        if (rootFirst == null)
            return rootFirst == rootSecond

        if (rootFirst.key != rootSecond?.key || rootFirst.colorBlack != rootSecond.colorBlack)
            return false
        else
            return checkStructure(rootFirst.left, rootSecond.left) && checkStructure(rootFirst.right, rootSecond.right)
     }

    @Test
    fun rotateLeftOne() {
        node = RBNode(1, 1, null)
        nodeCheck = RBNode(1, 1, null)
        node?.rotateLeft()
        assertNotNull(node)
        assertTrue(checkStructure(node, nodeCheck))
    }

    @Test
    fun rotateRightOne() {
        node = RBNode(1, 1, null)
        nodeCheck = RBNode(1, 1, null)
        node?.rotateRight()
        assertNotNull(node)
        assertTrue(checkStructure(node, nodeCheck))
    }

    @Test
    fun rotateLeftLeaf() {
        node = RBNode(1, 1, null)
        node!!.colorBlack = true
        node!!.left = RBNode(-1, 1, node)
        nodeCheck = RBNode(1, 1, null)
        nodeCheck!!.colorBlack = true
        nodeCheck!!.left = RBNode(-1, 1, nodeCheck)
        node!!.left!!.rotateLeft()
        assertTrue(checkStructure(node, nodeCheck))
    }

    @Test
    fun rotateRightLeaf() {
        node = RBNode(1, 1, null)
        node!!.colorBlack = true
        node!!.left = RBNode(-1, 1, node)
        nodeCheck = RBNode(1, 1, null)
        nodeCheck!!.colorBlack = true
        nodeCheck!!.left = RBNode(-1, 1, nodeCheck)
        node!!.left!!.rotateRight()
        assertTrue(checkStructure(node, nodeCheck))
    }

    @Test
    fun RotateLeftNormal() {
        for (i in 1..100)
            RBTree.insert(i, i)

        node = RBTree.root!!.right
        RBTree.root!!.rotateLeft()
        assertEquals(RBTree.root!!.parent, node)
        assertEquals(RBTree.root, node?.left)
    }

    @Test
    fun RotateRightNormal() {
        for (i in 1..100)
            RBTree.insert(i, i)

        node = RBTree.root!!.left
        RBTree.root!!.rotateRight()
        assertEquals(RBTree.root!!.parent, node)
        assertEquals(RBTree.root, node?.right)
    }
}