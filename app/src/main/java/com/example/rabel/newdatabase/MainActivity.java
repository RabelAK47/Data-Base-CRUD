package com.example.rabel.newdatabase;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper myDb;
    EditText eId, eName, eSurname, eMarks;
    Button btnData, btnView, btnUpdate, btnDelete;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        eName = (EditText) findViewById(R.id.name);
        eSurname = (EditText) findViewById(R.id.sur_name);
        eMarks = (EditText) findViewById(R.id.marks);
        eId = (EditText) findViewById(R.id.id);

        btnData = (Button) findViewById(R.id.addBtn);
        btnView = (Button) findViewById(R.id.viewBtn);
        btnUpdate = (Button) findViewById(R.id.updateBtn);
        btnDelete = (Button) findViewById(R.id.delBtn);
        txtView = (TextView) findViewById(R.id.txtV);

        addData();
        viewData();
        updateData();
        deleteData();
    }

    void addData()
    {
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = eName.getText().toString();
                String _Surname = eSurname.getText().toString();
                String _eMarks = eMarks.getText().toString();

                boolean isInserted = myDb.insertData(_name, _Surname, _eMarks);

                if(isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data is Not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    void viewData()
    {
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();
                if(cursor.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this, "Error, Nothing Found", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext())
                    {
                        buffer.append("ID: " + cursor.getString(0) + "\n");
                        buffer.append("Name: " + cursor.getString(1) + "\n");
                        buffer.append("SurName: " + cursor.getString(2) + "\n");
                        buffer.append("Marks: " + cursor.getString(3) + "\n\n");
                    }
                    String ans = buffer.toString();
                    txtView.setText(ans);
                }
            }
        });
    }

    void updateData()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = eId.getText().toString();
                String _eName = eName.getText().toString();
                String _eSurName = eSurname.getText().toString();
                String _marks = eMarks.getText().toString();
                boolean isUpdate = myDb.updateData(_id, _eName, _eSurName, _marks);

                if(isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data is not updated", Toast.LENGTH_LONG).show();
            }
        });
    }
    void deleteData()
    {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _del = eId.getText().toString();
                Integer deleteRows = myDb.deleteData(_del);
                if(deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Error, Data is not deleted!", Toast.LENGTH_LONG).show();

            }
        });
    }
}
