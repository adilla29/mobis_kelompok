package com.kelompok.mobisuts_2411500078.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok.mobisuts_2411500078.R;
import com.kelompok.mobisuts_2411500078.api.ApiInterface;
import com.kelompok.mobisuts_2411500078.api.RetrofitClient;
import com.kelompok.mobisuts_2411500078.model.ResponsePendaftaran;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendaftaranActivity extends AppCompatActivity {

    private EditText etNama, etTtl, etWa, etSekolah, etAlamat;
    private ImageView imgPendaftaran;
    private Button btnUpload, btnDaftar;
    private String sFoto = ""; // Menampung string Base64 foto

    // Launcher untuk membuka galeri (Metode terbaru)
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgPendaftaran.setImageBitmap(bitmap);
                        sFoto = bitmapToBase64(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Gagal memproses gambar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);

        // 1. Inisialisasi View sesuai ID di XML
        etNama = findViewById(R.id.et_nama);
        etTtl = findViewById(R.id.et_ttl);
        etWa = findViewById(R.id.et_wa);
        etSekolah = findViewById(R.id.et_asal_sekolah);
        etAlamat = findViewById(R.id.et_alamat);
        imgPendaftaran = findViewById(R.id.img_pendaftaran);
        btnUpload = findViewById(R.id.btn_upload);
        btnDaftar = findViewById(R.id.btn_daftar);

        // 2. Aksi Tombol Upload Foto
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        // 3. Aksi Tombol Simpan/Daftar
        btnDaftar.setOnClickListener(v -> simpanData());
    }

    private void simpanData() {
        String nama = etNama.getText().toString().trim();
        String ttl = etTtl.getText().toString().trim();
        String wa = etWa.getText().toString().trim();
        String sekolah = etSekolah.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();

        // Validasi sederhana
        if (nama.isEmpty() || wa.isEmpty()) {
            Toast.makeText(this, "Nama dan WhatsApp tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Panggil Retrofit
        ApiInterface api = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ResponsePendaftaran> call = api.daftar(nama, ttl, wa, sekolah, alamat, sFoto);

        call.enqueue(new Callback<ResponsePendaftaran>() {
            @Override
            public void onResponse(Call<ResponsePendaftaran> call, Response<ResponsePendaftaran> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Tampilkan pesan sukses dari server
                    Toast.makeText(PendaftaranActivity.this, "Berhasil: " + response.body().getPesan(), Toast.LENGTH_LONG).show();

                    // 1. Buat Intent untuk pindah ke MainActivity (Halaman Pengumuman)
                    // Ganti 'MainActivity.class' dengan nama activity pengumumanmu jika berbeda
                    Intent intent = new Intent(PendaftaranActivity.this, com.kelompok.mobisuts_2411500078.MainActivity.class);

                    // 2. Clear Task (Opsional tapi disarankan):
                    // Agar ketika user di halaman pengumuman menekan tombol 'Back', tidak kembali ke form pendaftaran lagi
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);

                    // 3. Tutup halaman pendaftaran
                    finish();
                } else {
                    Toast.makeText(PendaftaranActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePendaftaran> call, Throwable t) {
                Toast.makeText(PendaftaranActivity.this, "Koneksi Laragon Gagal: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Fungsi konversi Bitmap ke String Base64
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}