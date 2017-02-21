package com.gtp.test_tree;

import org.junit.Test;

/**
 * 栈，队列，链表,树
 * 
 * 树的优点: 1.一种是有序数组:查找快 2.链表:删除快
 * 
 * 
 * 平衡二叉树（Balanced Binary Tree）又被称为AVL树（有别于AVL算法），且具有以下性质： 它是一
 * 棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 * 平衡二叉树的常用实现方法有红黑树、AVL、替罪羊树、Treap、伸展树等。 最小二叉平衡树的节点的公式 如下 F(n)=F(n-1)+F(n-2)+1
 * 这个类似于一个递归的数列，可以参考Fibonacci(斐波那契)数列， 1是根节点，F(n-1)是左子树的节点数量，F(n-2)是右子树的节点数量
 * 
 * 
 * 红黑树
 * 特点:它可以在O(log n)时间内做查找，插入和删除，这里的n 是树中元素的数目
 * 定义:自平衡二叉树
 * 
 * @author gaotingping
 *
 *         2017年1月26日 上午10:26:33
 */
public class Test1 {

	@Test
	public void test1() {

		MyTree root = getTree();

		xxBL(root.root);
		System.out.println();

		zxBL(root.root);
		System.out.println();

		hxBL(root.root);
	}

	@Test
	public void test2() {
		MyTree root = getTree();
		System.out.println(isBalanced(root.root));

	}

	/*
	 * 平衡二叉树的定义
	 * 1.左右子树高度相差不超过1
	 * 2.左右子树也是平衡二叉树
	 */
	private int getHeight(MyNode root) {
		
		if (root == null){
			return 0;
		}
		
		int depL = getHeight(root.left);
		
		int depR = getHeight(root.right);
		
		if (depL < 0 || depR < 0 || Math.abs(depL - depR) > 1){
			return -1; // 返回给该节点自己的value
		}else{
			return Math.max(depL, depR) + 1; // 返回给该节点自己的value
		}
	}

	public boolean isBalanced(MyNode root) {
		return (getHeight(root) >= 0);
	}

	// 顺序是按照跟节点来的

	/*
	 * 先序遍历:首先访问根，再先序遍历左（右）子树，最后先序遍历右（左）子树
	 * 
	 * 不递归遍历如何实现？:栈(递归的原理还是借助与栈)
	 */
	public void xxBL(MyNode node) {

		if (node == null) {
			return;
		}

		System.out.print(node.data + " ");

		if (node.left != null) {
			xxBL(node.left);
		}

		if (node.right != null) {
			xxBL(node.right);
		}
	}

	// 中序遍历:首先中序遍历左（右）子树，再访问根，最后中序遍历右（左）子树
	public void zxBL(MyNode node) {

		if (node == null) {
			return;
		}

		if (node.left != null) {
			xxBL(node.left);
		}

		System.out.print(node.data + " ");

		if (node.right != null) {
			xxBL(node.right);
		}
	}

	// 后续遍历:首先后序遍历左（右）子树，再后序遍历右（左）子树，最后访问根
	public void hxBL(MyNode node) {

		if (node == null) {
			return;
		}

		if (node.left != null) {
			xxBL(node.left);
		}

		if (node.right != null) {
			xxBL(node.right);
		}

		System.out.print(node.data + " ");
	}

	public MyTree getTree() {

		MyTree t = new MyTree();

		t.insert(5);
		t.insert(3);
		t.insert(7);

		return t;
	}
}
