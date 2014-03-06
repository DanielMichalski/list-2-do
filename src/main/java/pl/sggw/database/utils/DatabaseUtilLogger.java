package pl.sggw.database.utils;

import android.database.Cursor;
import android.util.Log;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

public class DatabaseUtilLogger {
    /**
     * Ta metoda pobiera całą zawartość kurosra (Cursor) i wyświetla ją.
     */
    public static void LogCursorInfo(Cursor c, String debugTag) {
        Log.i(debugTag, "*** Początek kursora *** " + " Wyników:" + c.getCount() + " Kolumn: " + c.getColumnCount());

        // Wyświetlenie nazw kolumn.
        String rowHeaders = "|| ";
        for (int i = 0; i < c.getColumnCount(); i++) {

            rowHeaders = rowHeaders.concat(c.getColumnName(i) + " || ");
        }
        Log.i(debugTag, "KOLUMNY " + rowHeaders);

        // Wyświetlenie rekordów.
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            String rowResults = "|| ";
            for (int i = 0; i < c.getColumnCount(); i++) {
                rowResults = rowResults.concat(c.getString(i) + " || ");
            }
            Log.i(debugTag, "Wiersz " + c.getPosition() + ": " + rowResults);

            c.moveToNext();
        }
        Log.i(debugTag, "*** Koniec kursora ***");
    }
}
