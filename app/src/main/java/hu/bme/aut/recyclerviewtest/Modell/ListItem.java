package hu.bme.aut.recyclerviewtest.Modell;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "listitem")
public class ListItem implements Parcelable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "imageResource")
    public int imageResource;

    @ColumnInfo(name = "lNev")
    public String lNev;

    @ColumnInfo(name = "lVaros")
    public String lVaros;

    @ColumnInfo(name = "Ratings")
    public ArrayList<Integer> Ratings = new ArrayList<>();

    @ColumnInfo(name = "Comments")
    public ArrayList<Comment> Comments = new ArrayList<>();

    public ListItem(int im, String nev, String varos){
        imageResource = im;
        lNev = nev;
        lVaros = varos;
    }

    public ListItem(int imageResource, String lNev, String lVaros, ArrayList<Integer> Ratings, ArrayList<Comment> Comments){
        this.imageResource = imageResource;
        this.lNev = lNev;
        this.lVaros = lVaros;
        this.Ratings = Ratings;
        this.Comments = Comments;
    }

    protected ListItem(Parcel in) {
        imageResource = in.readInt();
        lNev = in.readString();
        lVaros = in.readString();
        //noinspection unchecked
        Ratings = in.readArrayList(Integer.class.getClassLoader());
        //noinspection unchecked
        Comments = in.readArrayList(Comment.class.getClassLoader());
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public void addRating(int r) {Ratings.add(r);}
    public void addComment(Comment c) {Comments.add(c);}

    public int numOfGivenRating(int gv){
        int n = 0;

        for (int i : Ratings) {
            if (i == gv) n++;
        }

        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeString(lNev);
        dest.writeString(lVaros);
        dest.writeList(Ratings);
        dest.writeList(Comments);
    }
}
