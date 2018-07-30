package com.example.akiya.deliverymyfeelings.Game;

import android.graphics.Bitmap;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;


public class hole {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public hole(OriginalView _ov) {owner= _ov;}

    //引数で渡された値から穴を作成する
    public void Create(holePos _a) {
        x=_a.x;                                                                                                              //始点のx座標
        y=_a.y;                                                                                                              //始点のy座標
        Scalex=_a.Scalex;                                                                                                   //拡大後のx座標
        Scaley=_a.Scaley;                                                                                                   //拡大後のy座標
    }

    //透過処理を行う
    public Bitmap Update(Bitmap _bmp) {
        if(Alpha>0) {
            Bitmap tempBitmap;                                                                                              //一時格納用Bitmap
            tempBitmap = owner.Class_Bmp.Pixel_Alpha(_bmp, (int) x, (int) y, (int) Scalex, (int) Scaley, Alpha);        //穴の範囲でBitmapの色を透過させる
            Alpha -= 2;                                                                                                    //フェードアウト処理
            return  tempBitmap;                                                                                            //加工したBitmapを返す
        }
        return _bmp;
    }

    //穴の座標が必要になった時に使用する
    public holePos ReturnPos(){
        holePos obj=new holePos(owner);                                                             //変数をpublicに扱うためのクラスを作成する
        obj.x=x;                                                                                    //始点のx座標
        obj.Scalex=Scalex;                                                                         //始点のy座標
        obj.y=y;                                                                                    //拡大後のx座標
        obj.Scaley=Scaley;                                                                         //拡大後のy座標
        return obj;
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private float x;                                                                               //始点のx座標
    private float y;                                                                               //始点のx座標
    private float Scalex;                                                                          //拡大後のy座標
    private float Scaley;                                                                          //拡大後のy座標
    private int Alpha=255;                                                                         //透明度
    private OriginalView owner =null;                                                              //OriginalViewを格納

}
