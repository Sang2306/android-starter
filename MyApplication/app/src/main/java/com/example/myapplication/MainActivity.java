package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private CustomAdapter customAdapter;
    private ArrayList<Model> modelArrayList;
    private int[] myImageList = new int[]{
            R.drawable.benz,
            R.drawable.bike,
            R.drawable.car,
            R.drawable.carrera,
            R.drawable.ferrari,
            R.drawable.harly,
            R.drawable.lamborghini,
            R.drawable.silver
    };
    private String[] myImageNameList = new String[]{
            "Benz",
            "Bike",
            "Car",
            "Carrera",
            "Ferrari",
            "Harly",
            "Lamborghini",
            "Silver"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview);
        modelArrayList = populateList();
        customAdapter = new CustomAdapter(this, modelArrayList);
        lv.setAdapter(customAdapter);

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(lv),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                customAdapter.remove(position);
                            }
                        });

        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Position " + position, LENGTH_SHORT).show();
                }
            }
        });

    }

    private ArrayList<Model> populateList() {

        ArrayList<Model> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Model model = new Model();
            model.setName(myImageNameList[i]);
            model.setImage_drawable(myImageList[i]);
            list.add(model);
        }

        return list;
    }
}
