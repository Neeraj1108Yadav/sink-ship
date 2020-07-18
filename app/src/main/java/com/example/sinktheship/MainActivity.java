package com.example.sinktheship;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
                                                               ShipListener.ShipView {

    private EditText editText_shipCount,editText_shipLoot;
    private TextView textView_loot,textView_days;
    private ShipListener.ShipPresenter presenter;
    private Button button_shipCount,button_loot,button_startWar,button_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_shipCount = findViewById(R.id.editText_shipCount);
        editText_shipLoot = findViewById(R.id.editText_shipLoot);
        textView_loot = findViewById(R.id.textView_loot);
        textView_days = findViewById(R.id.textView_days);
        button_shipCount = findViewById(R.id.button_shipCount);
        button_loot = findViewById(R.id.button_loot);
        button_startWar = findViewById(R.id.button_startWar);
        button_reset = findViewById(R.id.button_reset);
        presenter = new PresenterShip(this);

        button_shipCount.setOnClickListener(this);
        button_loot.setOnClickListener(this);
        button_startWar.setOnClickListener(this);
        button_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_shipCount:
                if(!editText_shipCount.getText().toString().isEmpty() &&
                   Integer.parseInt(editText_shipCount.getText().toString()) != 0 &&
                   Integer.parseInt(editText_shipCount.getText().toString()) != 1){
                    presenter.inputMaxPirateShip(Integer.parseInt(editText_shipCount.getText().toString()));
                }else{
                    Toast.makeText(MainActivity.this,"Minimum 2 ships required",Toast.LENGTH_LONG).show();
                }
                 break;
            case R.id.button_loot:
                if(!editText_shipLoot.getText().toString().isEmpty()){
                    presenter.enterShipLoot(Integer.parseInt(editText_shipLoot.getText().toString()));
                }
                break;
            case R.id.button_startWar:
                button_startWar.setEnabled(true);
                button_startWar.setClickable(true);
                button_startWar.setBackgroundColor(getResources().getColor(R.color.hint_color));
                button_shipCount.setTextColor(Color.BLACK);
                presenter.startWar();
                break;
            case R.id.button_reset:
                textView_days.setText("");
                disableButtons();
                presenter.reset();
                 break;

        }
    }

    @Override
    public void onSetShipLoot(int shipCount,int shipLoot,int maxShip) {
        if(shipCount == 1){
            textView_loot.setText("["+shipLoot+", ");
        }else if(shipCount < maxShip){
            textView_loot.append(String.valueOf(shipLoot)+", ");
        }else if(shipCount == maxShip){
            textView_loot.append(String.valueOf(shipLoot)+"]");
            button_loot.setEnabled(false);
            button_loot.setClickable(false);
            button_startWar.setEnabled(true);
            button_startWar.setClickable(true);
            button_loot.setBackgroundColor(getResources().getColor(R.color.hint_color));
            button_loot.setTextColor(Color.BLACK);
            button_startWar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            button_startWar.setTextColor(getResources().getColor(R.color.darkTextColor));
            editText_shipLoot.setEnabled(false);
        }
        editText_shipLoot.setText("");
    }

    @Override
    public void getWarResult(int resultDays) {
        if(resultDays == 1){
            textView_days.setText(getResources().getString(R.string.war_end)+" "+resultDays+" "+getResources().getString(R.string.day));
        }else if(resultDays > 1){
            textView_days.setText(getResources().getString(R.string.war_end)+" "+resultDays+" "+getResources().getString(R.string.days));
        }else{
            textView_days.setText(getResources().getString(R.string.no_war));
        }
    }

    @Override
    public void onSetMaxShip() {
        hideShow(true);
        button_shipCount.setEnabled(false);
        button_shipCount.setClickable(false);
        button_startWar.setEnabled(false);
        button_startWar.setClickable(false);
        button_shipCount.setBackgroundColor(getResources().getColor(R.color.hint_color));
        button_startWar.setBackgroundColor(getResources().getColor(R.color.hint_color));
        button_shipCount.setTextColor(Color.BLACK);
        button_startWar.setTextColor(Color.BLACK);
        editText_shipCount.setEnabled(false);
    }

    private void disableButtons(){
        hideShow(false);
        button_shipCount.setEnabled(true);
        button_shipCount.setClickable(true);
        button_loot.setClickable(true);
        button_loot.setEnabled(true);
        button_shipCount.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        button_loot.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        button_shipCount.setTextColor(getResources().getColor(R.color.darkTextColor));
        button_loot.setTextColor(getResources().getColor(R.color.darkTextColor));
        editText_shipCount.setEnabled(true);
        editText_shipLoot.setEnabled(true);
        editText_shipLoot.setText("");
        editText_shipCount.setText("");
        textView_loot.setText("");
    }

    private void hideShow(boolean isShow){
        button_loot.setVisibility(isShow ? View.VISIBLE : View.GONE);
        editText_shipLoot.setVisibility(isShow ? View.VISIBLE : View.GONE);
        button_startWar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        textView_loot.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}