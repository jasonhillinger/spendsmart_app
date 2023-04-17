package com.example.spendsmart_soen357_app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendsmart_soen357_app.Model.Transaction;
import com.example.spendsmart_soen357_app.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Viewholder>{
    private LayoutInflater inflater;
    ArrayList<Transaction> transactions;
    DecimalFormat decimalFormatter;

    private Context context;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions){
        inflater = LayoutInflater.from(context);
        this.transactions = transactions;
        decimalFormatter = new DecimalFormat("###,###,###,###.##");
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_transaction,parent,false);
        View view = inflater.inflate(R.layout.viewholder_transaction, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // Format date and time to string
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma");
        String dateString = dateFormat.format(transactions.get(position).getDate());
        String timeString = timeFormat.format(transactions.get(position).getTime());
        String dateTimeString = dateString + " " + timeString;

        // Formatting the amount
        String amount = "$ " + decimalFormatter.format(transactions.get(position).getAmount());

        holder.transactionSubject.setText(transactions.get(position).getSubject());
        holder.transactionAmount.setText(amount);
        holder.transactionDate.setText(dateTimeString);

        if(transactions.get(position).getType().equals("Purchase")){
            holder.transactionAmount.setTextColor(Color.parseColor("#F30A18"));
        }else {
            holder.transactionAmount.setTextColor(Color.parseColor("#00CC52"));
        }
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView transactionSubject, transactionDate, transactionAmount;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            transactionSubject = itemView.findViewById(R.id.transaction_subject);
            transactionDate = itemView.findViewById(R.id.transaction_date);
            transactionAmount = itemView.findViewById(R.id.transaction_amount);
        }
    }
}
