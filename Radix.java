public class Radix {

    public static int nth(int n, int col) {
        int pow10 = (int) Math.pow(10, col);
        return Math.abs((n - (n % pow10)) / pow10 % 10);
    }

    public static int length(int n) {
        if (n == 0) {
            return 1;
        } else {
            return (int) Math.log10(Math.abs(n)) + 1;
        }
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

            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new SortableLinkedList();
            }

            for (int i = 0; i < maxLength; i++) {
                // Move to buckets
                while (data.size() > 0) {
                    int n = data.remove(0);
                    buckets[nth(n, i)].add(n);
                }

                merge(data, buckets); // Merge
            }
        }
    }

    private static void radixSortSimpleNeg(SortableLinkedList data) {
        if (data.size() > 1) {
            int maxLength = getMaxLength(data);
            SortableLinkedList[] buckets = new SortableLinkedList[10];

            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new SortableLinkedList();
            }

            for (int i = 0; i < maxLength; i++) {
                // Move to buckets
                while (data.size() > 0) {
                    int n = data.remove(0);
                    buckets[nth(n, i)].add(n);
                }

                mergeNeg(data, buckets); // Merge
            }
        }
    }

    public static void radixSort(SortableLinkedList data) {
        SortableLinkedList nonNegative = new SortableLinkedList();
        SortableLinkedList negative = new SortableLinkedList();

        while (data.size() > 0) {
            int n = data.remove(0);

            if (n >= 0) {
                nonNegative.add(n);
            } else {
                negative.add(n);
            }
        }

        radixSortSimpleNeg(negative);
        radixSortSimple(nonNegative);
        
        data.extend(negative);
        data.extend(nonNegative);

    }

    private static int getMaxLength(SortableLinkedList data) {
        if (data.size() > 0) {
            int max = 0;

            for (int i = 0; i < data.size(); i++) {
                int temp = data.remove(0);
                int tempAbs = Math.abs(temp);
                if (tempAbs > max) {
                    max = tempAbs;
                }
                data.add(temp);
            }

            return length(max);
        } else
            throw new IllegalArgumentException("Data size is less than 1");
    }

}