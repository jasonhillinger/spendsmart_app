package com.example.spendsmart_soen357_app.ui.budget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendsmart_soen357_app.R;

import java.util.List;
import java.util.zip.Inflater;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<RecyclerModel> userList;
    private LayoutInflater inflater;
    private Context context;

    public Adapter (Context context, List<RecyclerModel>userList)
    {
        inflater = LayoutInflater.from(context);
        this.userList=userList;
        this.context = context;
    }

    public void deleteItem(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userList.size());
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item, parent, false);
        View view = inflater.inflate(R.layout.budget_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position){
        int imageview = userList.get(position).getImageview();
        String name = userList.get(position).getNameview();
        String priceview = userList.get(position).getPriceview();
        String categoryview = userList.get(position).getCategoryview();

        holder.setData(imageview, name, priceview, categoryview);

    }

    @Override
    public int getItemCount(){
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageview;
        private TextView priceview;
        private TextView nameview;
        private TextView categoryview;
        private Adapter adapter;

        public ViewHolder(@NonNull View itemView, Adapter adapter) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            priceview = itemView.findViewById(R.id.priceview);
            nameview = itemView.findViewById(R.id.nameview);
            categoryview = itemView.findViewById(R.id.categoryview);
            this.adapter = adapter;

            // Set the click listener for the itemView
            itemView.setOnClickListener(this);
        }

        public void setData(int image, String name, String price, String category) {
            imageview.setImageResource(image);
            priceview.setText(price);
            nameview.setText(name);
            categoryview.setText(category);
        }

        @Override
        public void onClick(View v) {
            // Create a pop-up dialog that confirms whether the user wants to delete the item or not
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Call the deleteItem method of the adapter with the position of the clicked item
                            adapter.deleteItem(getAdapterPosition());
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView imageview;
//        private TextView priceview;
//        private TextView nameview;
//        private TextView categoryview;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageview = itemView.findViewById(R.id.imageview);
//            priceview = itemView.findViewById(R.id.priceview);
//            nameview = itemView.findViewById(R.id.nameview);
//            categoryview = itemView.findViewById(R.id.categoryview);
////            this.adapter = adapter;
////            // Set the click listener for the itemView
////            itemView.setOnClickListener(this);
//        }
//
//        public void setData(int image, String name, String price, String category) {
//            imageview.setImageResource(image);
//            priceview.setText(price);
//            nameview.setText(name);
//            categoryview.setText(category);
//        }
//
////        @Override
////        public void onClick(View v) {
////            // Create a pop-up dialog that confirms whether the user wants to delete the item or not
////            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
////            builder.setMessage("Are you sure you want to delete this item?")
////                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            // Call the deleteItem method of the adapter with the position of the clicked item
////                            adapter.deleteItem(getAdapterPosition());
////                        }
////                    })
////                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            // Do nothing
////                        }
////                    });
////            AlertDialog dialog = builder.create();
////            dialog.show();
////        }
//    }
}
