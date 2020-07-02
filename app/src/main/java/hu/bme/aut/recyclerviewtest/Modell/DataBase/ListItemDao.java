package hu.bme.aut.recyclerviewtest.Modell.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.Update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.recyclerviewtest.Modell.Comment;
import hu.bme.aut.recyclerviewtest.Modell.ListItem;

@Dao
public interface ListItemDao {
    @Query("SELECT * FROM listitem")
    List<ListItem> getAll();

    @Insert
    long insert(ListItem listItem);

    @Update
    void update(ListItem listItem);

    class Converters {

        @TypeConverter
        public ArrayList<Integer> fromString(String val){
            Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
            return new Gson().fromJson(val, listType);
        }

        @TypeConverter
        public String fromArrayList(ArrayList<Integer> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }

        @TypeConverter
        public ArrayList<Comment> fromString2(String val){
            Type listType = new TypeToken<ArrayList<Comment>>() {}.getType();
            return new Gson().fromJson(val, listType);
        }

        @TypeConverter
        public String fromArrayList2(ArrayList<Comment> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }
    }
}
