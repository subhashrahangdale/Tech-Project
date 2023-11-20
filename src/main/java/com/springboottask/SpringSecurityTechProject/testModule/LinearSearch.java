package com.springboottask.SpringSecurityTechProject.testModule;

public class LinearSearch {


    public static void main(String[] args) {

        int nums[] = {3, 4, 5, 6, 7, 8, 9, 12, 13, 15, 17};
        int target = 15;

        System.out.println("element in index : " + binarySearchRecursive(nums, target,0,nums.length-1));
        System.out.print(mcall);

    }

    private static int leanerSearch(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private static int binarySearch(int[] nums, int target) {
        int st = 0;
        int end = nums.length - 1;
        while (st <= end) {
            int mid = (st + end) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else if (nums[mid] < target) {
                st = mid + 1;
            }
        }
        return -1;
    }


    static int mcall=0;
    private static int binarySearchRecursive(int[] nums, int target, int st, int end) {
        mcall++;
        if(st <= end) {
            int mid = (st + end) / 2;
            int num = nums[mid];
            if (num == target) {
                return mid;
            } else if (num > target) {
                end = mid - 1;
                return binarySearchRecursive(nums, target, st, end);
            } else {
                st = mid + 1;
                return binarySearchRecursive(nums, target, st, end);
            }
        }
        return -1;
    }
}
