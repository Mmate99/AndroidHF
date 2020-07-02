package hu.bme.aut.recyclerviewtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.recyclerviewtest.Modell.Comment;
import hu.bme.aut.recyclerviewtest.Modell.ListItem;
import hu.bme.aut.recyclerviewtest.R;

public class SightActivity extends AppCompatActivity implements Parcelable {

    private static final int NAGYON_JO = 4;
    private static final int ELEG_JO = 3;
    private static final int SEMLEGES = 2;
    private static final int SOHA_TOBBET = 1;

    private ImageView iv;
    private TextView tvNev;
    private TextView tvVaros;
    private PieChart pcRatings;
    private ArrayList<Comment> comments;
    private FloatingActionButton addComment;

    private static List<ListItem> itemList = MainActivity.getItemList();

    public SightActivity(){}

    protected SightActivity(Parcel in) {
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<SightActivity> CREATOR = new Creator<SightActivity>() {
        @Override
        public SightActivity createFromParcel(Parcel in) {
            return new SightActivity(in);
        }

        @Override
        public SightActivity[] newArray(int size) {
            return new SightActivity[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight);

        iv = findViewById(R.id.s_im);
        tvNev = findViewById(R.id.s_nev);
        tvVaros = findViewById(R.id.s_varos);
        pcRatings = findViewById(R.id.s_chart);
        addComment = findViewById(R.id.fab);

        Intent intent = getIntent();
        final int pos = intent.getIntExtra("list pos", 999);
        final ListItem listItem = itemList.get(pos);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SightActivity.this, RatingActivity.class);
                intent.putExtra("list pos", pos);
                intent.putExtra("SightActivity", SightActivity.this);
                startActivity(intent);
            }
        });

        if (listItem != null) {
            iv.setImageResource(listItem.imageResource);
            tvNev.setText(listItem.lNev);
            tvVaros.setText(listItem.lVaros);
            comments = listItem.Comments;
            loadRatings(listItem);
        }
        else Toast.makeText(SightActivity.this, "Elem nem található!!", Toast.LENGTH_SHORT).show();

        loadComments(comments);
    }

    private void loadRatings(ListItem li){
        pcRatings = findViewById(R.id.s_chart);
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(li.numOfGivenRating(NAGYON_JO), "Nagyon jó!"));
        entries.add(new PieEntry(li.numOfGivenRating(ELEG_JO), "Elég jó!"));
        entries.add(new PieEntry(li.numOfGivenRating(SEMLEGES), "Semleges!"));
        entries.add(new PieEntry(li.numOfGivenRating(SOHA_TOBBET), "Soha többet!"));

        PieDataSet dataSet = new PieDataSet(entries, "Ratings");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueTextSize(0);

        PieData data = new PieData(dataSet);
        pcRatings.setData(data);
        pcRatings.invalidate();

        pcRatings.getDescription().setEnabled(false);
        pcRatings.getLegend().setEnabled(false);
        pcRatings.setEntryLabelTextSize(13);
        pcRatings.setEntryLabelColor(Color.BLACK);
    }

    private void loadComments(ArrayList<Comment> coms){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < coms.size(); i++){
            LinearLayout layout = findViewById(R.id.comment_list);
            View commentView = inflater.inflate(R.layout.comment_row, null);
            TextView tv_nev = commentView.findViewById(R.id.tv_comment_nev);
            TextView tv_szoveg = commentView.findViewById(R.id.tv_comment_szoveg);
            tv_nev.setText(coms.get(i).getNev());
            tv_szoveg.setText(coms.get(i).getSzoveg());
            layout.addView(commentView);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(comments);
    }
}