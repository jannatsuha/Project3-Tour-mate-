package com.group.avengers.tourmate.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.group.avengers.tourmate.Classes.Expense;
import com.group.avengers.tourmate.R;

import java.util.List;

public class AllExpenseAdapter extends RecyclerView.Adapter<AllExpenseAdapter.ViewholderAdapter>{

    private List<Expense> expenses;

    public AllExpenseAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public ViewholderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.expense_list_show,parent,false);
        return new ViewholderAdapter(view);
    }

    @Override
    public void onBindViewHolder(ViewholderAdapter holder, int position) {
        holder.amount.setText(expenses.get(position).getAmount());
        holder.comment.setText(expenses.get(position).getComment());
        holder.date.setText(expenses.get(position).getTimeDate());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ViewholderAdapter extends RecyclerView.ViewHolder{

        TextView amount;
        TextView comment;
        TextView date;

        public ViewholderAdapter(View itemView) {
            super(itemView);

            amount=itemView.findViewById(R.id.expAmountShow);
            comment=itemView.findViewById(R.id.expCommentShow);
            date=itemView.findViewById(R.id.expDateShow);
        }
    }
}


