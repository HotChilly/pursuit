package com.vipromos.pursuit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CompletedPursuitActivity extends AppCompatActivity {

    List<Pursuit> pursuitList;
    List<String> pursuitDisplayArray = new ArrayList<>();;
    private PursuitDatabase mPursuitDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_pursuits);

        mPursuitDB = PursuitDatabase.getInstance(getApplicationContext());
        pursuitList = mPursuitDB.pursuitDao().getPursuitsNewerFirst(1);

        String pursuitNameHolder;
        Pursuit singlePursuitHolder;

        for(int i = 0; i < pursuitList.size(); i++) {
            singlePursuitHolder = pursuitList.get(i);
            pursuitNameHolder = singlePursuitHolder.getText();
            pursuitDisplayArray.add(pursuitNameHolder);
        }


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_completed_pursuit_listview, pursuitDisplayArray);

        ListView listView = (ListView) findViewById(R.id.completed_pursuit_list);
        listView.setAdapter(adapter);
    }

    public void doneCompletedPursuitView(View view) {

    }

}
