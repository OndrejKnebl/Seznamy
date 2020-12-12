package com.example.seznamy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    Dialog myDialog;
    ListView listListView;
    ArrayList<HashMap<Integer, String>> arrayList;
    HashMap<Integer, String> hashmap;

    EditText editListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.popup_new_list);

        editListName = (EditText) myDialog.findViewById(R.id.editListName);


        arrayList = new ArrayList<HashMap<Integer, String>>();
        ArrayList<String> arrayListForView = new ArrayList<String>();
        arrayList = mydb.getItemList();
        hashmap = arrayList.get(0);

        for (Map.Entry me : hashmap.entrySet()) {
            arrayListForView.add(me.getValue().toString());
        }


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayListForView);
        listListView = findViewById(R.id.listView);
        listListView.setAdapter(arrayAdapter);

        listListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            hashmap= arrayList.get(position);

            int i = 0;
            for (Map.Entry me : hashmap.entrySet()) {

                if(i == position) {
                    mydb.deleteList(Integer.parseInt(me.getKey().toString()));
                    break;
                }
                i++;
            }
            
            updateMainActivity();
        }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.import_list) {
            System.out.println(id);
        }

        if(id == R.id.export_list) {
            System.out.println(id);
        }

        return super.onOptionsItemSelected(item);
    }


    public void addList(View view) {
        showPopUp();
    }

    public void showPopUp() {
        myDialog.show();
    }


    public void closeDialog(View view) {
        myDialog.dismiss();
    }

    public void createNewList(View view) {
        if (mydb.createNewList(editListName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_SHORT).show();
        }

        editListName.setText("");
        myDialog.dismiss();
        updateMainActivity();
    }


    private void updateMainActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }

}