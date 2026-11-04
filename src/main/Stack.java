import java.util.Objects;

public class Stack<T> {

    private Object[] elements;
    private int top;

    // Constructor
    public Stack() {
        this(10);
    }

    public Stack(int initialCapacity) {

        if (initialCapacity < 1) {
            initialCapacity = 1;
        }

        elements = new Object[initialCapacity];
        top = 0;
    }

    // Memperbesar kapasitas Stack
    private void ensureCapacity() {

        if (top < elements.length) {
            return;
        }

        Object[] expanded =
                new Object[elements.length * 2];

        for (int i = 0; i < top; i++) {
            expanded[i] = elements[i];
        }

        elements = expanded;
    }

    // Menambahkan elemen ke Stack
    public void push(T value) {

        if (value == null) {
            throw new IllegalArgumentException(
                    "Stack tidak menerima null"
            );
        }

        ensureCapacity();

        elements[top++] = value;
    }

    // Mengeluarkan elemen paling atas
    @SuppressWarnings("unchecked")
    public T pop() {

        if (isEmpty()) {
            return null;
        }

        T value = (T) elements[--top];

        elements[top] = null;

        return value;
    }

    // Melihat elemen paling atas tanpa menghapusnya
    @SuppressWarnings("unchecked")
    public T peek() {

        if (isEmpty()) {
            return null;
        }

        return (T) elements[top - 1];
    }

    // Menghitung jumlah elemen
    public int size() {
        return top;
    }

    // Memeriksa apakah Stack kosong
    public boolean isEmpty() {
        return top == 0;
    }

    // Mengambil elemen berdasarkan posisi dari bawah
    @SuppressWarnings("unchecked")
    public T getFromBottom(int index) {

        if (index < 0 || index >= top) {
            throw new IndexOutOfBoundsException(
                    "Index tidak valid"
            );
        }

        return (T) elements[index];
    }

    // Mencari elemen tanpa mengubah urutan Stack
    public boolean searchPreserve(T target) {

        Stack<T> backup =
                new Stack<>(Math.max(1, top));

        // Menyalin seluruh elemen
        for (int i = 0; i < top; i++) {
            backup.push(getFromBottom(i));
        }

        boolean found = false;

        // Mengeluarkan seluruh elemen sementara
        while (!isEmpty()) {

            T value = pop();

            if (Objects.equals(value, target)) {
                found = true;
            }
        }

        // Mengembalikan Stack seperti semula
        restore(backup);

        return found;
    }

    // Mengembalikan isi Stack
    public void restore(Stack<T> backup) {

        if (backup == null) {
            throw new IllegalArgumentException(
                    "Backup tidak boleh null"
            );
        }

        elements = new Object[
                Math.max(10, backup.top)
        ];

        top = backup.top;

        for (int i = 0; i < top; i++) {
            elements[i] = backup.elements[i];
        }
    }

    // Memeriksa invariant Stack
    public boolean validateInvariant() {

        if (elements == null) return false;

        if (top < 0 || top > elements.length) {
            return false;
        }

        for (int i = 0; i < top; i++) {

            if (elements[i] == null) {
                return false;
            }
        }

        for (int i = top; i < elements.length; i++) {

            if (elements[i] != null) {
                return false;
            }
        }

        return true;
    }
}
