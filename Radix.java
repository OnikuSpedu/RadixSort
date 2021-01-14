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

    public static void radixSortSimple(SortableLinkedList data) {
        if (data.size() > 1) {
            int maxLength = length(getMax(data));
            for (int i = 0; i < maxLength; i++) {
                sortOnce(data, i);
            }
        }
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

    private static int getMax(SortableLinkedList data) {
        if (data.size() > 0) {
            int max = data.get(0);
            data.remove(0);
            data.add(0);
            for (int i = 0; i < data.size() - 1; i++) {
                int temp = data.get(0);
                if (temp > max) {
                    max = temp;
                }
                data.remove(0);
                data.add(temp);
            }
            return max;
        } else
            throw new IllegalArgumentException("Data size is less than 1");
    }
}