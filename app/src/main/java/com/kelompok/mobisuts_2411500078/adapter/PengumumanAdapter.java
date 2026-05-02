package com.kelompok.mobisuts_2411500078.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.kelompok.mobisuts_2411500078.R;
import com.kelompok.mobisuts_2411500078.model.Pengumuman;
import java.util.List;

public class PengumumanAdapter extends RecyclerView.Adapter<PengumumanAdapter.ViewHolder> {

    // Variabel list yang tadi error harus ada di sini
    private List<Pengumuman> list;

    // Constructor untuk menerima data dari MainActivity
    public PengumumanAdapter(List<Pengumuman> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan nama file layout sesuai (item_pengumuman.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengumuman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pengumuman p = list.get(position);
        holder.tvJudul.setText(p.getJudul());
        holder.tvTgl.setText(p.getTgl_posting());
        holder.tvDeskripsi.setText(p.getDeskripsi());

        // Menggunakan Glide untuk memuat gambar dari URL database
        Glide.with(holder.itemView.getContext())
                .load(p.getGambar())
                .placeholder(R.drawable.ic_launcher_background) // Gambar sementara saat loading
                .into(holder.imgPengumuman);
    }

    @Override
    public int getItemCount() {
        // Jika list null, return 0. Jika ada isi, return jumlahnya.
        return (list != null) ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvTgl, tvDeskripsi;
        ImageView imgPengumuman;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan dengan ID di XML kamu yang baru
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvTgl = itemView.findViewById(R.id.tv_tgl);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            imgPengumuman = itemView.findViewById(R.id.img_pengumuman);
        }
    }
}