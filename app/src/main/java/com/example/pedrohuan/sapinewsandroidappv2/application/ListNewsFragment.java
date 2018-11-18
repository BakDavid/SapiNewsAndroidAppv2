package com.example.pedrohuan.sapinewsandroidappv2.application;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item.ListItem;
import com.example.pedrohuan.sapinewsandroidappv2.application.adapter_and_item.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListNewsFragment extends Fragment {

    View fullView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fullView = inflater.inflate(R.layout.fragment_list_news,container,false);

        recyclerView = (RecyclerView) fullView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();

        for(int i = 0; i< 10;i++)
        {
            ListItem listItem = new ListItem(
                    "Ez egy buzi rovid leiras: " + i,
                    "Bak David " + i,
                    "" + i,
                    null
            );

            listItems.add(listItem);
        }

        adapter = new NewsAdapter(listItems,getContext());

        recyclerView.setAdapter(adapter);

        return fullView;
    }
}
