# ADR-0001: Pemilihan Struktur Data

- Status: Diterapkan 
- Tanggal: 5-11-2026

## Konteks
Aplikasi perlu menyimpan record putusan dan mencatat aktivitas pengguna. Implementasi mengikuti batasan tugas agar struktur data dibuat sendiri.

## Keputusan
Menggunakan DynamicArray sebagai struktur data utama dan Stack sebagai struktur data sekunder untuk audit. Penyimpanan internal menggunakan array.

## Alasan
DynamicArray mendukung akses indeks dan operasi tambah/hapus. Stack cocok untuk riwayat aktivitas karena menggunakan prinsip LIFO.

## Konsekuensi
perlu mengimplementasikan operasi, pengelolaan kapasitas, invariant, dan pengujian sendiri.

