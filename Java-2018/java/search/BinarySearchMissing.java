package search;


public class BinarySearchMissing {

    public static void main(String[] args) {
        Integer[] data = new Integer[args.length - 1];
        int key = Integer.parseInt(args[0]);
        for (int i = 1; i < args.length; i++) {
            data[i - 1] = Integer.parseInt(args[i]);
        }
        if (data.length == 0) {
            System.out.print(-1);
            return;
        }
        if (key < data[data.length - 1]) {
            System.out.print(-(data.length + 1));
            return;
        }
        if (key > data[0]) {
            System.out.print(-1);
            return;
        }
        //System.out.println(binSearchIterative(data, key));
        System.out.println(binSearchRecursive(data, key, -1, data.length));
    }


    // Pre:  (data[i] >= data[i + 1] for all i in 0 .. data.size - 2)

    // Sa: && (right == data.size || (right < data.size && data[right] <= key)) &&
    // && (left == -1 || (0 <= left < data.size && data[left] > key))

    private static int binSearchIterative(Integer[] data, int key) {
        int left = -1, right = data.length;
        // Inv: (right == data.length || (right < data.length && data[right] <= key))
        // && (left == -1 || (left >= 0 && left < data.length && data[left] > key))
        // && (key' == key)  && (data'.size == data.size && data[i] == data'[i] for all i in 0..data.size-1)
        while (right - left > 1) {
            int middle = left + (right - left) / 2;
            // Pre && Inv && (right - left >=1) && (middle == left + (right - left) / 2)
            if (data[middle] <= key) {
                // Pre && Inv && (right - left >=1) && right - left > 1  && middle = (right + left) div 2
                // && data[middle] <= key && left' <= left < middle < right <= right'
                right = middle;
                // Pre && Inv && right == middle && left = left'' &&   left < right &&  left >= left'
            } else {
                // Pre && Inv && (right - left >=1) && right - left > 1 && middle = (right + left) div 2 && data[middle] > key
                left = middle;
                // Pre && Inv && left == middle && right = right'' &&  left < right && right <= right'
            }
        }

        if (key != data[right]) {
            //key != data[right]
            return -(right + 1);
            //R == -(right + 1)
        }

        return right;
    }
    // Post: (data[i] == data[i]' for all i in 0 .. data.size - 2) && ((data.last > x && R == -( data.size + 1))
    // || (R < data.size && data[R] <= key && (R == -1 || data[R - 1] > key) || (R = -(right + 1)) && key != data[right]))
    // && (data.size == data'.size) && (key' == key)

    // Pre:  (data[i] >= data[i + 1] for all in 0..data.size - 2)
    // && (right == data.size || (right < data.size && data[right] <= key)) &&
    // && (left == -1 || (0 <= left < data.size && data[left] > key))

    // Inv: (data[i] == data[i]' for all in 0 .. data.size - 1)  && (right == data.length || (right < data.length && data[right] <= key))
    // && (left == -1 || (left >= 0 && left < data.length && data[left] > key))
    // && key' == key
    private static int binSearchRecursive(Integer[] data, int key, int left, int right) {
        if (right - left == 1) {
            //Pre && Inv right - left == 1
            if (data[right] != key) {
                //Pre && Inv && data[right] != key
                return -(right + 1);
                // R == -(right + 1)
            } else {
                return right;
                // R == right
            }
        } else {
            int middle = (left + right) / 2;
            // Pre && Inv && (right - left > 1) && right > left + 1 && middle == (left + right) div 2
            // && left < middle < right && left == left' && right = right' && key = key'
            if (data[middle] <= key) {
                // Pre && Inv && (right - left >= 1) && right > left + 1 && middle == (left + right) div 2
                // && left < middle < right && left' == left && right' == right && data[middle] <= key
                return binSearchRecursive(data, key, left, middle);
            } else {
                // Pre && Inv && (right - left >= 1) && right > left + 1 && middle == (left + right) div 2
                // && left < middle < right && left' == left && right' == right && data[middle] >= key
                return binSearchRecursive(data, key, middle, right);
            }
        }
    }
    // Post: (data[i] == data[i]' for all i in 0 .. data.size - 2) && ((data.last > x && R == -( data.size + 1))
    // || (R < data.size && data[R] <= key && (R == -1 || data[R - 1] > key) || (R = -(right + 1)) && key != data[right]))
    // && (data.size == data'.size) && (key' == key)
}