package com.vipromos.pursuit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultiplePursuitActivity extends AppCompatActivity implements PursuitDialogFragment.OnPursuitEnteredListener {

    public RecyclerView recyclerView;
    public PursuitAdapter adapter;

    List<Pursuit> pursuitList;
    private PursuitDatabase mPursuitDB;
    private Context cntx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_pursuits);

        cntx = getApplicationContext();

        pursuitList = new ArrayList<>();
        mPursuitDB = PursuitDatabase.getInstance(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.pursuitRecyclerView);
        recyclerView.setHasFixedSize(true);
        // Create 2 grid layout columns
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new PursuitAdapter(this, loadPursuits());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.multiple_pursuit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(MultiplePursuitActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.completed:
                Intent intent2 = new Intent(MultiplePursuitActivity.this, CompletedPursuitActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //get pursuits from the Pursuit database
    private List<Pursuit> loadPursuits() {
            return mPursuitDB.pursuitDao().getPursuitsOlderFirst(0);
    }

    public void addPursuitClick(View view) {
        // Prompt user to type new subject
        FragmentManager manager = getSupportFragmentManager();
        PursuitDialogFragment dialog = new PursuitDialogFragment();
        dialog.show(manager, "pursuitDialog");
    }
    @Override
    public void onPursuitEntered(String pursuit) {
        // Returns pursuit entered in the SubjectDialogFragment dialog
        if (pursuit.length() > 0) {
            Pursuit newPursuit = new Pursuit(pursuit);

            //exception is thrown to handle adding duplicate

            pursuitList = mPursuitDB.pursuitDao().getPursuit();
            String checkDup;
            for(int i = 0; i < pursuitList.size(); i++) {
                checkDup = pursuitList.get(i).getText();

                if(checkDup.equals(pursuit)) {
                    Toast.makeText(cntx, R.string.duplicate_pursuit_toast, Toast.LENGTH_LONG).show();
                    return;
                }
            }

            mPursuitDB.pursuitDao().insertPursuit(newPursuit);
            //pursuitList.add(newPursuit);
            onResume();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Load subjects here in case settings changed
        adapter = new PursuitAdapter(this, loadPursuits());
        recyclerView.setAdapter(adapter);
    }
}
