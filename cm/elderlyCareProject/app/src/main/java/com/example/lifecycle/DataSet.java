package com.example.lifecycle;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSet implements Parcelable {

    int temporature;
    int purse;
    int gps;
    String statement;
    String user_name;

    // 1. 생성자 정의
    // 1.1 Default 생성자 정의
    public DataSet(String name,String stat,int temp,int pur, int g){
        user_name = name;
        statement = stat;
        temporature = temp;
        purse = pur;
        gps = g;
    }
    // 1.2 Parcel 객체 읽기
    public DataSet(Parcel src){
        user_name = src.readString();
        statement = src.readString();
        temporature = src.readInt();
        purse = src.readInt();
        gps = src.readInt();


    }
    // 2. CREATOR 상수 정의!!
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            // 3. DataSet 생성자를 호출해 Parcel 객체에서 읽어 return!!
            return new DataSet(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new DataSet[size];
        }
    };
    // 4. describeContents() : 직렬화하려는 객체의 유형을 구분할때 사용!!
    // 여기선 그냥 0 반환으로..
    public int describeContents(){
        return 0;
    }
    // 5. Parcel 객체로 쓰기(write) ㄱㄱ!!
    public void writeToParcel(Parcel dest, int flag){
        dest.writeString(user_name);
        dest.writeString(statement);
        dest.writeInt(temporature);
        dest.writeInt(purse);
        dest.writeInt(gps);

    }
}
