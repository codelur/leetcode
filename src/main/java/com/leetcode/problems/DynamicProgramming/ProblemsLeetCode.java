package com.leetcode.problems.DynamicProgramming;

import javax.swing.tree.TreeNode;

public class ProblemsLeetCode {

    public class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode() {}
             TreeNode(int val) { this.val = val; }
             TreeNode(int val, TreeNode left, TreeNode right) {
                 this.val = val;
                 this.left = left;
                 this.right = right;
             }
         }

    //Problem 129
    int sumTotal = 0;

    private void sumToLeaf (TreeNode root, int sum){
        int crrSum = sum + root.val;

        if(root.left == null && root.right == null) sumTotal +=crrSum;
        if(root.left != null) sumToLeaf ( root.left, 10 * crrSum);
        if(root.right != null) sumToLeaf ( root.right, 10 * crrSum);
    }
    public int sumNumbers(TreeNode root) {
        sumToLeaf ( root, 0);

        return sumTotal;

    }
}
