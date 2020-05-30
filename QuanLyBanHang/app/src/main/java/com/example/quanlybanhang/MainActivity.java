package com.example.quanlybanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.DBAdapter.CustomerDBAdapter;
import com.example.quanlybanhang.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner sp_name;
    private Spinner sp_product;
    private Spinner sp_date;
    private Spinner sp_month;
    private Spinner sp_year;
    private EditText edit_amount;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnExit;
    private ListView lv;
    List<String> list_name = new ArrayList<>();
    List<String> list_product = new ArrayList<>();
    List<Integer> list_date = new ArrayList<>(), list_month = new ArrayList<>(), list_year = new ArrayList<>();
    List<Customer> list = new ArrayList<>();
    ConstraintLayout pre_constraintLayout;

    CustomerAdapter customerAdapter;
    CustomerDBAdapter customerDBAdapter;
    //giá trị của Customer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
        initData(list_name, list_product, list_date, list_month, list_year);
        setAdapterToViews();

        customerDBAdapter = new CustomerDBAdapter(this);
        loadDataToListViewFromDB();
    }

    private void setAdapterToViews() {
        //name
        ArrayAdapter<String> name_adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout, list_name);
        name_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sp_name.setAdapter(name_adapter);

        //product
        ArrayAdapter<String> product_adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout, list_product);
        product_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sp_product.setAdapter(product_adapter);

        //date
        ArrayAdapter<Integer> date_adapter = new ArrayAdapter<Integer>(this, R.layout.custom_spinner_layout, list_date);
        date_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sp_date.setAdapter(date_adapter);

        //month
        ArrayAdapter<Integer> month_adapter = new ArrayAdapter<Integer>(this, R.layout.custom_spinner_layout, list_month);
        month_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sp_month.setAdapter(month_adapter);

        //year
        ArrayAdapter<Integer> year_adapter = new ArrayAdapter<Integer>(this, R.layout.custom_spinner_layout, list_year);
        year_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sp_year.setAdapter(year_adapter);
    }

    private void initData(List<String> name, List<String> product, List<Integer> date, List<Integer> month, List<Integer> year) {
        name.add("Lê Tánh Sang");
        name.add("Nguyễn Quang");
        name.add("Nguyễn Bá");
        name.add("Nguyễn Thị Quỳnh");
        product.add("VSmart");
        product.add("Đèn học");
        product.add("Bàn nhựa");
        product.add("Xe máy");
        product.add("Vinfast");
        product.add("Cafe");
        for (int d = 1; d <= 31; d++) {
            date.add(d);
        }
        for (int m = 1; m <= 12; m++) {
            month.add(m);
        }
        for (int y = 2000; y < 2030; y++) {
            year.add(y);
        }
    }

    private void setControl() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        sp_name = findViewById(R.id.cb_customer);
        sp_product = findViewById(R.id.cb_product);
        sp_date = findViewById(R.id.date);
        sp_month = findViewById(R.id.month);
        edit_amount = findViewById(R.id.edit_amout);
        sp_year = findViewById(R.id.year);
        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnEdit = findViewById(R.id.btn_edit);
        btnExit = findViewById(R.id.btn_exit);

        //thay the toolbar thanh action bar
        toolbar.setTitle("Xin chào!");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //thiet lap mot so gia tri mac dinh
        btnDelete.setTag("");
        btnEdit.setTag("");
    }

    private void setEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //thiet lap mau nen item khi chon
                if (pre_constraintLayout != null) {
                    pre_constraintLayout.setBackgroundColor(Color.WHITE);
                }
                ConstraintLayout constraintLayout = view.findViewById(R.id.one_item);
                constraintLayout.setBackgroundColor(Color.LTGRAY);
                pre_constraintLayout = constraintLayout;

                TextView name = view.findViewById(R.id.tv_name);
                TextView product = view.findViewById(R.id.tv_product);
                TextView date = view.findViewById(R.id.tv_date_time);
                TextView amount = view.findViewById(R.id.tv_amount);
                TextView itemId = view.findViewById(R.id.itemId);

                sp_name.setSelection(getIndex(sp_name, name.getText().toString()));
                sp_product.setSelection(getIndex(sp_product, product.getText().toString()));
                String[] dmy = date.getText().toString().split("/");
                sp_date.setSelection(getIndex(sp_date, dmy[0]));
                sp_month.setSelection(getIndex(sp_month, dmy[1]));
                sp_year.setSelection(getIndex(sp_year, dmy[2]));
                edit_amount.setText(amount.getText().toString());

                //luu lại vị trí của ỉtem trong nút xóa
                String compose_pos_id = position + "/" + itemId.getText().toString();
                btnDelete.setTag(compose_pos_id);
                btnEdit.setTag(compose_pos_id);

                Toast.makeText(getApplicationContext(), "Đã chọn item thứ " + position, Toast.LENGTH_SHORT).show();
            }

            private int getIndex(Spinner spinner, String myString) {
                for (int i = 0; i < spinner.getCount(); i++) {
                    if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                        return i;
                    }
                }
                return 0;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data = getDataFromViews();
                if (data[3].length() == 0){
                    Toast.makeText(getApplicationContext(), "Số lượng không được bỏ trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Customer customer = new Customer(data[0], data[1], data[2], Integer.parseInt(data[3]));
                list.add(customer);

                //Thêm vào trong cơ sở dữ liệu
                long id = customerDBAdapter.insertData(customer.getName(), customer.getProduct(), customer.getDate(), customer.getAmount());
                if (id < 0) {
                    Toast.makeText(getApplicationContext(), "Thêm dữ liệu vào database bị lỗi!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm dữ liệu vào database thành công!", Toast.LENGTH_SHORT).show();
                }

                loadDataToListViewFromDB();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận thoát");
                builder.setMessage("Bạn có thực sự muốn thoát ứng dụng?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });

                builder.setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create();
                builder.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String compose_pos_id = btnDelete.getTag().toString();
                if (compose_pos_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "Chưa chọn item để xóa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận xoá");
                builder.setMessage("Bạn có đồng ý xóa không?");

                builder.setPositiveButton("Đồng ý nha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //xóa item trong list và database
                        String[] pos_id = compose_pos_id.split("/");
                        int pos = Integer.parseInt(pos_id[0]);
                        int id = Integer.parseInt(pos_id[1]);
                        list.remove(pos);
                        //xóa item trong database
                        customerDBAdapter.delete(id);
                        //thiet lap lai gia tri tag cho button
                        btnDelete.setTag("");

                        loadDataToListView();
                        Toast.makeText(getApplicationContext(), "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Trở lại đi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create();
                builder.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String compose_pos_id = btnEdit.getTag().toString();
                if (compose_pos_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "Chưa chọn item để sửa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] pos_id = compose_pos_id.split("/");
                int id = Integer.parseInt(pos_id[1]);

                String[] data = getDataFromViews();
                if (data[3].length() == 0){
                    Toast.makeText(getApplicationContext(), "Số lượng không được bỏ trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                customerDBAdapter.updateName(id, data[0], data[1], data[2], Integer.parseInt(data[3]));

                loadDataToListViewFromDB();

                Toast.makeText(getApplicationContext(), "Đã sửa thành công!", Toast.LENGTH_SHORT).show();
                btnEdit.setTag("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Đã chọn item 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Đã chọn item 2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Đã chọn item 3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.user:
                Toast.makeText(this, "Đã chọn item user", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String[] getDataFromViews() {
        String[] data = new String[4];
        data[0] = sp_name.getSelectedItem().toString();
        data[1] = sp_product.getSelectedItem().toString();
        data[2] = sp_date.getSelectedItem() + "/" + sp_month.getSelectedItem() + "/" + sp_year.getSelectedItem();
        data[3] = edit_amount.getText().toString();
        return data;
    }

    private void loadDataToListView() {
        customerAdapter = new CustomerAdapter(getApplicationContext(), list);
        lv.setAdapter(customerAdapter);
    }

    private void loadDataToListViewFromDB() {
        list.clear();
        ArrayList<Customer> customers = customerDBAdapter.getData();
        for (Customer customer : customers) {
            switch (customer.getProduct()) {
                case "VSmart":
                    customer.setProductImg(R.drawable.vsmart);
                    break;
                case "Đèn học":
                    customer.setProductImg(R.drawable.lamp);
                    break;
                case "Bàn nhựa":
                    customer.setProductImg(R.drawable.bannhua);
                    break;
                case "Vinfast":
                    customer.setProductImg(R.drawable.vinfast);
                    break;
                case "Cafe":
                    customer.setProductImg(R.drawable.coffee);
                    break;
                default:
                    break;
            }
        }
        list.addAll(customers);
        loadDataToListView();
    }
}
