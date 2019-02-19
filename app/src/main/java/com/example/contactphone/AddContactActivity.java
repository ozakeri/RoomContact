package com.example.contactphone;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactphone.database.DataBaseClint;
import com.example.contactphone.model.Contact;

public class AddContactActivity extends AppCompatActivity {

    EditText editTextName, editTextFamily, editTextPhone;
    Button button;
    String name, family, phone;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        editTextName = findViewById(R.id.editTextName);
        editTextFamily = findViewById(R.id.editTextFamily);
        editTextPhone = findViewById(R.id.editTextPhone);
        button = findViewById(R.id.button_save);

        contact = (Contact) getIntent().getSerializableExtra("contact");

        if (contact != null) {
            editTextName.setText(contact.getName());
            editTextFamily.setText(contact.getFamily());
            editTextPhone.setText(contact.getPhone());
        }

        if (contact != null) {
            button.setText("update");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        final String name = editTextName.getText().toString().trim();
        final String family = editTextFamily.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("name required");
            editTextName.requestFocus();
        }

        if (family.isEmpty()) {
            editTextFamily.setError("family required");
            editTextFamily.requestFocus();
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("phone required");
            editTextPhone.requestFocus();
        }

        class ContactAdd extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Contact contact = new Contact();
                contact.setName(name);
                contact.setFamily(family);
                contact.setPhone(phone);
                DataBaseClint.getInstance(getApplicationContext()).getAppDataBase().contactDao().addContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        class ContactUpdate extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                contact.setName(name);
                contact.setFamily(family);
                contact.setPhone(phone);
                DataBaseClint.getInstance(getApplicationContext()).getAppDataBase().contactDao().updateContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_LONG).show();
            }
        }

        if (contact != null) {
            ContactUpdate contactUpdate = new ContactUpdate();
            contactUpdate.execute();
        } else {
            ContactAdd contactAdd = new ContactAdd();
            contactAdd.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteContact(contact);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void deleteContact(final Contact contact) {
        class DeleteContact extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DataBaseClint.getInstance(getApplicationContext()).getAppDataBase().contactDao().deleteContact(contact);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
            }
        }

        DeleteContact deleteContact = new DeleteContact();
        deleteContact.execute();
    }
}
