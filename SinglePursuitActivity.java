package com.vipromos.pursuit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class SinglePursuitActivity extends AppCompatActivity {

    String mPursuitString;
    Pursuit mPursuit;
    private PursuitDatabase mPursuitDB;
    List<Pursuit> pursuitList;
    private Context cntx;

    EditText benefitsInput;
    EditText endGoalInput;
    EditText actionInput;
    int priorityInput = 0;

    TextView mWhy;
    TextView mHow;
    TextView mWhere;

    TextView mActionDisplay;
    EditText mNewActionInput;
    Button mCompleted;
    Button mAddAction;
    TextView mAction;
    ChipGroup rankNewActionTime;
    int successCountShort;
    int successCountMed;
    int successCountLong;
    TextView SCS;
    TextView SCM;
    TextView SCL;

    TextView SClabel;
    TextView SCSlabel;
    TextView SCMlabel;
    TextView SCLlabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPursuitDB = PursuitDatabase.getInstance(getApplicationContext());
        cntx = getApplicationContext();

        Intent intent = getIntent();
        mPursuitString = intent.getStringExtra("pursuit");
        setTitle(mPursuitString);

        pursuitList = mPursuitDB.pursuitDao().getPursuit();
        String checkString;
        Pursuit pursuitCheck;
        for(int i = 0; i < pursuitList.size(); i++) {
            pursuitCheck = pursuitList.get(i);
            checkString = pursuitCheck.getText();
            if(checkString.equals(mPursuitString)) {
                mPursuit = pursuitList.get(i);
                break;
            }
        }
        if(mPursuit.getCreated()==true) {
            setSinglepursuitCreatedView();


        }
        else setContentView(R.layout.activity_single_pursuit_input);

        benefitsInput = findViewById(R.id.pursuit_benefits_input);
        endGoalInput = findViewById(R.id.pursuit_goal_input);
        actionInput = findViewById(R.id.pursuit_action_input);


    }

    public void setSinglepursuitCreatedView() {
        setContentView(R.layout.activity_single_pursuit);
        mWhy = (TextView)findViewById(R.id.pursuit_why_answer);
        String pWhy = mPursuit.getWhy();
        mWhy.setText(pWhy/*mPursuit.getWhy()*/);
        mHow = findViewById(R.id.pursuit_action_answer);
        mHow.setText(mPursuit.getAction());
        String check = mPursuit.getAction();
        mWhere = findViewById(R.id.pursuit_where_answer);
        mWhere.setText(mPursuit.getGoal());
        successCountShort = mPursuit.getSuccessCountShort();
        successCountMed = mPursuit.getSuccessCountMed();
        successCountLong = mPursuit.getSuccessCountLong();

        SCS = findViewById(R.id.pursuit_shortaction_display);
        SCSlabel = findViewById(R.id.pursuit_shortaction_label);

        SCM = findViewById(R.id.pursuit_medaction_display);
        SCMlabel = findViewById(R.id.pursuit_medaction_label);

        SCL = findViewById(R.id.pursuit_longaction_display);
        SCLlabel = findViewById(R.id.pursuit_longaction_label);

        String tempS = String.valueOf(successCountShort);
        String tempM = String.valueOf(successCountMed);
        String tempL = String.valueOf(successCountLong);
        SCS.setText(tempS);
        SCM.setText(tempM);
        SCL.setText(tempL);

        SClabel = findViewById(R.id.pursuit_successcount_label);
        if(successCountShort > 0) {
            SClabel.setVisibility(View.VISIBLE);
            SCSlabel.setVisibility(View.VISIBLE);
            SCS.setVisibility(View.VISIBLE);
        }
        if(successCountMed > 0) {
            SClabel.setVisibility(View.VISIBLE);
            SCM.setVisibility(View.VISIBLE);
            SCMlabel.setVisibility(View.VISIBLE);
        }
        if(successCountLong > 0) {
            SClabel.setVisibility(View.VISIBLE);
            SCLlabel.setVisibility(View.VISIBLE);
            SCL.setVisibility(View.VISIBLE);
        }

    }

    public void finishedActionClick(View view) {
        mCompleted = findViewById(R.id.completed_button);
        mCompleted.setVisibility(View.INVISIBLE);
        mAddAction = findViewById(R.id.new_action_button);
        mAddAction.setVisibility(View.VISIBLE);
        mAction = findViewById(R.id.pursuit_action);
        mAction.setText(R.string.new_action);
        mActionDisplay = findViewById(R.id.pursuit_action_answer);
        mNewActionInput = findViewById(R.id.pursuit_new_action_input);
        mNewActionInput.setVisibility(View.VISIBLE);
        mActionDisplay.setVisibility(View.INVISIBLE);
        mAction.setVisibility(View.VISIBLE);
        rankNewActionTime = findViewById(R.id.pursuit_new_time_input);
        rankNewActionTime.setVisibility(View.VISIBLE);

        if(mPursuit.getPriority()==1) {
            successCountShort = mPursuit.getSuccessCountShort();
            successCountShort++;
            mPursuit.setSuccessCountShort(successCountShort);
        }
        if(mPursuit.getPriority()==2) {
            successCountMed = mPursuit.getSuccessCountMed();
            successCountMed++;
            mPursuit.setSuccessCountMed(successCountMed);
        }
        if(mPursuit.getPriority()==3) {
            successCountLong = mPursuit.getSuccessCountLong();
            successCountLong++;
            mPursuit.setSuccessCountLong(successCountLong);
        }
        mPursuitDB.pursuitDao().updatePursuit(mPursuit);

    }

    public void addNewActionClick(View view) {

        if(!TextUtils.isEmpty(mNewActionInput.getText())) {
            //mPursuit.setAction(mNewActionInput.getText().toString());
            String check = mNewActionInput.getText().toString();
            mPursuit.setAction(check);
            mPursuit.setPriority(priorityInput);
            mPursuitDB.pursuitDao().updatePursuit(mPursuit);

           // mPursuit.setAction(actionInput.getText().toString());
            mPursuit.setPriority(priorityInput);

            mAction.setText(R.string.how);
            mActionDisplay.setText(mPursuit.getAction());
            mNewActionInput.setVisibility(View.INVISIBLE);
            mActionDisplay.setVisibility(View.VISIBLE);
            mAddAction.setVisibility(View.INVISIBLE);
            mCompleted.setVisibility(View.VISIBLE);
            rankNewActionTime.setVisibility(View.INVISIBLE);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            //setSinglepursuitCreatedView();
        }
    }

    public void completedPursuitClick(final View view) {
        if(mPursuit.getCreated() == true) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.accomplished_pursuit);
            builder.setMessage(R.string.accomplished_pursuit_info);

            builder.setPositiveButton(R.string.yes_excited, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    int trueFlag = 1;
                    mPursuit.setCompleted(trueFlag);
                    mPursuitDB.pursuitDao().updatePursuit(mPursuit);
                    Intent intent = new Intent(view.getContext(), MultiplePursuitActivity.class);
                    view.getContext().startActivity(intent);

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.not_yet, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    public void createdPursuitClick(View view) {
        if(mPursuit.getCreated() == true) {
            onBackPressed();
            return;
        }
        if(benefitsInput.getText()!=null&&endGoalInput.getText()!=null&&actionInput.getText()!=null&&priorityInput!=0) {
            //mPursuitDB.pursuitDao().updatePursuit(mPursuit);
            mPursuit.setWhy(benefitsInput.getText().toString());
            mPursuit.setGoal(endGoalInput.getText().toString());
            mPursuit.setAction(actionInput.getText().toString());
            mPursuit.setPriority(priorityInput);
            mPursuit.setCreated(true);
            mPursuitDB.pursuitDao().updatePursuit(mPursuit);
            setSinglepursuitCreatedView();
            Toast.makeText(cntx, R.string.created_pursuit_toast, Toast.LENGTH_LONG).show();

        }
        else Toast.makeText(cntx, "Complete All Feilds", Toast.LENGTH_LONG).show();

    }
    public void onPrioOne(View v) {
        if(priorityInput!=1) {
            priorityInput = 1;
        }
        else priorityInput = 0;
    }
    public void onPrioTwo(View v) {
        if(priorityInput!=2) {
            priorityInput = 2;
        }
        else priorityInput = 0;
    }
    public void onPrioThree(View v) {
        if(priorityInput!=3) {
            priorityInput = 3;
        }
        else priorityInput = 0;
    }

}
