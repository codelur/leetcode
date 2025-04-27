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

    // Problem 161
    private boolean editRec(String s, String t, int indexS, int indexT, int changes){
        //end of String

        if(indexS == s.length() && indexT == t.length())
            return changes == 1? true : false;

        if( (s.length() - indexS == 1 && t.length() - indexT == 0 && changes == 0)
                ||  (s.length()- indexS == 0 && t.length() - indexT == 1 && changes == 0))
            return true;

        //end of only one of the strings
        if ((indexS != s.length() && indexT == t.length())
                || (indexS == s.length() && indexT != t.length()))
            return false;


        char sC = s.charAt(indexS);
        char tC = t.charAt(indexT);
        if(sC == tC){
           return editRec(s,  t,  indexS +1,  indexT+1,  changes);
        }else{
            if(changes == 1)
                return false;
            boolean replace = editRec(s,  t,  indexS +1,  indexT+1,  changes + 1);
            boolean addInT = editRec(s,  t,  indexS ,  indexT+1,  changes + 1);
            boolean addInS = editRec(s,  t,  indexS+1 ,  indexT,  changes + 1);

            return replace || addInS || addInT;
        }
    }

    public boolean isOneEditDistance(String s, String t) {
        return editRec( s,  t, 0, 0, 0);

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
