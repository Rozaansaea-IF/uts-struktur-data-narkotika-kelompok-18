
public class DynamicArray<T> implements StrukturData<T> {

    private Object[] data;
    private int size;
    private int capacity;

    // Constructor
    public DynamicArray() {
        capacity = 10;
        size = 0;
        data = new Object[capacity];
    }

    // Memperbesar kapasitas array
    private void resize() {

        capacity = capacity * 2;

        Object[] newData = new Object[capacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    // Menambahkan data di akhir
    @Override
    public void add(T value) {

        if (size == capacity) {
            resize();
        }

        data[size] = value;
        size++;
    }

    // Mengambil data berdasarkan indeks
    @SuppressWarnings("unchecked")
    @Override
    public T get(int position) {

        checkIndex(position);

        return (T) data[position];
    }

    // Mengubah data pada indeks tertentu
    public void set(int position, T value) {

        checkIndex(position);

        data[position] = value;
    }

    // Menambahkan data pada posisi tertentu
    public void insert(int position, T value) {

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException(
                "Posisi tidak valid"
            );
        }

        if (size == capacity) {
            resize();
        }

        for (int i = size; i > position; i--) {
            data[i] = data[i - 1];
        }

        data[position] = value;
        size++;
    }

    // Menghapus data berdasarkan indeks
    @SuppressWarnings("unchecked")
    @Override
    public T remove(int position) {

        checkIndex(position);

        T removedData = (T) data[position];

        for (int i = position; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;
        data[size] = null;

        return removedData;
    }

    // Mencari indeks berdasarkan nilai
    public int search(T value) {

        for (int i = 0; i < size; i++) {

            if (data[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    // Memeriksa apakah data tersedia
    @Override
    public boolean contains(T value) {
        return search(value) != -1;
    }

    // Mengembalikan jumlah elemen
    @Override
    public int size() {
        return size;
    }

    // Memeriksa apakah array kosong
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Menghapus seluruh elemen
    @Override
    public void clear() {

        for (int i = 0; i < size; i++) {
            data[i] = null;
        }

        size = 0;
    }

    // Memeriksa indeks
    private void checkIndex(int position) {

        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException(
                "Indeks di luar batas array"
            );
        }
    }

    // Memeriksa invariant struktur data
    public boolean validateInvariant() {

        if (data == null) return false;
        if (size < 0) return false;
        if (capacity < 1) return false;
        if (size > capacity) return false;
        if (data.length != capacity) return false;

        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                return false;
            }
        }

        for (int i = size; i < capacity; i++) {
            if (data[i] != null) {
                return false;
            }
        }

        return true;
    }

    // Menampilkan seluruh isi array
    public void display() {

        for (int i = 0; i < size; i++) {
            System.out.println(data[i]);
            System.out.println("----------------------");
        }
    }
}
