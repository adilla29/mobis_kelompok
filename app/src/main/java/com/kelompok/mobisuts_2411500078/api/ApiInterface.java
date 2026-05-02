package com.kelompok.mobisuts_2411500078.api;

import com.kelompok.mobisuts_2411500078.model.Pengumuman;
import com.kelompok.mobisuts_2411500078.model.ResponsePendaftaran;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    // 1. Fungsi untuk Pendaftaran (POST)
    @FormUrlEncoded
    @POST("daftar.php")
    Call<ResponsePendaftaran> daftar(
            @Field("nama") String nama,
            @Field("ttl") String ttl,
            @Field("whatsapp") String wa,
            @Field("sekolah") String sekolah,
            @Field("alamat") String alamat,
            @Field("foto") String fotoBase64
    );

    // 2. Fungsi untuk Mengambil Data Pengumuman (GET)
    // Tambahkan ini agar error 'Cannot resolve method getPengumuman' hilang
    @GET("get_pengumuman.php")
    Call<List<Pengumuman>> getPengumuman();
}