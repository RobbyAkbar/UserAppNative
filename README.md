# User App Auth
Test script ini dibuat untuk mengukur kemampuan kandidat yang sebelumnya sudah melaksanakan interview dengan divisi HR PT. FAN Integrasi Teknologi dengan harapan kandidat dapat menyelesaikan test script ini dengan sebaik mungkin.

## Requirement:
[x] Native: Java/Kotlin
[Hybrid: Flutter](https://github.com/RobbyAkbar/UserAppFlutter) - OnProgress
[x] Firebase

## Soal
- Buatlah aplikasi android native dan hybrid dengan fitur: Register, Login, Lupa Password, Home.

## Keterangan
- Data user terdiri dari:
    * Nama
        * Minimal 3 huruf
        * Maksimal 50 huruf
        * Tidak boleh kosong
    * Email
        * Email validasi
        * Tidak boleh kosong
    * Password
        * Minimal 8 karakter
        * Mengandung angka, huruf besar dan kecil
        * Tidak boleh kosong
    * Password Konfirmasi
        * Sama dengan field password
        * Tidak boleh kosong
- Pada halaman Register, Login dan Lupa Password harus ada validasi setiap inputan
- Disaat Registrasi dan Lupa Password user akan mendapat link konfirmasi via email
- Data disimpan di Firebase
- Pada halaman Home tampilkan data user yang sudah registrasi, disaat mengambil data harus menggunakan token dari login.
- Data pada halaman Home dapat difilter berdasarkan status sudah konfirmasi via email atau belum.
- **UI yang menarik dan animasi akan menjadi nilai tambah.**

## Acknowledgements
- [Login-UI-Compose](https://github.com/B-L-Studios/Login-UI-Compose)
- [FirebaseAuthJetCompose](https://github.com/meshramaravind/FirebaseAuthJetCompose)
- [AttendanceFirebase](https://github.com/verindrarizya/AttendanceFirebase)
