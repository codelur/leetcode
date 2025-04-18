package com.leetcode.problems.DynamicProgramming;

import java.util.Arrays;

public class Problems {

    //Problem 583
    public int minDistance(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();

        int length1 = word1.length();
        int length2 = word2.length();
        int[][] dp = new int[length1+1][length2+1];

        //fill first row
        for (int i = 1; i < dp[0].length; i++)
            dp[0][i] = i;

        //fill first column
        for(int j = 1; j < dp.length; j++)
            dp[j][0] = j;

        for (int i = 1; i < dp.length; i++)
            for (int j = 1; j < dp[0].length; j++){
                if(s1[i-1] == s2[j-1])
                    dp[i][j] = dp[i-1][j-1];
                else{
                    dp[i][j] = Math.min(dp[i][j-1] + 1, dp[i-1][j] + 1);
                }
            }

        return dp[length1][length2];
    }

    //Problem 562
    public int longestLine(int[][] mat) {
        //4 sides: going right, down, diagonal, reverse diagonal
        int[][][] dp = new int[4][mat.length][mat[0].length];
        int result = 0;
        for(int i=0;i<mat.length;i++){

            for(int j=0;j<mat[0].length;j++){

                if(mat[i][j]==1){
                    dp[0][i][j] = i==0 ? 1: dp[0][i-1][j] + 1;
                    dp[1][i][j] = j==0 ? 1: dp[1][i][j-1] + 1;
                    dp[2][i][j] = i==0 || j==0 ? 1 : dp[2][i-1][j-1] + 1;
                    dp[3][i][j] = i==0 || j == mat[0].length - 1 ? 1 : dp[3][i-1][j+1] + 1;
                    result = Math.max(result,
                        Math.max(Math.max(dp[0][i][j],dp[1][i][j]),
                                    Math.max(dp[2][i][j],dp[3][i][j])));
                }

            }
        }

        return result;
    }

    //Problem 45
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp,n);
        dp[0] = 0;
        for (int i=0;i<n-1;i++){
            int jumpsAtCurrent = nums[i];
            for(int j=1;j<=jumpsAtCurrent;j++){
                if(i+j>=n)
                    break;
                dp[i+j] = Math.min(dp[i+j],dp[i]+1);
            }
        }

        return dp[n-1];
    }

    public static void main(String[] args) {
        System.out.println("Hello Worldaaa!");
    }
}
