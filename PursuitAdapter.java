package com.vipromos.pursuit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

//recyclerview.adapter
//receclerview.ViewHolder
public class PursuitAdapter extends RecyclerView.Adapter<PursuitAdapter.PursuitViewHolder> {

    private Context mContext;
    private List<Pursuit> pursuitList;
    AdapterView.OnItemClickListener mItemClickListener;
    private Pursuit mSelectedPursuit;
    private int mSelectedPursuitPosition = RecyclerView.NO_POSITION;
    private ActionMode mActionMode = null;
    private PursuitDatabase mPursuitDB;// = PursuitDatabase.getInstance(mContext);
    Pursuit mPursuit;


    public PursuitAdapter(Context mContext, List<Pursuit> pursuitList) {
        this.mContext = mContext;
        mPursuitDB = PursuitDatabase.getInstance(mContext);
        this.pursuitList = pursuitList;
    }

    public void removePursuit(Pursuit pursuit) {
        // Find pursuit in the list
        int index = pursuitList.indexOf(pursuit);
        if (index >= 0) {
            // Remove the pursuit
            pursuitList.remove(index);

            // Notify adapter of pursuit removal
            notifyItemRemoved(index);
        }
    }

    @NonNull
    @Override
    public PursuitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_view_items, null);
        PursuitViewHolder holder = new PursuitViewHolder(view);
        //view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PursuitViewHolder holder, int position) {
        //Pursuit pursuit =
        mPursuit = pursuitList.get(position);

        if(mSelectedPursuitPosition == position) {
            holder.textViewTitle.setBackgroundColor(Color.RED);
        }
        else {

            holder.textViewTitle.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPursuitCardBackground));
        }


        holder.textViewTitle.setText(mPursuit.getText());
        //holder.textViewTitle.setBackgroundColor((holder.textViewTitle.getResources().getColor(R.color.colorPursuitCardBackground)));

    }

    @Override
    public int getItemCount() {
        return pursuitList.size();
    }

    class PursuitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        TextView textViewTitle;

        public PursuitViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            textViewTitle = itemView.findViewById(R.id.pursuitTextView);
            textViewTitle.setBackgroundColor(textViewTitle.getResources().getColor(R.color.colorPursuitCardBackground));
            textViewTitle.setTextColor(textViewTitle.getResources().getColor(R.color.colorPursuitCardText));
        }


        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), SinglePursuitActivity.class);
            intent.putExtra("pursuit", textViewTitle.getText());
            v.getContext().startActivity(intent);

        }

        public void bind(Pursuit pursuit, int position) {

        }

        @Override
        public boolean onLongClick(View view) {
            if (mActionMode != null) {
                return false;
            }
            mSelectedPursuitPosition = getAdapterPosition();
            mSelectedPursuit = pursuitList.get(mSelectedPursuitPosition);
            // Re-bind the selected item
            notifyDataSetChanged();
            // Show the CAB
            mActionMode = ((Activity)mContext).startActionMode(mActionModeCallback);
            return true;

        }


    }
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Provide context menu for CAB
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // Process action item selection
            switch (item.getItemId()) {
                case R.id.delete:
                    // Delete from the database and remove from the RecyclerView
                    PursuitAdapter.this.removePursuit(mSelectedPursuit);
                    //going to save all pursuits for now, will add clear databse option to options later
                    //mPursuitDB.pursuitDao().deletePursuit(mSelectedPursuit);
                    // Close the CAB
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;

            // CAB closing, need to deselect item if not deleted
            notifyItemChanged(mSelectedPursuitPosition);
            mSelectedPursuitPosition = RecyclerView.NO_POSITION;
        }
    };

}
