package com.example.library;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private DatabaseReference mDatabase;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        recyclerView = findViewById(R.id.recyclerViewRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance("https://libraryreservationsystem-f2a88-default-rtdb.europe-west1.firebasedatabase.app/").getReference("rooms");

        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(this, roomList);
        recyclerView.setAdapter(roomAdapter);

        getUserRoleAndFilterRooms();
    }

    private void getUserRoleAndFilterRooms() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(user.getUid());

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String role = dataSnapshot.child("role").getValue(String.class);
                    if (role != null) {
                        filterRoomsBasedOnRole(role.toLowerCase()); // role küçük harfe dönüştürülerek geçiliyor
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("RoomListActivity", "Error getting user role", databaseError.toException());
                }
            });
        }
    }

    private void filterRoomsBasedOnRole(String role) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                roomList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Room room = snapshot.getValue(Room.class);
                    if (room != null) {
                        int allowedCode = room.getAllowed_users();
                        if ((role.equals("academic") && (allowedCode == 1 || allowedCode == 3)) ||
                                (role.equals("student") && (allowedCode == 2 || allowedCode == 3))) {
                            roomList.add(room);
                        }
                    }
                }
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("RoomListActivity", "Error retrieving rooms", databaseError.toException());
            }
        });
    }

    private void addStaticRooms() {
        // allowed_users: 1 = Akademisyen, 2 = Öğrenci, 3 = Her ikisi de

        Room room1 = new Room("Akademik Çalışma Odası", 1, 1, 1, 3, 1, 2);
        Room room2 = new Room("Bireysel Çalışma Odası", 2, 1, 1, 5, 1, 2);
        Room room3 = new Room("Grup Çalışma Odası", 3, 2, 4, 10, 1, 3);
        Room room4 = new Room("Toplantı Odası", 3, 4, 10, 2, 2, 4);

        mDatabase.child("room1").setValue(room1);
        mDatabase.child("room2").setValue(room2);
        mDatabase.child("room3").setValue(room3);
        mDatabase.child("room4").setValue(room4);

        Toast.makeText(this, "Odalar Firebase'e eklendi!", Toast.LENGTH_SHORT).show();
    }
}
