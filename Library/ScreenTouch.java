package com.example.akiya.deliverymyfeelings.Library;


import android.view.MotionEvent;
import android.view.View;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;

public class ScreenTouch implements View.OnTouchListener{

    public ScreenTouch(OriginalView _ov){
    owner=_ov;
}

    public boolean onTouch(View v, MotionEvent event){
        buttonflg=0;
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:                           //画面にタッチした瞬間
                buttonflg = 1;                                      //タッチされたことを判定
                tx = event.getX()*owner.Getpar_ds_x();              //画面タッチX座標取得
                ty = event.getY()*owner.Getpar_ds_y();              //画面タッチY座標取得
                break;
            case MotionEvent.ACTION_MOVE:                          //押されている間
                break;
            case MotionEvent.ACTION_UP:                            //離した瞬間
                buttonflg = 2;                                     //タッチされたことを判定
                tx = event.getX()*owner.Getpar_ds_x();             //画面タッチX座標取得
                ty = event.getY()*owner.Getpar_ds_y();             //画面タッチY座標取得
                break;
        }
        return true;
    }

    //ボタンの状態を表す
    public int onDown(){return buttonflg;}

    //ボタンの状態をリセットする
    public void ResetonDown(){buttonflg=0;}

    //タッチしたx座標を返す
    public float GetTX(){return tx;}

    //タッチしたy座標を返す
    public float GetTY(){return ty;}


    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private float tx=0;                                             //タッチしたx座標
    private float ty=0;                                             //タッチしたy座標
    private int buttonflg;                                          //タッチの状態を表す
    private OriginalView owner;                                     ////OriginalViewを格納

}
