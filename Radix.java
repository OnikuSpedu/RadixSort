public class Radix {
    public static int nth(int n, int col) {
        return Math.abs((n - (n % (int) Math.pow(10, col))) / (int) Math.pow(10, col) % 10);
    }

    public static int length(int n) {
        if (n == 0) {
            return 1;
        } else {
            int x = Math.abs(n);
            return (int) Math.floor(Math.log10(x)) + 1;
        }
    }

    public static void merge(SortableLinkedList original, SortableLinkedList[] buckets) {
        for (int i = 0; i < buckets.length; i++) {
            original.extend(buckets[i]);
        }
    }

    public static void mergeNeg(SortableLinkedList original, SortableLinkedList[] buckets) {
        for (int i = buckets.length - 1; i >= 0; i--) {
            System.out.println(buckets[i]);
            original.extend(buckets[i]);
        }
    }

    public static void radixSortSimple(SortableLinkedList data) {
        if (data.size() > 1) {
            int maxLength = getMaxLength(data);
            for (int i = 0; i < maxLength; i++) {
                sortOnce(data, i);
            }
        }
    }

    public static void radixSortNeg(SortableLinkedList data) {
        if (data.size() > 1) {
            int maxLength = getMaxLength(data);
            for (int i = 0; i < maxLength; i++) {
                sortOnceNeg(data, i);
            }
        }
    }

    public static void radixSort(SortableLinkedList data) {
        //The goal will be to split into two linked lists. 
        //One for nonnegative other for positive. Sort them. 
        //Then merge at the end.

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

    private static void sortOnce(SortableLinkedList data, int i) {
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
        merge(data, buckets);
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
            int temp = data.get(0);
            int maxLen = length(temp);
            data.remove(0);
            data.add(temp);
            for (int i = 0; i < data.size() - 1; i++) {
                temp = data.get(0);
                if (length(temp) > maxLen) {
                    maxLen = length(temp);
                }
                data.remove(0);
                data.add(temp);
            }
            return maxLen;
        } else
            throw new IllegalArgumentException("Data size is less than 1");
    }
}