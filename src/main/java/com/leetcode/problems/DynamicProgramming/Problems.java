package com.leetcode.problems.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Problems {


    static List<List<Integer>> nestedList = Arrays.asList(
        Arrays.asList(2),
        Arrays.asList(3, 4),
        Arrays.asList(6, 5, 7),
        Arrays.asList(4, 1, 8, 3)
    );

    //Problem 125
    public int maxProduct(int[] nums) {

        int minSoFar = 1;
        int maxSoFar = 1;
        int result = nums[0];
        for(int number: nums){
            int tempMax = maxSoFar * number;
            int tempMin = minSoFar * number;
            maxSoFar = Math.max(number,Math.max(tempMax,tempMin));
            minSoFar = Math.min(number,Math.min(tempMax,tempMin));
            result = Math.max(result,maxSoFar);
        }

        return result;
    }


    //Problem 120 with memoization
    private int minTotalDP (int i, int j, int[][] dp, List<List<Integer>> triangle){
        if(i == dp.length-1)
            return triangle.get(i).get(j);

        if(dp[i][j] != -1)
            return dp[i][j];

        int goDown = triangle.get(i).get(j) + minTotalDP (i+1,j, dp, triangle);
        int goRight = triangle.get(i).get(j) + minTotalDP (i+1,j+1, dp, triangle);

        return dp[i][j] = Math.min(goDown,goRight);
    }

    public int minimumTotalwithDP(List<List<Integer>> triangle) {
        int rows = triangle.size();
        int columns = triangle.get(rows -1).size();
        int[][] dp = new int[rows][columns];
        //fill up the rows with the value -1, take the risk of recalculating
        //if thevalue is -1 in calculations but take the risk to improve performance
        for(int i=0;i<rows;i++)
            Arrays.fill(dp[i],-1);

        return minTotalDP (0,0, dp, triangle);

    }

    //Problem 120 not memoization
    public int  minimumTotal(List<List<Integer>> triangle) {
        //overwrtting the input

        int rows = triangle.size();
        for (int i = 1; i< rows;i++){
            for(int j=0;j<triangle.get(i).size();j++){
                int minAbove = Integer.MAX_VALUE;
                //get the one on diagonal to the left
                if(j>0)
                    minAbove = triangle.get(i-1).get(j-1);
                //get the one above and compare which is smaller
                if(j<i)
                    minAbove = Math.min(minAbove,triangle.get(i-1).get(j));

                int value = minAbove +  triangle.get(i).get(j);
                triangle.get(i).set(j,value);
            }
        }

        return Collections.min(triangle.get(rows-1));

    }

    public static void main(String[] args) {
        Problems problem = new Problems();
        List<String> words = new ArrayList<>();
        words.add("a");
        words.add("0");
        System.out.println(Collections.min(words));

        int[][] array = new int[9][9];
        for(int i=0;i<array.length;i++)
            Arrays.fill(array[i],-1);

        int[] array2 = array[0];
        array2[0] = 0;
        System.out.println(array[0][1]);


    }


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


}
