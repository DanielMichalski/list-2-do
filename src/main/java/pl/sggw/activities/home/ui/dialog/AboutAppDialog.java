package pl.sggw.activities.home.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * @author Daniel Michalski
 * @date 15.11.12
 */

public class AboutAppDialog extends AlertDialog {

    public AboutAppDialog(Context context) {
        super(context);
        setTitle("Tu powinno być info o aplikacji");
    }
}
