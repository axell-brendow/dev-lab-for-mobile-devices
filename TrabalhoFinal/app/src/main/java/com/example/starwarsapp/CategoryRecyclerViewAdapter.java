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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.function.Consumer;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder>
{
    private Context myContext;
    private Category myCategory;
    private JsonArray myArray;

    public CategoryRecyclerViewAdapter(Context myContext, Category myCategory)
    {
        this.myContext = myContext;
        this.myCategory = myCategory;
        this.myArray = myCategory.getResults();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater myInflater = LayoutInflater.from(myContext);
        view = myInflater.inflate(R.layout.cardview_item_category, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        final JsonObject jsonObject = myArray.get(position).getAsJsonObject();
        SimplifiedInformer informer = APIConsumer.gson.fromJson(jsonObject, myCategory.getItemClass());

        informer.getPrimaryInfo(jsonObject,
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        holder.txtPrimaryInfo.setText(s);
                    }
                }
        );

        informer.getInfo1Name(jsonObject,
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        holder.txtInfo1Name.setText(s + ":");
                    }
                }
        );

        informer.getInfo1(jsonObject,
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        holder.txtInfo1.setText(s);
                    }
                }
        );

        informer.getInfo2Name(jsonObject,
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        holder.txtInfo2Name.setText(s + ":");
                    }
                }
        );

        informer.getInfo2(jsonObject,
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        holder.txtInfo2.setText(s);
                    }
                }
        );

        holder.imgThumbnailCategoryItem.setImageResource(R.drawable.no_image_available);

        holder.cardView.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(myContext, CategoryItemActivity.class);
                    intent.putExtra("category_name", myCategory.getName());
                    intent.putExtra("item_json", jsonObject.toString());

                    myContext.startActivity(intent);
                }
            }
        );
    }

    @Override
    public int getItemCount()
    {
        return myArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtPrimaryInfo;
        TextView txtInfo1Name;
        TextView txtInfo1;
        TextView txtInfo2Name;
        TextView txtInfo2;
        ImageView imgThumbnailCategoryItem;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txtPrimaryInfo = itemView.findViewById(R.id.card_primary_info);
            txtInfo1Name = itemView.findViewById(R.id.card_info1_name);
            txtInfo1 = itemView.findViewById(R.id.card_info1);
            txtInfo2Name = itemView.findViewById(R.id.card_info2_name);
            txtInfo2 = itemView.findViewById(R.id.card_info2);
            imgThumbnailCategoryItem = itemView.findViewById(R.id.card_image);
            cardView = itemView.findViewById(R.id.card_id);
        }
    }
}
