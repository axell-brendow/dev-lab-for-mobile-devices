package com.example.starwarsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    private Context myContext;
    private List<Category> myData;

    public RecyclerViewAdapter(Context myContext, List<Category> myData)
    {
        this.myContext = myContext;
        this.myData = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater myInflater = LayoutInflater.from(myContext);
        view = myInflater.inflate(R.layout.cardview_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        final Category category = myData.get(position);

        holder.txtCategoryDescription.setText(category.getCount() + " " + category.getName());
        holder.imgThumbnailCategory.setImageResource(category.getThumbnail());
        holder.cardView.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(myContext, CategoryActivity.class);
                    intent.putExtra("category_name", category.getName());

                    myContext.startActivity(intent);
                }
            }
        );
    }

    @Override
    public int getItemCount()
    {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtCategoryDescription;
        ImageView imgThumbnailCategory;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txtCategoryDescription = itemView.findViewById(R.id.card_description);
            imgThumbnailCategory = itemView.findViewById(R.id.card_image);
            cardView = itemView.findViewById(R.id.card_id);
        }
    }
}
