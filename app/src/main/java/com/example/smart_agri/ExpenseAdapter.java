package com.example.smart_agri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private Context context;
    private OnItemsClick onItemsClick;
    private List<Expense_Model> expense_modelList;

    public ExpenseAdapter(Context context,OnItemsClick onItemsClick) {
        this.context = context;
        expense_modelList=new ArrayList<>();
        this.onItemsClick=onItemsClick;

    }
    public void add(Expense_Model expense_model){
        expense_modelList.add(expense_model);
        notifyDataSetChanged();

    }
    public void clear(){
        expense_modelList.clear();
        notifyDataSetChanged();

    }

    public ExpenseAdapter() {
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Expense_Model expenseModel=expense_modelList.get(position);
        holder.note.setText(expenseModel.getNote());
        holder.categorgy.setText(expenseModel.getCategory());
        holder.amount.setText(String.valueOf(expenseModel.getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemsClick.onClick(expenseModel);
            }
        });

    }

    @Override
    public int getItemCount() {

        return expense_modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView note,categorgy,amount,date;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            note = itemView.findViewById(R.id.note);
            categorgy = itemView.findViewById(R.id.category);
            amount= itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
        }
    }
}
