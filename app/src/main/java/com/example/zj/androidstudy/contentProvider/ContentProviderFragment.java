package com.example.zj.androidstudy.contentProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderFragment extends BaseFragment {
    public static final int REQUEST_CODE = 1;

    private Button mBtnGetContact;
    private Button mBtnQueryProvider;
    private ListView mContactsListView;
    private ArrayAdapter<String> mArrayAdapter;
    private List<String> mContactsList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return init(inflater.inflate(R.layout.fragment_contentprovider, container, false));
    }

    @Override
    protected void initViews(View view) {
        mBtnGetContact = view.findViewById(R.id.btn_get_contact);
        mBtnQueryProvider = view.findViewById(R.id.btn_query_provider);
        mContactsListView = view.findViewById(R.id.lv_contacts);
        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mContactsList);
        mContactsListView.setAdapter(mArrayAdapter);

        mBtnGetContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_CONTACTS }, REQUEST_CODE);
                } else {
                    readContacts();
                }
            }
        });

        mBtnQueryProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryProvider();
            }
        });

        view.findViewById(R.id.btn_insert_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBook();
            }
        });
        view.findViewById(R.id.btn_update_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });
        view.findViewById(R.id.btn_delete_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    @Override
    protected void initWorkers() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null);
                if (cursor == null) {
                    return;
                }
                mContactsList.clear();
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mContactsList.add(displayName + "\n" + phoneNumber);
                }
                mArrayAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    private void queryProvider() {
        mContactsList.clear();

        new Thread(new Runnable() {
          @Override
          public void run() {
            Uri bookUri = Uri.parse("content://com.example.bookprovider/book");
            Cursor bookCursor = getActivity().getContentResolver().query(bookUri,
                new String[] {"_id", "name"}, null, null, null);
            while (bookCursor.moveToNext()) {
              mContactsList.add("Book index: " + bookCursor.getInt(0)
                  + "  name: " + bookCursor.getString(1));
            }
            bookCursor.close();

            Uri userUri = Uri.parse("content://com.example.bookprovider/user");
            Cursor userCursor = getActivity().getContentResolver().query(userUri,
                new String[] {"_id", "name", "sex"}, null, null, null);
            while (userCursor.moveToNext()) {
              mContactsList.add("User index: " + userCursor.getInt(0)
                  + "  name: " + userCursor.getString(1) + "  sex: "
                  + userCursor.getInt(2));
            }
            userCursor.close();

            getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                mArrayAdapter.notifyDataSetChanged();
              }
            });
          }
        }).start();
    }

    private void insertBook() {
        Uri bookUri = Uri.parse("content://com.example.bookprovider/book");
        ContentValues values = new ContentValues();
        values.put("_id", "6");
        values.put("name", "Kotlin");
        getActivity().getContentResolver().insert(bookUri, values);
    }

    private void updateBook() {
        Uri bookUri = Uri.parse("content://com.example.bookprovider/book");
        ContentValues values = new ContentValues();
        values.put("_id", "6");
        values.put("name", "Java");
        getActivity().getContentResolver().update(bookUri, values, "_id=?", new String[]{"6"});
    }

    private void deleteBook() {
        Uri bookUri = Uri.parse("content://com.example.bookprovider/book");
        getActivity().getContentResolver().delete(bookUri, "_id=?", new String[]{"6"});
    }
}
