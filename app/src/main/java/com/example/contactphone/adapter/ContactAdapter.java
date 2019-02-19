package com.example.contactphone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contactphone.R;
import com.example.contactphone.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.CustomView> {

    private Context context;
    private List<Contact> arrayList;

    public ContactAdapter(Context context, List<Contact> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_tasks, viewGroup, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView customView, int i) {

        Contact contact = arrayList.get(i);
        customView.textViewName.setText(contact.getName());
        customView.textViewFamily.setText(contact.getFamily());
        customView.textViewPhone.setText(contact.getPhone());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CustomView extends RecyclerView.ViewHolder {
        TextView textViewName, textViewFamily, textViewPhone;

        CustomView(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewFamily = itemView.findViewById(R.id.textViewFamily);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
        }
    }
}
