package com.leetcode.problems.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import jdk.internal.net.http.common.Pair;

public class Problems {


    public static void main(String[] args) {

        Problems problem = new Problems();
        int[] heights = new int[]{1,2,3,4,3,1};
        System.out.println(problem.calculateMaxSquare(heights));

    }

    static List<List<Integer>> nestedList = Arrays.asList(
        Arrays.asList(2),
        Arrays.asList(3, 4),
        Arrays.asList(6, 5, 7),

        Arrays.asList(4, 1, 8, 3)
    );

    //Problem 219
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++){
            int num = nums[i];
            if(map.containsKey(num)){
                if (Math.abs(map.get(num) - i)<=k)
                    return true;
                else
                    map.put(num,i);
            }else{
                map.put(num,i);
            }
        }

        return false;
    }

    //Problem 787
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] prev = new int[n];
        Arrays.fill(prev, Integer.MAX_VALUE);
        prev[src] = 0;

        for (int i=0;i<k+1;i++){
            int[] curr = Arrays.copyOf(prev, n);
            for(int[] flight: flights){
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];
                if(prev[from]!=Integer.MAX_VALUE){
                    curr[to] = Math.min(curr[to], prev[from] + price);
                }
            }
            prev = curr;
        }

        return prev[dst] == Integer.MAX_VALUE? -1 : prev[dst];
    }

    //Problem 418
    public int wordsTyping(String[] sentence, int rows, int cols) {

        StringBuffer sb = new StringBuffer();
        for(String word: sentence){
            sb.append(word);
            sb.append(" ");
        }
        int sentenceLength = sb.length();

        int cursor = 0;
        for (int i = 0;i<rows;i++){
            cursor += cols;
            if(sb.charAt(cursor % sentenceLength) != ' '){
                while(cursor >0 && sb.charAt(cursor % sentenceLength) != ' ')
                    cursor--;
            }
            cursor++;
        }
        return cursor / sentenceLength;
    }
    Pair<Integer,Integer> pair = new Pair<>(3,3);


    //Problem Code
    private int calculateMaxSquare(int[] heights){
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i < n; i++) {
            int minHeight = heights[i];
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                int width = j - i + 1;
                int side = Math.min(minHeight, width);
                maxArea = Math.max(maxArea, side * side);
            }
        }

        return maxArea;
    }



    //Problem 518
    private int changeDP (int i,int j, int[] coins, int[][] dp){

        if(i==0)
            return 1;

        if (dp[i][j]!=-1)
            return dp[i][j];

        int useCoin = i - coins[j]<0? 0 : changeDP (i - coins[j],j,coins, dp);
        int notUseCoin =  j == 0?0:changeDP (i,j - 1,coins, dp);

        dp[i][j] = useCoin + notUseCoin;
        return dp[i][j];
    }

    public int change(int amount, int[] coins) {
        int[][] dp = new int[amount + 1][coins.length];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        int result =  changeDP (amount, coins.length - 1 , coins, dp);

        return result;
    }

    //Problem 681
    public String nextClosestTime(String time) {

        HashSet<Integer> clockDigits = new HashSet<>();
        for (char c: time.toCharArray()){
             clockDigits.add(c-'0');
        }

        int timeInSeconds = Integer.parseInt(time.substring(0,2)) * 60
             + Integer.parseInt(time.substring(3));

         boolean notFound = false;
         while (true){
             timeInSeconds++;
             int hour = (timeInSeconds % 1440) / 60;
             int hourDigit1 = hour / 10;
             int hourDigit2 = hour % 10;

             int minutes = (timeInSeconds % 60) ;
             int minutesDigit1 = minutes / 10;
             int minutesDigit2 = minutes % 10;

             if(clockDigits.contains(hourDigit1) && clockDigits.contains(hourDigit2)
                 && clockDigits.contains(minutesDigit1) && clockDigits.contains(minutesDigit2))
                     return (String) ""+hourDigit1 +""+ hourDigit2 +":" + minutesDigit1 +""+minutesDigit2;

         }
     }

    //Problem 163
    private List<Integer> getRangeList(int first, int last){
        List<Integer> range = new ArrayList<>();
        range.add(first);
        range.add(last);
        return range;
    }

    public List<List<Integer>> findMissingRanges(int[] nums, int lower, int upper) {

        //list for the result of the function
        List<List<Integer>> result = new ArrayList<>();

        //check empty string
        if(nums.length ==0) {
            result.add(getRangeList(lower,upper));
            return result;
        }

        //get the first range
        if(nums[0]!=lower)
            result.add(getRangeList(lower, nums[0]-1));

        //get the rest of the ranges
        for (int i=0;i<nums.length-1;i++){
            if(nums[i+1] - nums[i] != 1){
                result.add(getRangeList(nums[i]+1, nums[i+1]-1));
            }

        }

        //get the last range
        if(nums[nums.length-1]!=upper)
            result.add(getRangeList(nums[nums.length-1]+1,upper));

        return result;
    }

    //Problem 36 Sudoku validity
    private boolean checkSubBox(int row,int column,char[][] board){
        HashSet<Character> set = new HashSet<>();
         for(int i=row;i<row+3;i++){

            for(int j=column;j<column+3;j++){
                if(board[i][j]!='.' && !set.add(board[i][j]))
                    return false;
            }
         }
         return true;
    }


    public boolean isValidSudoku(char[][] board) {
        int rows = board.length;
        int columns = board[0].length;
        for(int i=0;i<rows;i++){
            HashSet<Character> set = new HashSet<>();
            for(int j=0;j<columns;j++){
                if(board[i][j]!='.' && !set.add(board[i][j]))
                    return false;
            }
        }
        for(int j=0;j<columns;j++){
            HashSet<Character> set = new HashSet<>();
            for(int i=0;i<rows;i++){
                if(board[i][j]!='.' && !set.add(board[i][j]))
                    return false;
            }
        }

        for(int i=0;i<rows;i=i+3)
            for(int j=0;j<columns;j=j+3)
                if(!checkSubBox(i,j,board))
                    return false;

        return true;

    }

    //Problem 3517
    public String smallestPalindrome(String s) {
        StringBuffer result = new StringBuffer();
        int mid = s.length()/2;
        char[] word = s.substring(0,mid).toCharArray();
        Arrays.sort(word);
        String firstHalf = new String(word);
        String secondHalf = new StringBuffer(firstHalf).reverse().toString();
        if(s.length()%2==1)
            firstHalf += s.charAt(mid);
        return firstHalf + secondHalf;
    }

    //Problem 3516
    public int findClosest(int x, int y, int z) {
        int a = Math.abs(z-x);
        int b = Math.abs(z-y);
        return (a<b)?1:((a>b)?2:0);
    }

    //Problem 3522
    public long calculateScore(String[] instructions, int[] values) {
        int n = values.length;
        boolean[] visited = new boolean[n];
        int i=0;
        boolean repeated = false;
        long score =0;
        while(i>=0 && i<n){
            visited[i] = true;
            if(instructions[i].charAt(0) == 'j'){
                i += values[i];
            }else{
                score += values[i];
                i++;
            }
            if(i>=0 && i<n){
                if(visited[i])
                    return score;
            }
        }
        return score;
    }

    //Problem 155
    class MinStack {

    Stack<Integer> stack;
    int min;

    public MinStack() {
        stack = new Stack<>();
        min = Integer.MAX_VALUE;
    }

    public void push(int val) {
        if(val<=min){
            stack.push(min);
            min = val;
        }
        stack.push(val);
    }

    public void pop() {
        if (min == stack.pop())
            min = stack.pop();

    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
    }

    //Problem 397
    private HashMap<Long, Integer> memo = new HashMap<>();

    private int numSteps(long i){
        if (i==1)
            return 0;

        if(memo.containsKey(i))
            return memo.get(i);

        int steps = Integer.MAX_VALUE;
        int stepPlus = Integer.MAX_VALUE;
        int stepsMinus = Integer.MAX_VALUE;

        if(i%2 == 0){
            steps =  1 + numSteps(i/2);
        }else{
            stepPlus = 1 + numSteps(i+1);
            stepsMinus = 1 + numSteps(i-1);
        }
        memo.put(i,Math.min (steps, Math.min (stepPlus,stepsMinus)));
        return memo.get(i);
    }

    public int integerReplacement(int n) {
        return numSteps(n);

    }

    //problem 264
    public int nthUglyNumber(int n) {
        PriorityQueue<Integer> uglies = new PriorityQueue<>();
        Set<Integer> seenUglies = new HashSet<>();

        uglies.add(1);
        seenUglies.add(1);
        for (int i=0;i<n;i++){

            int factor = uglies.poll();
            if(seenUglies.add(factor*2))
                uglies.add(factor*2);
            if(seenUglies.add(factor*3))
                uglies.add(factor*3);
            if(seenUglies.add(factor*5))
                uglies.add(factor*5);
        }

        return uglies.poll();


    }

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
