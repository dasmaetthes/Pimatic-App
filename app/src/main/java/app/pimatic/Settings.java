package app.pimatic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String ipAdress = sharedPreferences.getString("IP", null);
        EditText editText = (EditText)findViewById(R.id.ipInput);
        editText.setText(ipAdress, TextView.BufferType.EDITABLE);

        Button acceptSettings = findViewById(R.id.acceptSettings);

        acceptSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText ipAdress = findViewById(R.id.ipInput);
                String ipAdressStrg= ipAdress.getEditableText().toString();
                SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("IP", ipAdressStrg);
                editor.commit();

                Intent settingsIntent = new Intent(Settings.this, Pimatic.class);
                startActivity(settingsIntent);
            }
        });
    }

}
