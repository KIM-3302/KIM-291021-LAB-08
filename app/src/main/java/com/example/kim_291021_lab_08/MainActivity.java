package com.example.kim_291021_lab_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button buttonAdd, buttonDelete, buttonClear, buttonRead,
            buttonUpdate;
    EditText etName, etEmail, etID;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = (Button) findViewById(R.id.button);
        buttonAdd.setOnClickListener(this);
        buttonRead = (Button) findViewById(R.id.button2);
        buttonRead.setOnClickListener(this);
        buttonClear = (Button) findViewById(R.id.button3);
        buttonClear.setOnClickListener(this);
        buttonUpdate = (Button) findViewById(R.id.button4);
        buttonUpdate.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button5);
        buttonDelete.setOnClickListener(this);

        etID = (EditText) findViewById(R.id.editTextTextPersonName);
        etName = (EditText) findViewById(R.id.editTextTextPersonName2);
        etEmail = (EditText) findViewById(R.id.editTextTextPersonName3);
        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        String ID = etID.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); // класс для добавления новых строк в таблицу
        switch (v.getId())
        {
            case R.id.button:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, email);
                database.insert(DBHelper.TABLE_PERSONS, null,
                        contentValues);
                break;
            case R.id.button2:
                Cursor cursor = database.query(DBHelper.TABLE_PERSONS, null,
                        null, null,
                        null, null, null); // все поля без сортировки и группировки
                if (cursor.moveToFirst())
                {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex =
                            cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex =
                            cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d("mLog", "ID =" + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " +
                                cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                cursor.close(); // освобождение памяти
                break;
            case R.id.button3:
                database.delete(DBHelper.TABLE_PERSONS, null, null);
                break;
            case R.id.button4:
                if (ID.equalsIgnoreCase(""))
            {
                break;
            }
            int delCount = database.delete(DBHelper.TABLE_PERSONS,
                    DBHelper.KEY_ID + "= " + ID, null);
            Log.d("mLog", "Удалено строк = " + delCount);
            case R.id.button5:
                if (ID.equalsIgnoreCase(""))
            {
                break;
            }
            contentValues.put(DBHelper.KEY_MAIL, email);
            contentValues.put(DBHelper.KEY_NAME, name);
            int updCount = database.update(DBHelper.TABLE_PERSONS,
                    contentValues, DBHelper.KEY_ID + "= ?", new String[] {ID});
            Log.d("mLog", "Обновлено строк = " + updCount);
        }
        dbHelper.close(); // закрываем соединение с БД
    }
}