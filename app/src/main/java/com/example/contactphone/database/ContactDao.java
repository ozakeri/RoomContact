package com.example.contactphone.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.Context;

import com.example.contactphone.model.Contact;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE phone = :number")
    public Contact getPhoneByNumber(String number);

    //@Query("SELECT * FROM contact WHERE name LIKE :name")
    @Query("SELECT * FROM contact WHERE name LIKE '%' || :name || '%'")
    List<Contact> getNameByKeyWord(String name);

    @Insert
    void addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);
}
