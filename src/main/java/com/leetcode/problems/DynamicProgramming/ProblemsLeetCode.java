package com.leetcode.problems.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

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

    HashMap<String,Boolean> map = new HashMap<>();
    HashMap<String,Integer> wordCount = new HashMap<>();

    private boolean isSub( String s,  String word){

        int pointer = 0;
        for (int i=0;i<s.length();i++){
            if(word.charAt(pointer) == s.charAt(i)){
                pointer++;
                if (pointer == word.length()){
                    map.put(word, true);
                    return true;
                }

            }
        }
        return false;

    }

    //Problem 792
    public int numMatchingSubseq(String s, String[] words) {
        int count = 0;
         for (String word: words){
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
         }
        for (Map.Entry<String, Integer> entry: wordCount.entrySet()){
            if(isSub(s, entry.getKey()))
                count += entry.getValue();
        }

        return count;
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

    // Problem 294
    public boolean canWin(String s) {
    if (s == null || s.length() < 2) {
        return false;
    }
    HashMap<String, Boolean> winMap = new HashMap<String, Boolean>();
    return helper(s, winMap);
}

public boolean helper(String s, HashMap<String, Boolean> winMap) {
    if (winMap.containsKey(s)) {
        return winMap.get(s);
    }
    for (int i = 0; i < s.length() - 1; i++) {
        if (s.startsWith("++", i)) {
            String t = s.substring(0, i) + "--" + s.substring(i+2);
            if (!helper(t, winMap)) {
                winMap.put(s, true);
                return true;
            }
        }
    }
    winMap.put(s, false);
    return false;
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
