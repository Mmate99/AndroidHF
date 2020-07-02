package hu.bme.aut.recyclerviewtest.Modell.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import hu.bme.aut.recyclerviewtest.Modell.ListItem;

@Database(
        entities = {ListItem.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({ListItemDao.Converters.class})
public abstract class UtazasiKisokosDatabase extends RoomDatabase {
    public abstract ListItemDao listitemdao();
}
