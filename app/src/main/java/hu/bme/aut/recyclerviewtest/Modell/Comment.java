package hu.bme.aut.recyclerviewtest.Modell;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private String Nev;
    private String Szoveg;

    public Comment(String n, String sz){
        Nev = "-" + n;
        Szoveg = sz;
    }

    public Comment(String sz){
        Nev = "-Anonymus";
        Szoveg = sz;
    }

    protected Comment(Parcel in) {
        Nev = in.readString();
        Szoveg = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getNev() {
        return Nev;
    }

    public String getSzoveg() {
        return Szoveg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Nev);
        dest.writeString(Szoveg);
    }
}
