public class StackTest {

    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();

        // Pengujian PUSH
        stack.push("IMPORT");
        stack.push("TAMBAH");
        stack.push("HAPUS");

        System.out.println("Size: " + stack.size());

        // Pengujian PEEK
        System.out.println("Peek: " + stack.peek());

        // Pengujian SEARCH PRESERVE
        boolean found = stack.searchPreserve("TAMBAH");

        System.out.println("Ditemukan: " + found);

        // Memastikan urutan tetap
        System.out.println("Setelah pencarian:");

        for (int i = 0; i < stack.size(); i++) {
            System.out.println(stack.getFromBottom(i));
        }

        // Pengujian POP
        System.out.println("Pop: " + stack.pop());

        System.out.println("Size setelah pop: " + stack.size());

        // Pengujian RESTORE
        Stack<String> backup = new Stack<>();

        backup.push("A");
        backup.push("B");
        backup.push("C");

        stack.restore(backup);

        System.out.println("Setelah restore:");

        for (int i = 0; i < stack.size(); i++) {
            System.out.println(stack.getFromBottom(i));
        }

        // Pengujian invariant
        System.out.println(
            "Invariant: " + stack.validateInvariant()
        );
    }
}
