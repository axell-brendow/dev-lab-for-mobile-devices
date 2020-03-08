package com.example.starwarsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CategoryItemRecyclerViewAdapter extends RecyclerView.Adapter<CategoryItemRecyclerViewAdapter.MyViewHolder>
{
    private Context myContext;
    private HashMap<String, String> myData;

    // Receives the context and the data involved in the rendering
    public CategoryItemRecyclerViewAdapter(Context myContext, HashMap<String, String> myData)
    {
        this.myContext = myContext;
        this.myData = myData;
    }

    public static <K, V> Map.Entry<K, V> getEntryOnIndex(int index, Map<K, V> map)
    {
        Set< Map.Entry<K, V> > entrySet = map.entrySet();
        Iterator< Map.Entry<K, V> > iterator = entrySet.iterator();
        Map.Entry<K, V> entry = iterator.next();

        for (int i = 0; i < index && iterator.hasNext(); i++)
        {
            entry = iterator.next();
        }

        return entry;
    }

    @NonNull
    @Override // Receives the parent view where each item will be rendered
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater myInflater = LayoutInflater.from(myContext);
        view = myInflater.inflate(R.layout.category_item_property, parent, false);

        return new MyViewHolder(view);
    }

    @Override // After onCreateViewHolder, calls this to let us customize it.
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        final Map.Entry<String, String> entry = getEntryOnIndex(position, myData);

        holder.info1PropertyName.setText(entry.getKey());
        holder.info1Property.setText(entry.getValue());
    }

    @Override
    public int getItemCount()
    {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView info1PropertyName;
        TextView info1Property;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            info1PropertyName = itemView.findViewById(R.id.info1_property_name);
            info1Property = itemView.findViewById(R.id.info1_property);
        }
    }
}
