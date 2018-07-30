package com.example.akiya.deliverymyfeelings.Main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.example.akiya.deliverymyfeelings.Library.DrawBitmapRelationship;
import com.example.akiya.deliverymyfeelings.Library.ScreenTouch;
import com.example.akiya.deliverymyfeelings.Library.Sound;
import java.util.Random;


    public class OriginalView extends View {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public OriginalView(Context context) {
        super(context);                                                             //Viewのコンストラクタを呼び出す。
        Class_ScreenTouch = new ScreenTouch(this);                            //画面タッチを管理
        setOnTouchListener(Class_ScreenTouch);                                     //Listerner
        Class_Bmp=new DrawBitmapRelationship(this);                            //Bitmap描画管理
        Class_sound=new Sound(this);                                           //音楽関係
        Class_MainGame=new MainGame(this);                                     //ゲーム処理Class
    }

    //メインループ処理
    public void start() {
        Thread trd = new Thread(new Runnable() {
            public void run() {
                while (loop) {
                    long prev = System.currentTimeMillis();                         //現在時刻を取得
                    mHandler.post(new Runnable() {
                        public void run() {loop = MainLoop();}                     //メインループ処理
                    });
                    mHandler.post(new Runnable() {
                        public void run() {postInvalidate();}});                    //再描画の発行
                    //FPS処理
                    long ptTime = 32 - (System.currentTimeMillis() - prev);         //ptTimeはスレッドが止まってほしい時間
                    try {
                        if (ptTime <= 0) {
                            Thread.sleep(1);                                 //最低１は止まらないといけない
                        } else {
                            Thread.sleep(ptTime);
                            Log.d("sleep", "ptTime:" + ptTime);
                        }
                    } catch (Exception e) {
                    }
                }
            }//run
        });
        trd.start();
    }

    //ランダム値を取得する
    public int GetRand(int _max){return r.nextInt(_max);}                       //0から_max-1の範囲で

    //解像度対応
    public void SetDisplyaSize(float _dsx,float _dsy) {
        par_ds_x = SYSTEM_X / _dsx;                                             //内部座標から画面座標
        par_ds_y = SYSTEM_Y / _dsy;                                             //内部座標から画面座標
        par_sd_x=_dsx/SYSTEM_X;                                                 //画面座標から内部座標
    }

    //Contextを渡す
    public Context GetContext() {return getContext();}

    //canvasを渡す
    public Canvas GetCanvas(){return  canvas;}

    //paintを渡す
    public Paint GetPaint(){return paint;}

    //par_sd_xを渡す
    public float Getpar_sd_x(){return par_sd_x;}

    //par_sd_xを渡す
    public float Getpar_ds_x(){return par_ds_x;}

    //par_sd_xを渡す
    public float Getpar_ds_y(){return par_ds_y;}

    //画面のタッチ状態を渡す
    public int OnDown(){return Class_ScreenTouch.onDown();}

    //ScreenTouchのフラグをゼロにする(タッチしていない状態にする)
    public void ResetOnDown(){Class_ScreenTouch.ResetonDown();}

    //タッチした箇所のx座標を渡す
    public float GetTX(){return Class_ScreenTouch.GetTX();}

    //タッチした箇所のy座標を渡す
    public float GetTY(){return Class_ScreenTouch.GetTY();}

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    public static final float SYSTEM_X=600;                                    //画面X座標のMAXサイズ
    public static final float SYSTEM_Y=840;                                    //画面Y座標のMAXサイズ


    //==============================================================================================
    // private
    // =============================================================================================

    // ---------------------------------------------------------------------------------------------
    //start内の関数
    //----------------------------------------------------------------------------------------------
    //
    private boolean MainLoop() {
        frameCnt++;                                          //カウントアップ
        Class_MainGame.Update();                            //ゲーム処理
        return true;
    }

    @Override   //ViewのonDrawを上書き
    protected void onDraw(Canvas c) {                       //出力先
        super.onDraw(c);
        canvas = c;                                         //出力先を覚える
        Class_MainGame.Draw();                             //ゲーム描画処理

    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Canvas canvas = null;                          //描画の出力先
    private Paint paint = new Paint();                     //色の保存
    private int frameCnt = 0;                              //FPS用のカウンター
    private boolean loop = true;                           //ループフラグ
    private Handler mHandler = new Handler();               //ハンドラー管理
    private MainGame Class_MainGame=null;                  //ゲーム内容を管理
    public DrawBitmapRelationship Class_Bmp=null;          //描画関係
    public Sound Class_sound =null;                        //音声関係
    private ScreenTouch Class_ScreenTouch;                 //画面タッチ
    public int SeId;                                        //SEのidを管理
    private  float par_ds_x;                               //画面座標
    private  float par_ds_y;                               //画面座標
    private  float par_sd_x;                               //内部座標
    private Random r=new Random();                          //ランダム値を扱う


}
