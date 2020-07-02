package hu.bme.aut.recyclerviewtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hu.bme.aut.recyclerviewtest.Modell.Comment;
import hu.bme.aut.recyclerviewtest.Modell.ListItem;
import hu.bme.aut.recyclerviewtest.R;
import hu.bme.aut.recyclerviewtest.Modell.DataBase.UtazasiKisokosDatabase;

public class RatingActivity extends AppCompatActivity {

    private TextView tv_nev;
    private TextView tv_varos;
    private RadioGroup rg_ertekeles;
    private EditText et_nev;
    private EditText et_komment;
    private Button btn_mentes;
    private Button btn_megse;

    private static UtazasiKisokosDatabase database = MainActivity.getDatabase();
    private static List<ListItem> itemList = MainActivity.getItemList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        setupComps();

        Intent intent = getIntent();
        final int pos = intent.getIntExtra("list pos", 999);
        final ListItem listItem = itemList.get(pos);
        final SightActivity sActivity = intent.getParcelableExtra("SightActivity");

        if (listItem != null) {
            tv_nev.setText(listItem.lNev);
            tv_varos.setText(listItem.lVaros);
        }
        else Toast.makeText(RatingActivity.this, "Elem nem található!!", Toast.LENGTH_SHORT).show();

        btn_mentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRatings(listItem);
                saveComments(listItem);

                listItemChanged(listItem);

                sActivity.finish();
                Intent intent = new Intent(RatingActivity.this, SightActivity.class);
                intent.putExtra("list pos", pos);
                startActivity(intent);
            }
        });

        btn_megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupComps(){
        tv_nev = findViewById(R.id.tv_r_nev);
        tv_varos = findViewById(R.id.tv_r_varos);
        rg_ertekeles = findViewById(R.id.rg_Rating);
        et_nev = findViewById(R.id.et_Nev);
        et_komment = findViewById(R.id.et_Ertekeles);
        btn_mentes = findViewById(R.id.btn_mentes);
        btn_megse = findViewById(R.id.btn_megse);
    }

    private void saveRatings(ListItem li){
        int rating = rg_ertekeles.getCheckedRadioButtonId();

        switch (rating){
            case R.id.rb_NagyonJo:
                li.addRating(4);
                break;
            case R.id.rb_ElegJo:
                li.addRating(3);
                break;
            case R.id.rb_Semleges:
                li.addRating(2);
                break;
            case R.id.rb_SohaTobbet:
                li.addRating(1);
                break;
        }
    }

    private void saveComments(ListItem li){
        String nev = et_nev.getText().toString();
        String komment = et_komment.getText().toString();

        finish();
        if (!komment.isEmpty()) {

            if (!nev.isEmpty()) li.addComment(new Comment(nev, komment));

            else li.addComment(new Comment(komment));
        }
    }

    private static void listItemChanged(final ListItem li){
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.listitemdao().update(li);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }
}
