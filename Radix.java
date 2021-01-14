public class Radix {
    
    public static int nth(int n, int col) {
        int pow10 = (int) Math.pow(10, col);
        return Math.abs((n - (n % pow10)) / pow10 % 10);
    }

    public static int length(int n) {
        int x = Math.abs(n);
        if (x >= 1000000000) return 10;
        else if (x >= 100000000) return 9;
        else if (x >= 10000000) return 8;
        else if (x >= 1000000) return 7;
        else if (x >= 100000) return 6;
        else if (x >= 10000) return 5;
        else if (x >= 1000) return 4;
        else if (x >= 100) return 3;
        else if (x >= 10) return 2;
        else return 1;

        // if (n == 0) {
        //     return 1;
        // } else {
        //     int x = Math.abs(n);
        //     return (int) Math.floor(Math.log10(x)) + 1;
        // }
    }

    public static void merge(SortableLinkedList original, SortableLinkedList[] buckets) {
        for (int i = 0; i < buckets.length; i++) {
            original.extend(buckets[i]);
        }
    }

    private static void mergeNeg(SortableLinkedList original, SortableLinkedList[] buckets) {
        for (int i = buckets.length - 1; i >= 0; i--) {
            original.extend(buckets[i]);
        }
    }

    public static void radixSortSimple(SortableLinkedList data) {
        if (data.size() > 1) {
            int maxLength = getMaxLength(data);
            SortableLinkedList[] buckets = new SortableLinkedList[10];
            for (int j = 0; j < buckets.length; j++) {
                buckets[j] = new SortableLinkedList();
            }
            for (int i = 0; i < maxLength; i++) {

                // Move to buckets
                
                while (data.size() > 0) {
                    int n = data.get(0);
                    data.remove(0);
                    int digit = nth(n, i);
                    buckets[digit].add(n);
                }

                // Merge
                merge(data, buckets);
            }
        }
    }

    private static void radixSortNeg(SortableLinkedList data) {
        if (data.size() > 1) {
            int maxLength = getMaxLength(data);
            for (int i = 0; i < maxLength; i++) {
                sortOnceNeg(data, i);
            }
        }
    }

    public static void radixSort(SortableLinkedList data) {
        // The goal will be to split into two linked lists.
        // One for nonnegative other for positive. Sort them.
        // Then merge at the end.

        SortableLinkedList nonNegative = new SortableLinkedList();
        SortableLinkedList negative = new SortableLinkedList();

        int size = data.size();
        for (int i = 0; i < size; i++) {
            int n = data.get(0);
            data.remove(0);

            if (n >= 0) {
                nonNegative.add(n);
            } else {
                negative.add(n);
            }
        }

        Radix.radixSortSimple(nonNegative);
        Radix.radixSortNeg(negative);

        data.extend(negative);
        data.extend(nonNegative);

    }


    private static void sortOnceNeg(SortableLinkedList data, int i) {
        // Init buckets
        SortableLinkedList[] buckets = new SortableLinkedList[10];
        for (int j = 0; j < buckets.length; j++) {
            buckets[j] = new SortableLinkedList();
        }

        // Move to buckets
        int size = data.size();
        for (int j = 0; j < size; j++) {
            int n = data.get(0);
            data.remove(0);
            int digit = nth(n, i);
            buckets[digit].add(n);
        }

        // Merge
        mergeNeg(data, buckets);
    }

    private static int getMaxLength(SortableLinkedList data) {
        if (data.size() > 0) {
            int max = 0;
            for (int i = 0; i < data.size(); i++) {
                int temp = data.get(0);
                int tempAbs = Math.abs(temp);
                if (tempAbs > max) {
                    max = tempAbs;
                }
                data.remove(0);
                data.add(temp);
            }
            return length(max);
        } else
            throw new IllegalArgumentException("Data size is less than 1");
    }

}