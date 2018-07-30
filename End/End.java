package com.example.akiya.deliverymyfeelings.End;

import android.graphics.Bitmap;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;
import com.example.akiya.deliverymyfeelings.Library.Number;

public class End {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public End(OriginalView _ov) {
        owner = _ov;
        Class_Number=new Number(owner);
        LoadBitmap();                                       //Bitmapのロード処理
    }

    public int Update() {
        Counter();                                          //文字用のカウントアップ処理
        return End();                                      //画面がタッチされた時１が返ってきてタイトルに進む
    }

    public void Draw(int _score) {
        Draw_Bitmap();                                      //Bitmapの描画
        Draw_Number(_score);                                //score表示
    }


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //Bitmapロード処理
    public void LoadBitmap(){
        ScoreGB=owner.Class_Bmp.SerchBmp("images/ScoreBG.png");                   //背景ロード処理
        Result=owner.Class_Bmp.SerchBmp("images/EndResult.png");                  //文字のロード処理
        Class_Number.LoadBitmap("images/",".png");                       //０～９の数字を読み込む
    }
    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //文字のフェードイン用カウンター
    private void Counter(){
        if(count<254)count+=2;                                                              //カウントアップ処理
    }

    //画面がタッチされたら1を返しタイトルに進む
    private int End(){
        if(owner.OnDown()==2)return 1;                                                      //タップし画面から手が離れた時1を返す
        return 0;
    }
    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //Bitmap描画
    private void Draw_Bitmap(){
        owner.Class_Bmp.DrawBitmap(ScoreGB,0,0);                                //背景描画
        if(count>0)owner.Class_Bmp.DrawBitmap(Result,0,0,count);               //コメント描画
    }

    private void Draw_Number(int _score){
        if(count>0)Class_Number.DrawNumber(_score,480,500,0,count);    //_scoreを描画する
    }
    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Bitmap ScoreGB;                         //背景Bitmap
    private Bitmap Result;                          //コメントBitmap
    private int count=-100;                         //文字とスコアのフェードイン用の変数
    private OriginalView owner =null;               //OriginalViewを格納
    private Number Class_Number=null;               //Class_Numberを格納

}
