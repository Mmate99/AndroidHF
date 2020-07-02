package hu.bme.aut.recyclerviewtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.recyclerviewtest.Modell.Comment;
import hu.bme.aut.recyclerviewtest.Modell.Visual.ListAdapter;
import hu.bme.aut.recyclerviewtest.Modell.ListItem;
import hu.bme.aut.recyclerviewtest.R;
import hu.bme.aut.recyclerviewtest.Modell.DataBase.UtazasiKisokosDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private static ListAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private static List<ListItem> itemList;
    private static UtazasiKisokosDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(
                getApplicationContext(),
                UtazasiKisokosDatabase.class,
                "UtazasiKisokos"
        ).build();

        loadItemsInBackground();

        initRecycleView();

        final EditText et_filter = findViewById(R.id.etSearch);

        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
    }

    private void initRecycleView() {
        rView = findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rAdapter = new ListAdapter(itemList);
        rLayoutManager = new LinearLayoutManager(this);

        rView.setAdapter(rAdapter);
        rView.setLayoutManager(rLayoutManager);

        rAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, SightActivity.class);
                intent.putExtra("list pos", position);
                startActivity(intent);
            }
        });
    }

    private void search(String s) {
        ArrayList<ListItem> filteredList = new ArrayList<>();

        for (ListItem i : itemList) {
            if (i.lNev.toLowerCase().contains(s.toLowerCase()) || i.lVaros.toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(i);
            }
        }

        rAdapter.filteredList(filteredList);
    }

    private static void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<ListItem>>() {

            @Override
            protected List<ListItem> doInBackground(Void... voids) {
                itemList = database.listitemdao().getAll();
                if (itemList.size() == 0){
                    initList();
                }
                return itemList;
            }

            @Override
            protected void onPostExecute(List<ListItem> itemList) {
                rAdapter.update(itemList);
            }
        }.execute();
    }

    public static void addItemToList(final ListItem newItem) {
        new AsyncTask<Void, Void, ListItem>() {

            @Override
            protected ListItem doInBackground(Void... voids) {
                newItem.id = database.listitemdao().insert(newItem);
                itemList.add(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(ListItem listItem) {
                rAdapter.addItem(listItem);
            }
        }.execute();
    }

    public static List<ListItem> getItemList() {
        return itemList;
    }

    public static UtazasiKisokosDatabase getDatabase() {
        return database;
    }

    private static void initList() {
        ListItem citadella = new ListItem(R.drawable.citadella, "Citadella", "Budapest");
        citadella.addComment(new Comment("Bojan Milosavic", "Very nice looking fortress in Budapest, one of the symbol of the city. Historic place with a very good view. This place is must see when you visit Budapest. I was there twice, and never disappointed. Beautiful touristic place. However, recommend visit place."));
        citadella.addComment(new Comment("Alexander Hiker", "Szép a panoráma. Belátni Pest nagy részét. 18 évvel ezelőtt voltam, de már akkor is elég fárasztónak tünt gyalog feljutnom. De megérte. Azok számára, akik rendszeresen túráznak a hegyekben, szerintem nem jelent majd gondot, ez a kis mászás. Bár éjszaka már abban az időben sem volt ajánlatos kóborolni odafent, így csak félig feljutva láthattam az éjszakai fényeket. Nappal, tiszta időben szuper. Szépen rá lehet látni a Dunára is. Terveztem, hogy egyszer megnézem onnan a tüzijátékot."));
        citadella.addComment(new Comment("Renáta Revóczky", "És fenn egy nívós karácsonyi vásár is fogadott. Sajnos az erőd belseje nem volt látogatható."));
        citadella.addRating(4);
        citadella.addRating(3);
        citadella.addRating(3);
        citadella.addRating(3);
        citadella.addRating(5);
        citadella.addRating(5);
        citadella.addRating(5);
        citadella.addRating(5);
        citadella.addRating(5);
        citadella.addRating(5);
        citadella.addRating(2);
        citadella.addRating(2);
        citadella.addRating(1);
        citadella.addRating(1);

        ListItem parlament = new ListItem(R.drawable.parlament, "Parlament", "Budapest");
        parlament.addComment(new Comment("Csikós Dávid", "Véleményem szerint a világ legszebb épülete! Ezredik megtekintésre is gyönyörű."));
        parlament.addComment(new Comment("Kis Béla", "Nem véletlenül látogatják ennyien az egyenlőre Magyarország legmagasabb(96 méteres) csodaszép épületet! Hatalmas élmény, mindenkinek ajánlom."));
        parlament.addComment(new Comment("Nagy Áron", "Csodás építmény. Nem láttam még ennél szebb országházat."));
        parlament.addRating(4);
        parlament.addRating(3);
        parlament.addRating(3);
        parlament.addRating(3);
        parlament.addRating(5);
        parlament.addRating(5);
        parlament.addRating(5);
        parlament.addRating(5);
        parlament.addRating(5);
        parlament.addRating(5);
        parlament.addRating(2);
        parlament.addRating(2);
        parlament.addRating(1);
        parlament.addRating(1);

        ListItem dom = new ListItem(R.drawable.dom, "Dóm", "Szeged");
        dom.addComment(new Comment("Luca Thaly", "Ehhez nem igazán tudok mit mondani. Jelenleg felújítás alatt van,de oldalról be lehet menni ingyen és meg lehet nézni belülről. Csodaszép. Ezen kívűl pesig (értelemszerűen) fizetős idegenvezetés is van ami rejtettebb helyekre is enged bekukkantást(ezen mi nem vettünk részt csak láttunk más csoportokat ilyenen részt venni) kívülről is nagyon szép a templom.\n"));
        dom.addComment(new Comment("Attila Szabó", "Aki most látogat Szegedre és akarja megnézni a Szegedi Dómot az egy kívül belül felállványozott épületet lát. Ám a restaurálásokat és renoválásokat, illetve a beltéri fűtés korszerűsítést követően egy csodálatosan megújuló Fogadalmi Templom fogja fogadni a hívőket és túristákat. Az ország egyik legszebb hangzású és egyik legnagyobb orgonája található itt. Aki szép időben vállaja, hogy felmegy a toronyba, azt csodálatos kilátás várja. A templom előtti \"Dömötör torony\" jelzi, hogy ebben a városban milyen régóta él a keresztény vallásos hit. A templom előtti tér Közép-Európa egyik legszebb tere."));
        dom.addRating(4);
        dom.addRating(3);
        dom.addRating(3);
        dom.addRating(3);
        dom.addRating(5);
        dom.addRating(5);
        dom.addRating(5);
        dom.addRating(5);
        dom.addRating(5);
        dom.addRating(5);
        dom.addRating(2);
        dom.addRating(2);
        dom.addRating(1);
        dom.addRating(1);

        ListItem cifra = new ListItem(R.drawable.cifra, "Cifra Palota", "Kecskemét");
        cifra.addComment(new Comment("Angela Grof", "Gyönyörű, teljesen elvarázsolt minket a hely, aki itt jár, mindenképpen térjen be."));
        cifra.addComment(new Comment("Tibor Hidvégi", "Nagyon szép kívülről és belül is rendezett képet mutat. A kiállítások érdekesek és informatívak. A belépőjegyek nem túl drágák, a rendezvények megérik a pénzüket."));
        cifra.addRating(4);
        cifra.addRating(3);
        cifra.addRating(3);
        cifra.addRating(3);
        cifra.addRating(5);
        cifra.addRating(5);
        cifra.addRating(5);
        cifra.addRating(5);
        cifra.addRating(5);
        cifra.addRating(5);
        cifra.addRating(2);
        cifra.addRating(2);
        cifra.addRating(1);
        cifra.addRating(1);

        ListItem sumeg = new ListItem(R.drawable.sumeg, "Sümegi Vár", "Sümeg");
        sumeg.addComment(new Comment("István Kovács", "Magával ragadó ! Körbe járod, egy pillanatra elképzeled , hogy várvédő vagy és ha már választhatok, kovács lennék, vagy szakács. És napokig fogva tart a gondolat, milyen nehéz lehetett nekik az ostrom alatt."));
        sumeg.addComment(new Comment("Komlósi Andrea", "Kb. 20 éve jártam itt utoljára és a Sümegi vár akkor is csodálatos volt, de ennyi év után természetesen megváltozott a környék,de ha lehet ez a vár azóta mégszebb lett:)Egy élmény volt a vár meglátogatása s nagyon szépen fel lett újítva a környék is, ajánlom mindenkinek, hogy jöjjön el ide. Köszönjük az élményt!"));
        sumeg.addComment(new Comment("Imre Attila Kovács", "Már többször jártam itt, de nem lehet megunni. Mindig van valamilyen érdekes program vagy látványos bemutató. Nem is beszélve a várból kinézve a gyönyörű látványról ami igazán megigézi az embert és órákig eltudna nézelődni. Csak ajánlani tudom mindenkinek."));
        sumeg.addRating(4);
        sumeg.addRating(3);
        sumeg.addRating(3);
        sumeg.addRating(3);
        sumeg.addRating(5);
        sumeg.addRating(5);
        sumeg.addRating(5);
        sumeg.addRating(5);
        sumeg.addRating(5);
        sumeg.addRating(5);
        sumeg.addRating(2);
        sumeg.addRating(2);
        sumeg.addRating(1);
        sumeg.addRating(1);

        addItemToList(citadella);
        addItemToList(parlament);
        addItemToList(dom);
        addItemToList(cifra);
        addItemToList(sumeg);
    }
}
