package com.example.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView itemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        itemsListView = (ListView) findViewById(R.id.itemListView);
        itemsListView.setAdapter(itemsAdapter);

        // mock data

//       items.add("First to do");
//       items.add("Second to do");
        setupListViewListener();


    }

    public void onAddItem(View v) {

        EditText newItem = (EditText) findViewById(R.id.newItemEditText);
        String itemText = newItem.getText().toString();
        itemsAdapter.add(itemText);
        newItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "item added to list", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        Log.i("Main activity", "Setting listner in list view");
        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Main activity", "Item removed in position " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }


    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Error reading the file", e);
            items = new ArrayList<>();
        }
    }


    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error writing the file", e);
        }

    }

}
