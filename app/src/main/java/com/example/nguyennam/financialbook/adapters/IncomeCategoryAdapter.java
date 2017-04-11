package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;

import java.util.List;

public class IncomeCategoryAdapter extends RecyclerView.Adapter<IncomeCategoryAdapter.IncomeCategoryViewHolder> {

    Context context;
    List<String> data;

    public IncomeCategoryAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public IncomeCategoryAdapter.IncomeCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_incomecategory_item, parent, false);
        IncomeCategoryViewHolder myViewHolder = new IncomeCategoryViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(IncomeCategoryAdapter.IncomeCategoryViewHolder holder, int position) {
        String incomeCategory = data.get(position);
        holder.txtIncomeCategory.setText(incomeCategory);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class IncomeCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtIncomeCategory;
        public IncomeCategoryViewHolder(View itemView) {
            super(itemView);
            txtIncomeCategory = (TextView) itemView.findViewById(R.id.txtIncomeCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface IncomeCategoryOnClickListener {
        void onClick(int position);
    }

    IncomeCategoryOnClickListener myOnClickListener;

    public void setMyOnClickListener(IncomeCategoryOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
