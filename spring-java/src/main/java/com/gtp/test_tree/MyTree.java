package com.gtp.test_tree;

/**
 * 二叉树是每个节点最多有两个子树的树结构
 * 
 * 1.左边节点都比右边节点小
 * @author gaotingping
 *
 * 2017年1月26日 上午10:57:51
 */
public class MyTree {

	public MyNode root = null;// 树根

	// 方法
	public void insert(int data) {

		MyNode newNode = new MyNode(data);

		if (root == null) {/* 树为空 */
			root = newNode;
		} else {
			// 树非空
			MyNode current = root;
			MyNode parent;

			// 寻找插入的位置
			while (true) {
				parent = current;
				if (data < current.data) {// 大于插入左边，小于插入右边
					current = current.left;
					if (current == null) {
						parent.left = newNode;
						return;
					}
				} else {
					current = current.right;
					if (current == null) {
						parent.right = newNode;
						return;
					}
				}
			}
		}
	}

	public MyNode find(int data) {

		MyNode current = root;

		if (current == null) {
			return null;
		}

		while (current.data != data) {

			if (current.data < data) {// 右边
				current = current.right;
			} else {
				current = current.left;
			}

			if (current == null) {// 没有找到
				return null;
			}

		}

		return current;
	}

	public boolean delete(int data) {

		// 先找到需要删除的节点
		MyNode current = root;
		MyNode parent = root;
		boolean isLeftChild = false;

		while (current.data != data)// 显然，当current.iData == key 时，current
									// 就是要找的节点
		{
			parent = current;
			if (data < current.data) {
				isLeftChild = true;
				current = current.left;
			} else {
				isLeftChild = false;
				current = current.right;
			}
			if (current == null)// 找不到key时返回false
				return false;
		}

		// 分情况考虑删除的节点
		// 删除的节点为叶节点时
		if (current.left == null && current.right == null) {
			if (current == root)
				root = null;
			else if (isLeftChild)
				parent.left = null;
			else
				parent.right = null;
		}
		// 删除的节点有一个子节点
		else if (current.right == null)// 删除的节点只有一个左子节点时
		{
			if (current == root)// 要删除的节点为根节点
				root = current.left;
			else if (isLeftChild)// 要删除的节点是一个左子节点
				parent.left = current.left;
			else
				parent.right = current.left;// 要删除的节点是一个右子节点
		} else if (current.left == null)// 删除的节点只有一个右子节点时
		{
			if (current == root)// 要删除的节点为根节点
				root = current.right;
			else if (isLeftChild)// 要删除的节点是一个左子节点
				parent.left = current.right;
			else
				parent.right = current.right;// 要删除的节点是一个右子节点
		}
		// 删除的节点有两个子节点
		else {
			MyNode successor = getSuccessor(current);// 找到后继节点
			if (current == root)
				root = successor;
			else if (isLeftChild)
				parent.left = successor;
			else
				parent.right = successor;
			successor.left = current.left;
		}

		return true;
	}

	// 返回后继节点
	private MyNode getSuccessor(MyNode delNode) {
		MyNode successorParent = delNode;// 后继节点的父节点
		MyNode successor = delNode;// 后继节点
		MyNode current = delNode.right;// 移动到位置节点位置
		while (current != null) {
			successorParent = successor;
			successor = current;
			current = current.left;
		}
		if (successor != delNode.right) {
			successorParent.left = successor.right;
			successor.right = delNode.right;
		}
		return successor;
	}

}
