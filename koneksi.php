<?php
$host = "localhost";
$user = "root";
$pass = ""; 
$db   = "dbmobisuts_2411500078";

$con = mysqli_connect($host, $user, $pass, $db);

if (mysqli_connect_errno()) {
    echo "Gagal koneksi ke database: " . mysqli_connect_error();
}
?>