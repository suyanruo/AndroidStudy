package com.example.zj.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.zj.androidstudy.model.AuthorInfo;

public class Book implements Parcelable {
    public String name;
    public int price;
    // 实现Serializable接口的变量
    public AuthorInfo authorInfo;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
        this.authorInfo = new AuthorInfo("Unknown", 20);
    }

    public Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
        authorInfo = (AuthorInfo) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeSerializable(authorInfo);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        price = dest.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
