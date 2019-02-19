package com.example.contactphone.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.contactphone.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
