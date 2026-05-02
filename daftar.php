<?php
require_once('koneksi.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nama     = $_POST['nama'];
    $ttl      = $_POST['ttl'];
    $whatsapp = $_POST['whatsapp'];
    $sekolah  = $_POST['sekolah'];
    $alamat   = $_POST['alamat'];
    $foto     = $_POST['foto']; // Ini menerima String Base64 dari Android

    $sql = "INSERT INTO pendaftaran (nama, ttl, whatsapp, sekolah, alamat, foto) 
            VALUES ('$nama', '$ttl', '$whatsapp', '$sekolah', '$alamat', '$foto')";

    if (mysqli_query($con, $sql)) {
        $response["status"] = 1;
        $response["pesan"]  = "Pendaftaran berhasil disimpan!";
    } else {
        $response["status"] = 0;
        $response["pesan"]  = "Gagal menyimpan: " . mysqli_error($con);
    }

    echo json_encode($response);
}
mysqli_close($con);
?>