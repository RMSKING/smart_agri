package com.example.smart_agri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class Settings extends AppCompatActivity {

    private View parentView;
    private SwitchMaterial themeswitch;
    TextView themeTv,titleTV;

    private Usersettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

      /*  settings = (Usersettings)getApplication();

        initWidgets();
        loadSharedPreferences();
        initSwitchListener();

    }

    private void initSwitchListener() {
        themeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                if(checked)
                    settings.setCustomTheme(Usersettings.DARK_THEME);
                else
                    settings.setCustomTheme(Usersettings.LIGHT_THEME);
                    SharedPreferences.Editor editor = getSharedPreferences(Usersettings.PREFERENCES,MODE_PRIVATE).edit();
                    editor.putString(Usersettings.CUSTOM_THEME,settings.getCustomTheme());
                    editor.apply();
                    updateView();

                }

            private void updateView() {
                final int black = ContextCompat.getColor(this,R.color.black);
                final int white = ContextCompat.getColor(this,R.color.white);

                if(settings.getCustomTheme().equals(Usersettings.DARK_THEME))
                {
                    titleTV.setTextColor(white);
                    themeTv.setTextColor(white);
                    themeTv.setText("Dark");
                    parentView.setBackground(black);
                    themeswitch.setChecked(true);
                }else {
                    titleTV.setTextColor(black);
                    themeTv.setTextColor(black);
                    themeTv.setText("Dark");
                    parentView.setBackground(white);
                    themeswitch.setChecked(false);
                }
            }
        });
        }


    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Usersettings.PREFERENCES,MODE_PRIVATE);
        String theme = sharedPreferences.getString(Usersettings.CUSTOM_THEME,Usersettings.LIGHT_THEME);
        settings.setCustomTheme(theme);

    }

    private void initWidgets() {
        themeTv=findViewById(R.id.themeTV);
        titleTV=findViewById(R.id.titletv);
        themeswitch=findViewById(R.id.themeswitch);
        parentView=findViewById(R.id.parentview);*/
    }
}