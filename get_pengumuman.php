<?php
require_once('koneksi.php');

$sql = "SELECT * FROM pengumuman ORDER BY id_pengumuman DESC";
$res = mysqli_query($con, $sql);

$result = array();
while ($row = mysqli_fetch_array($res)) {
    array_push($result, array(
        'id_pengumuman' => $row['id_pengumuman'],
        'judul'         => $row['judul'],
        'tgl_posting'   => $row['tgl_posting'],
        'deskripsi'     => $row['deskripsi'],
        'gambar'        => $row['gambar'] // Pastikan isinya URL lengkap (misal: http://IP/foto.jpg)
    ));
}

echo json_encode($result);
mysqli_close($con);
?>