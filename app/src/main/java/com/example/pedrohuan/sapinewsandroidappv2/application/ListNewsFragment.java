package com.example.pedrohuan.sapinewsandroidappv2.application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item.ListItem;
import com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item.NewsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListNewsFragment extends Fragment {

    View fullView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    private List<String> newKeys;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    final DatabaseReference myRef = database.getReference("news");

    String openedFromFragment = "ListNewsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fullView = inflater.inflate(R.layout.fragment_list_news,container,false);

        recyclerView = (RecyclerView) fullView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();
        newKeys = new ArrayList<>();

        //Get the data from database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listItems.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    ListItem listItem = ds.getValue(ListItem.class);

                    String newKey = ds.getKey();

                    listItems.add(listItem);
                    newKeys.add(newKey);
                }

                adapter = new NewsAdapter(listItems,newKeys,openedFromFragment,getContext());

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });

        return fullView;
    }
}
