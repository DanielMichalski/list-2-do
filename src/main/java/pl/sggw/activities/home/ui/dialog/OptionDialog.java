package pl.sggw.activities.home.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * @author Daniel Michalski
 * @date 07.11.12
 */

public class OptionDialog extends AlertDialog {

    public OptionDialog(Context context) {
        super(context);

        setTitle("Wybierz czynność");
        setMessage("Co chcesz zrobić?");

        setButton("Pokaż", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Pokaż", Toast.LENGTH_SHORT).show();
            }
        });

        setButton2("Edytuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Edytuj", Toast.LENGTH_LONG).show();
            }
        });

        setButton3("Usun", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Usuń", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
