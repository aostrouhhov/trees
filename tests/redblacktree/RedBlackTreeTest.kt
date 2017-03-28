package redblacktree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RedBlackTreeTest {
    val RBTree = RedBlackTree<Int, Int>()
    var maxBlackHeight = -1

    fun checkStructure(node: RBNode<Int, Int>? = RBTree.root, blackHeight: Int = 1): Boolean {
        if (node == null)
            return true

        if (node.isLeaf()) {
            if (maxBlackHeight == -1)
                maxBlackHeight = blackHeight

            if (maxBlackHeight != blackHeight)
                return false

            return true
        }

        if (!node.colorBlack) {

            if (node.left?.colorBlack == false || node.right?.colorBlack == false)
                return false
            else
                return checkStructure(node.left, blackHeight + 1) && checkStructure(node.right, blackHeight + 1)
        }
        else {
            val res1: Boolean
            val res2: Boolean

            if (node.left?.colorBlack == true)
                res1 = checkStructure(node.left, blackHeight + 1)
            else
                res1 = checkStructure(node.left, blackHeight)

            if (node.right?.colorBlack == true)
                res2 = checkStructure(node.right, blackHeight + 1)
            else
                res2 = checkStructure(node.right, blackHeight)

            return res1 && res2
        }
    }

    @Test
    fun findExistentKey() {
        for (i in 1..100)
            RBTree.insert(i, i)

        for (i in 1..100)
            assertEquals(RBTree.find(i), Pair(i, i))
    }

    @Test
    fun findNonexistentKey() {
        for (i in 1..100)
            RBTree.insert(i, i)

        assertNull(RBTree.find(0))
        assertNull(RBTree.find(101))
    }

    @Test
    fun insertNormal() {
        for (i in 1..100)
            RBTree.insert(i, i)

        for (i in 1..100)
            assertNotNull(RBTree.find(i))
    }

    @Test
    fun insertSaveStructure() {
        for (i in 1..100) {
            RBTree.insert(i, i)
            maxBlackHeight = -1
            assertTrue(checkStructure())
        }
    }

    @Test
    fun insertReverseSaveStructure() {
        for (i in 100 downTo 1) {
            RBTree.insert(i, i)
            maxBlackHeight = -1
            assertTrue(checkStructure())
        }
    }

    @Test
    fun rootColorAfterInsert() {
        for (i in 1..100) {
            RBTree.insert(i, i)
            assertEquals(RBTree.root?.colorBlack, true)
        }
    }

    @Test
    fun deleteNormal() {
        RBTree.insert(1, 1)
        RBTree.delete(1)
        assertNull(RBTree.root)

        for (i in 1..100)
            RBTree.insert(i, i)

        for (i in 30..60)
            RBTree.delete(i)

        for (i in 1..100) {
            if (i in 30..60)
                assertFalse(Pair(i, i) in RBTree)
            else
                assertTrue(Pair(i, i) in RBTree)
        }
    }

    @Test
    fun deleteNonexistentKey() {
        for (i in 1..49)
            RBTree.insert(i, i)

        for (i in 51..100)
            RBTree.insert(i, i)

        RBTree.delete(50)

        for (i in 1..100) {
            if (i == 50)
                assertFalse(Pair(i, i) in RBTree)
            else
                assertTrue(Pair(i, i) in RBTree)
        }
    }

    @Test
    fun deleteSaveStructure() {
        for (i in 1..100)
            RBTree.insert(i, i)

        for (i in 1..100) {
            RBTree.delete(i)
            maxBlackHeight = -1
            assertTrue(checkStructure())
        }
    }

    @Test
    fun deleteReverseSaveStructure() {
        for (i in 1..100)
            RBTree.insert(i, i)

        for (i in 100 downTo 1) {
            RBTree.delete(i)
            maxBlackHeight = -1
            assertTrue(checkStructure())
        }
    }

    @Test
    fun rootColorAfterDelete() {
        for (i in 1..100)
            RBTree.insert(i, i)

        for (i in 30..60) {
            RBTree.delete(i)
            assertEquals(RBTree.root!!.colorBlack, true)
        }
    }

    @Test
    fun iterateEmptyTree() {
        for (i in RBTree)
            assertEquals(1, 2)
    }

    @Test
    fun iterateNormalTree() {
        for (i in 1..100)
            RBTree.insert(i, i)

        var a: Int = 101

        for (i in RBTree) {
            a--
            assertEquals(i, Pair(a, a))
        }
    }
}