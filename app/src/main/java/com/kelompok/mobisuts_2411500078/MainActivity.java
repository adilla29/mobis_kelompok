package com.kelompok.mobisuts_2411500078;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompok.mobisuts_2411500078.adapter.PengumumanAdapter;
import com.kelompok.mobisuts_2411500078.api.ApiInterface;
import com.kelompok.mobisuts_2411500078.api.RetrofitClient;
import com.kelompok.mobisuts_2411500078.model.Pengumuman;
import com.kelompok.mobisuts_2411500078.ui.PendaftaranActivity; // Import Pendaftaran
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPengumuman;
    private PengumumanAdapter adapter; // Gunakan tipe spesifik agar tidak kuning (Raw use)
    private FloatingActionButton fabDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPengumuman = findViewById(R.id.rv_pengumuman);
        rvPengumuman.setLayoutManager(new LinearLayoutManager(this));

        fabDaftar = findViewById(R.id.fab_tambah);
        fabDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PendaftaranActivity.class);
            startActivity(intent);
        });

        ambilDataPengumuman();
    }

    private void ambilDataPengumuman() {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<List<Pengumuman>> call = apiInterface.getPengumuman();

        call.enqueue(new Callback<List<Pengumuman>>() {
            @Override
            public void onResponse(Call<List<Pengumuman>> call, Response<List<Pengumuman>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pengumuman> list = response.body();
                    adapter = new PengumumanAdapter(list);
                    rvPengumuman.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pengumuman>> call, Throwable t) {
                // Ini akan memunculkan pesan kenapa dia gagal di layar HP
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                android.util.Log.e("API_ERROR", "Penyebab: ", t);
            }
        });
    }
}