package com.pedan.trainermemory;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
// Создаем свой класс. Для удобства хранения данных о кнопочке
class My_buttons extends Button {
    boolean press = false;
    boolean end_btn = false;
    int number_press = -1;
    public My_buttons(Context context) {
        super(context);
    }
}

public class Game_screen extends AppCompatActivity implements View.OnTouchListener {
    int x_col = 4;
    int y_col = 4;
    int xy_num = 0;
    int col_number = 3;
    int this_number=0;
    int press = 0;
    int score = 100;
    List<Integer> scenario;
    TextView levelText, textScore, textMem;
    SoundPool sp;
    int melodyAlarm, melodyClick, melodyPobeda, melodyRing, melodyPop;


    TableLayout game_table;
    Button btn_start;
    private List<My_buttons> list_buttons;
    private MotionEvent motionEvent;

    // Функция, которая готовит игровое поле *******************************************************************************
    public void clear_game(){
        game_table.removeAllViews();
        list_buttons = new ArrayList<My_buttons>();
        scenario = new ArrayList<Integer>();
        for (int i=0; i<y_col; i++){
            TableRow myRow = new TableRow(this);
            myRow.setLayoutParams(new TableLayout.LayoutParams(
                    MATCH_PARENT,
                    MATCH_PARENT,
                    1
            ));

            for (int j=0; j<x_col; j++) {
                My_buttons mybtn;
                mybtn = new My_buttons(this);
                TableRow.LayoutParams lParam = new TableRow.LayoutParams(
                        MATCH_PARENT,
                        MATCH_PARENT,
                        1
                );
                lParam.setMargins(2,2,2,2);
                xy_num++;
                mybtn.setBackgroundColor(Color.GRAY);
                mybtn.setText(Integer.toString(xy_num));
                mybtn.setEnabled(false);
                mybtn.setOnTouchListener(this);
                mybtn.setId(11+xy_num);
                list_buttons.add(mybtn);
                myRow.addView(mybtn,lParam);
            }
            game_table.addView(myRow,i);
        }

    }
    // Функция, которая показывает какие кнопочки нужно нажать **************************************************************************************
    public void on_light_button() {

        final Handler handler1 = new Handler();
      //  ik=0;
        final Random random = new Random();
        int p_curr;
        press=0;
        for (int i=0; i<list_buttons.size();i++) {
            list_buttons.get(i).end_btn=false;
            list_buttons.get(i).number_press=-1;
            list_buttons.get(i).setEnabled(true);
            list_buttons.get(i).setBackgroundColor(Color.GRAY);
            list_buttons.get(i).setTextColor(Color.BLACK);
        }
        scenario.clear();
        for (int ik=0; ik<col_number; ik++) {


            final int finalIk = ik;
            int cur_random = random.nextInt(list_buttons.size()-1);
            while (list_buttons.get(cur_random).number_press>0) cur_random = random.nextInt(list_buttons.size()-1);
            final int cur = cur_random;

            //list_buttons.get(cur).number_press=ik;
            scenario.add(cur);


            if (ik==col_number-1) {btn_start.setEnabled(false); list_buttons.get(cur).end_btn=true;}
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    sp.play(melodyPop,1,1,1,0,1);
                    list_buttons.get(cur).setBackgroundColor(Color.YELLOW);
                    list_buttons.get(cur).setTextColor(Color.RED);

                }
            }, 1000*ik);
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    list_buttons.get(cur).setBackgroundColor(Color.GRAY);
                    list_buttons.get(cur).setTextColor(Color.BLACK);

                }
            }, 1000*ik+1000);
            //list_buttons.get(ik).setBackgroundColor(Color.GRAY);

        }
        textMem.setText("Вспомнили "+Integer.toString(press)+" из "+ Integer.toString(col_number));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        game_table = findViewById(R.id.game_table);
        btn_start=findViewById(R.id.button20);
        levelText=findViewById(R.id.levelText);
        textScore=findViewById(R.id.textScore);
        textMem=findViewById(R.id.bystby);
        clear_game();
        btn_start.setOnTouchListener(this);
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        melodyAlarm = sp.load(this, R.raw.alarm, 1);
        melodyClick = sp.load(this, R.raw.click,1);
        melodyPobeda = sp.load(this, R.raw.pobeda,1);
        melodyRing = sp.load(this, R.raw.car,1);
        melodyPop = sp.load(this, R.raw.pop,1);

    }





    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.motionEvent = motionEvent;
        for (int i=0; i<list_buttons.size(); i++) {

            if((view.getId() == list_buttons.get(i).getId()) && (motionEvent.getAction() == ACTION_DOWN)) {



                if (i!=scenario.get(press)) {
                    list_buttons.get(i).setBackgroundColor(Color.RED);
                    sp.play(melodyAlarm,1,1,1,0,1);
                    Toast toast = Toast.makeText(Game_screen.this, "Вы ошиблись!!!. Попробуйте еще раз", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    score=score-2;
                    textScore.setText("Очки: "+Integer.toString(score));
                } else {
                    list_buttons.get(i).setBackgroundColor(Color.GREEN);
                    // Музон *************************************************************************
                    sp.play(melodyRing,1,1,1,0,1);
                    // Всплывающее сообщение *********************************************************
                  //  Toast toast = Toast.makeText(Game_screen.this, "ВЕРНО!!! Это кнопка "+Integer.toString(i+1), Toast.LENGTH_SHORT);
                   // toast.setGravity(Gravity.CENTER,0,0);
                  //  toast.show();
                    // Переход к следующей кнопке *****************************************************
                    press++;
                    textMem.setText("Вспомнили "+Integer.toString(press)+" из "+ Integer.toString(col_number));
                    // Зачисление баллов *************************************************************
                    score=score+10;
                    // Обновляем инфу о баллах ******************************************************
                    textScore.setText("Очки: "+Integer.toString(score));
                    if (scenario.size()==press) {

                        press=0;
                        // Переходим на следующий уровень ********************************************
                        col_number++;
                        // Обновляем инфу о текущем уровне *******************************************
                        levelText.setText("Уровень: "+Integer.toString(col_number-2));
                        list_buttons.get(i).end_btn=false;
                        btn_start.setEnabled(true);
                        for (int i1=0; i1<list_buttons.size();i1++) {
                            list_buttons.get(i1).setEnabled(false);
                          //  list_buttons.get(i1).end_btn=false;
                        }
                        //toast = Toast.makeText(Game_screen.this, "УРА!!! Победа!", Toast.LENGTH_SHORT);
                        //toast.setGravity(Gravity.CENTER,0,0);
                        //toast.show();
                        list_buttons.get(i).setBackgroundColor(Color.GRAY);
                        sp.play(melodyPobeda,1,1,1,0,1);

                    }
                }

            }
            if ((view.getId() == list_buttons.get(i).getId()) && (motionEvent.getAction() == ACTION_UP)) {
                list_buttons.get(i).setBackgroundColor(Color.GRAY);


            }
        }
        if (view.getId()==btn_start.getId()) {
            // Запускаем игру *********************************************************************
            sp.play(melodyClick,1,1,1,0,1);
            on_light_button();
        }

        return false;
    }
}
