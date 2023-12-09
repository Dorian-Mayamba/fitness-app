package uk.ac.aston.cs3mdd.fitnessapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import uk.ac.aston.cs3mdd.fitnessapp.R;

public class EmptyFieldDialogFragment extends DialogFragment {

    private Context context;

    public EmptyFieldDialogFragment(Context ctx){
        this.context = ctx;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle(R.string.empty_field_alert_title);
        return builder.create();
    }
}
