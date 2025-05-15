package com.example.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private final Context context;
    private final List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);

        holder.textViewRoomType.setText("Oda Tipi: " + room.getType());
        holder.textViewAllowedUsers.setText("Kullanıcılar: " + getRoleName(room.getAllowed_users()));
        holder.textViewPeopleLimit.setText("Kişi Sınırı: " + room.getMin_people() + " - " + room.getMax_people());
        holder.textViewRoomCount.setText("Toplam Oda: " + room.getTotal_rooms());
        holder.textViewDuration.setText("Süre: " + room.getMin_duration() + " - " + room.getMax_duration() + " saat");

        holder.reserveButton.setOnClickListener(v -> {
            Toast.makeText(context, room.getType() + " odası rezerve edildi!", Toast.LENGTH_SHORT).show();
            // Firebase'e rezervasyon kaydı yapılacak yer
        });
    }

    @Override
    public int getItemCount() {
        return roomList != null ? roomList.size() : 0;
    }

    private String getRoleName(int roleCode) {
        switch (roleCode) {
            case 1:
                return "Akademisyen";
            case 2:
                return "Öğrenci";
            case 3:
                return "Öğrenci, Akademisyen";
            default:
                return "Bilinmeyen";
        }
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRoomType, textViewAllowedUsers, textViewPeopleLimit, textViewRoomCount, textViewDuration;
        Button reserveButton;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoomType = itemView.findViewById(R.id.textViewRoomType);
            textViewAllowedUsers = itemView.findViewById(R.id.textViewAllowedUsers);
            textViewPeopleLimit = itemView.findViewById(R.id.textViewPeopleLimit);
            textViewRoomCount = itemView.findViewById(R.id.textViewRoomCount);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            reserveButton = itemView.findViewById(R.id.reserveButton);
        }
    }
}
