package com.example.quanlybanhang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.Model.Customer;

import java.util.List;

public class CustomerAdapter extends BaseAdapter {

    private Context context;
    private List<Customer> list;

    CustomerAdapter(Context context, List<Customer> list) {
        this.context = context;
        this.list = list;
    }

    public CustomerAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);

        ImageView image = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.tv_name);
        TextView product = convertView.findViewById(R.id.tv_product);
        TextView dateTime = convertView.findViewById(R.id.tv_date_time);
        TextView amount = convertView.findViewById(R.id.tv_amount);
        TextView itemId = convertView.findViewById(R.id.itemId);

        Customer customer = list.get(position);
        image.setImageResource(customer.getProductImg());
        name.setText(customer.getName());
        product.setText(customer.getProduct());
        dateTime.setText(customer.getDate());
        amount.setText(customer.getAmount().toString());
        itemId.setText(customer.getId().toString());

        return convertView;
    }
}
