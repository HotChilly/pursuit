package com.vipromos.pursuit;
//Code to create fragment that will hold ialog, asking user for input and taking input to create a new persuit
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PursuitDialogFragment extends DialogFragment  {

    // Host activity must implement
    public interface OnPursuitEnteredListener {
        void onPursuitEntered(String subject);
    }

    private OnPursuitEnteredListener mListener;

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        final EditText pursuitEditText = new EditText(getActivity());
        pursuitEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        pursuitEditText.setMaxLines(1);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_pursuit)
                .setView(pursuitEditText)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String pursuit = pursuitEditText.getText().toString();
                        mListener.onPursuitEntered(pursuit.trim());
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnPursuitEnteredListener) context;
    }
}
