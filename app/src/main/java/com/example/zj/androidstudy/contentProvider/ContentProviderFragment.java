package com.example.zj.androidstudy.contentProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderFragment extends BaseFragment {
    private ListView mContactsListView;
    private ArrayAdapter<String> mArrayAdapter;
    private List<String> mContactsList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return init(inflater.inflate(R.layout.fragment_contentprovider, container, false));
    }

    @Override
    protected void initViews(View view) {
        mContactsListView = view.findViewById(R.id.lv_contacts);
        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mContactsList);
        mContactsListView.setAdapter(mArrayAdapter);
    }

    public static int REQUEST_CODE = 1;
    @Override
    protected void initWorkers() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_CONTACTS }, REQUEST_CODE);
        } else {
            readContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(getActivity(), "Permission Denyed!", Toast.LENGTH_SHORT).show();
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
                if (cursor == null) return;
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

}
