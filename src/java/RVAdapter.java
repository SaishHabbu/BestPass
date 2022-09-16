package com.example.bestpass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestpass.databinding.EachRvBinding;

import javax.security.auth.callback.Callback;

public class RVAdapter extends ListAdapter<Password,RVAdapter.ViewHolder> {
    public  RVAdapter(MainActivity mainActivity) {
        super(CALLBACK);
    }
        private static final DiffUtil.ItemCallback<Password>CALLBACK=new DiffUtil.ItemCallback<Password>() {
            @Override
            public boolean areItemsTheSame(@NonNull Password oldItem, @NonNull Password newItem) {
                return oldItem.getId()== newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Password oldItem, @NonNull Password newItem) {
                return oldItem.getSigninName().equals(newItem.getSigninName())
                        && oldItem.getUsername().equals(newItem.getUsername())
                        && oldItem.getPassword().equals(newItem.getPassword());
            }


    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_rv,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Password password =  getItem(position);
        holder.binding.siginname.setText(password.getSigninName());
        holder.binding.username.setText(password.getUsername());
        holder.binding.password.setText(password.getPassword());

    }
    public  Password getPassword(int position){
        return  getItem(position);

    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        EachRvBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= EachRvBinding.bind(itemView);
        }
    }
}
