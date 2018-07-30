package com.example.akiya.deliverymyfeelings.Story;

import android.graphics.Bitmap;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;

/**
 * Created by akiya on 2018/06/26.
 */

public class StoryBase {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ

    public StoryBase(OriginalView _ov,String _pass){
        owner = _ov;
        Init(_pass);                                                                                //初期化
    }

    public int Update(){
        Scroll();                                                                                   //スクロール処理
        if(SceanChangeflg==true)count-=5;                                                         //シーンチェンジフラグがtrueになったらフェードアウト開始
        if(SceanChangeflg==true&&count<0)return 1;                                               //フェードアウトが完了したら戻り値1
        return 0;
    }

    public void Draw(){
        owner.Class_Bmp.DrawBitmap(StringBmp,0,0,count);                                   //文章の描画
        owner.Class_Bmp.DrawBitmap(bmp,0,0);                                                //スクロール用の文章の上にくる画像
    }

    //シーンチェンジを開始する
    public void StartSceanChange(){
        SceanChangeflg=true;
    }


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //初期化
    private void Init(String _pass){
        bmp=owner.Class_Bmp.SerchBmp("images/ストーリー背景.png");                         //背景
        StringBmp=owner.Class_Bmp.SerchBmp(_pass);                                                //外部に指定された_passを読み込む
    }

    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //スクロール処理
    private void Scroll(){
        if(y<owner.SYSTEM_Y) {                                                                                  //yが画面の一番下より上にある
            bmp = owner.Class_Bmp.Pixel_Alpha(bmp, 0, 0, (int) owner.SYSTEM_X, y, 0);   //現在のy座標の位置まで透明化処理を行い透明化したBitmapを返す
            if(count<=255)y +=2;                                                                                 //y座標を増やす
            if(y>owner.SYSTEM_Y) y=(int)owner.SYSTEM_Y;                                                       //yが画面以上にならないようにする
        }
    }


    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Bitmap bmp;                                                                            //スクロール用のStringBmpの上に張る画像
    private Bitmap StringBmp;                                                                      //シーンごとに描画する文字
    private int y=0;                                                                               //透明化を行うy座標の管理
    private boolean SceanChangeflg = false;                                                      //シーンチェンジ開始用
    private int count = 255;                                                                      //二枚目に移る時のカウント処理用
    private OriginalView owner = null;

}
