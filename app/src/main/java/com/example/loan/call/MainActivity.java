package com.example.loan.call;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button call=(Button) findViewById(R.id.buttonCall);
        n=(TextView) findViewById(R.id.text);
        load();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
    }
    private void call()
    {
        try
        {
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel: 0974526933"));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Log.e("helloandroid dialing example", "Call failed", e);
        }
    }
    public void load()
    {
        final List<String> phonesList=new ArrayList<>();
        //lay danh ba dien thoai
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNum=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Bitmap photo=loadPhoto(getContentResolver(), Long.valueOf(phoneNum));
           // phonesList.add(new Phone(name,phoneNum,"call",photo));
            phonesList.add(name+"\n"+phoneNum);

        }
        n.setText(phonesList.toString());
    }
    public  static Bitmap loadPhoto(ContentResolver contentResolver, long id)
    {
        Uri uri= ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,id);
        InputStream inputStream=ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,uri);
        if(inputStream!=null)
        {
            return null;
        }
        return BitmapFactory.decodeStream(inputStream);
    }

}
