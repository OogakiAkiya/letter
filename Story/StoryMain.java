package com.example.akiya.deliverymyfeelings.Story;

import android.graphics.Bitmap;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;


public class StoryMain {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public StoryMain(OriginalView _ov){
        owner = _ov;
        Init();                                                                                     //初期化
    }

    public int Update(){
        FadeIn();                                                                                   //フェードイン処理
        ChangeScean();                                                                              //シーンチェンジ
        OnDown();                                                                                   //画面がタッチされたら処理が行われる
        return FadeOut();                                                                           //フェードアウトやシーンチェンジが行われる
    }

    public void Draw(){
        owner.Class_Bmp.DrawBitmap(bmp, 0, 0,Alpha);                                        //背景描画
        SceanDraw();                                                                                //シーンの描画
       if(SceanNumber==SCEANMAX)owner.Class_Bmp.DrawBitmap(StringBmp,20,30,Alpha);       //最後のシーン
    }

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    private static final int SCEANMAX=2;                                                          //シーン数


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //初期化
    private void Init(){
        bmp=owner.Class_Bmp.SerchBmp("images/ストーリー背景.png");                         //背景
        StringBmp=owner.Class_Bmp.SerchBmp("images/String3.png");                         //文章のBitmap読み込み(注意事項)
        Scean[0]=new StoryBase(owner,"images/String1.png");                                //文章のBitmap読み込み(ストーリー)
        Scean[1]=new StoryBase(owner,"images/String2.png");                                //文章のBitmap読み込み(操作説明)
    }
    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //フェード処理
    private  void FadeIn(){
        if(disappearflg==false)Alpha+=4;                                                          //フェードイン
        if(Alpha>255&&disappearflg==false){                                                       //値が255を超えたら以下を通る
            disappearflg=true;                                                                    //フラグをtrueにする
            Alpha=255;                                                                             //Alpha値を255で固定にする
        }
    }

    //シーンチェンジ
    private void ChangeScean(){
        if(disappearflg) {                                                                         //フェードインの処理が終わっていたら
            if (SceanNumber < SCEANMAX) {                                                         //現在のシーンが最後のシーンでなければ
                if (Scean[SceanNumber].Update() == 1) SceanNumber++;                             //次のシーンに変更する
            }
        }
    }

    //終了処理
    private int FadeOut(){
        if(SceanNumber>=SCEANMAX&&Alpha!=255) Alpha -= 5;                                        //フェードアウト
        if(disappearflg==true&&Alpha<0)return 1;                                                 //
        return 0;
    }

    //画面がタッチされた時フェードアウトする
    private void OnDown(){
        if(owner.OnDown()==1){                                                                      //画面がタッチされた時
            if(SceanNumber>=SCEANMAX) Alpha-=5;                                                   //最後のシーンの時フェードアウトする
            if(SceanNumber<SCEANMAX)Scean[SceanNumber].StartSceanChange();                       //最後のシーン以外の時シーンのシーンチェンジ処理を呼び出す
            owner.ResetOnDown();                                                                    //タッチ状態の初期化
            owner.Class_sound.PlaySE(owner.SeId);                                                 //SEを流す
        }
    }

    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //ストーリー描画
    public void SceanDraw(){
        if(disappearflg) {                                                                         //フラグがtrueの時
            if (SceanNumber < SCEANMAX){                                                          //シーン数がシーンの
                Scean[SceanNumber].Draw();                                                         //現在のシーンが最後のシーンでなければ
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Bitmap bmp;                                                                            //背景用
    private Bitmap StringBmp;                                                                     //注意事項のBitmap読み込み
    private int SceanNumber=0;                                                                    //現在のシーン数
    private int Alpha=0;                                                                           //全体のBitmapの透明度
    private boolean disappearflg=false;                                                           //これがtrueになればシーンチェンジが始まる
    private StoryBase[] Scean=new StoryBase[SCEANMAX];                                            //ストーリーのシーンを管理するクラス
    private OriginalView owner = null;                                                             //OriginalViewを格納

}
