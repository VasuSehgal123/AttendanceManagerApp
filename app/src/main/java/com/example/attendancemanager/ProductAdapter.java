package com.example.attendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {



    private  Context mCtx;


    private List<CardItems> productList;

    //getting the context and product list with constructor
    public ProductAdapter(ArrayList<CardItems> productList) {

        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        Context context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_products,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {
        //getting the product of the specified position
        CardItems product = productList.get(position);

        //binding the data with the viewholder views
        holder.subject.setText(product.getPresent());
        holder.percentage.setText(product.getPercentage());
        holder.total.setText(product.getTotal());
        holder.present.setText(product.getPresent());
        holder.absent.setText(product.getAbsent());


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


 public static class ProductViewHolder extends RecyclerView.ViewHolder {

         public TextView subject, percentage, total, present,absent;


        public ProductViewHolder(View itemView) {
            super(itemView);

            subject = (TextView)itemView.findViewById(R.id.subject);
            percentage = (TextView)itemView.findViewById(R.id.percent);
            total = (TextView)itemView.findViewById(R.id.total);
            present = (TextView)itemView.findViewById(R.id.present);
            absent = (TextView)itemView.findViewById(R.id.absent);

        }
    }
}
