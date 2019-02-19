package com.example.contactphone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contactphone.adapter.ContactAdapter;
import com.example.contactphone.database.DataBaseClint;
import com.example.contactphone.model.Contact;
import com.example.contactphone.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    List<Contact> arrayList = new ArrayList<>();
    EditText searchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        floatingActionButton = findViewById(R.id.floating_button_add);
        searchTextView = findViewById(R.id.searchTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadContactList();

        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchKey = searchTextView.getText().toString();
                searchContactList(searchKey);

            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", arrayList.get(position));
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadContactList() {

        class LoadContact extends AsyncTask<Void, Void, List<Contact>> {

            @Override
            protected List<Contact> doInBackground(Void... voids) {
                arrayList = DataBaseClint.getInstance(getApplicationContext()).getAppDataBase().contactDao().getAll();
                return arrayList;
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                super.onPostExecute(contacts);
                recyclerView.setAdapter(new ContactAdapter(getApplicationContext(), contacts));
            }
        }

        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }

    private void searchContactList(final String keyWord) {

        class SearchContact extends AsyncTask<Void, Void, List<Contact>> {

            @Override
            protected List<Contact> doInBackground(Void... voids) {
                arrayList = DataBaseClint.getInstance(getApplicationContext()).getAppDataBase().contactDao().getNameByKeyWord(keyWord);
                return arrayList;
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                super.onPostExecute(contacts);
                recyclerView.setAdapter(new ContactAdapter(getApplicationContext(), contacts));
            }
        }

        SearchContact searchContact = new SearchContact();
        searchContact.execute();
    }
}
