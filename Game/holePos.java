package com.example.akiya.deliverymyfeelings.Game;

import android.graphics.Bitmap;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;
import static com.example.akiya.deliverymyfeelings.Game.Player.DOWN;
import static com.example.akiya.deliverymyfeelings.Game.Player.LEFT;
import static com.example.akiya.deliverymyfeelings.Game.Player.RIGHT;
import static com.example.akiya.deliverymyfeelings.Game.Player.UP;


public class holePos {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public holePos(OriginalView _ov){
        owner=_ov;
    }

    //渡した引数で初期値設定
    public void Init(float _x,float _y) {
        x=_x;                                                                                       //始点のx座標
        y=_y;                                                                                       //始点のy座標
        Scalex=_x;                                                                                  //拡大後のx座標
        Scaley=_y;                                                                                  //拡大後のy座標
    }

    public void Update(Bitmap _bmp,float _x, float _y,int _Vector) {
        float HalfBmp_Width= _bmp.getWidth()/2;                                                     //画像の横半分のサイズ
        float HalfBmp_Height=_bmp.getHeight()/2;                                                    //画像の縦半分のサイズ
        if(_Vector==UP||_Vector==DOWN) Judge_UpAndDown(HalfBmp_Width,HalfBmp_Height,_x,_y);         //画像縦向きの座標更新
        if(_Vector==LEFT||_Vector==RIGHT)Judge_LeftAndRight(HalfBmp_Width,HalfBmp_Height,_x,_y);   //画像横向きの座標更新
    }

    //値のリセット
    public void Reset() {
        x=0;
        y=0;
        Scalex=0;
        Scaley=0;
    }


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //画像縦向きでの場合の座標更新
    private void Judge_UpAndDown(float _HalfBmp_Height,float _HalfBmp_Width,float _x,float _y){
            if (x > _x-_HalfBmp_Width) x = _x-_HalfBmp_Width;                                       //始点のx座標
            if (Scalex < _x-_HalfBmp_Width) Scalex = _x-_HalfBmp_Width;                            //拡大後のx座標
            if (y > _y-_HalfBmp_Height) y = _y-_HalfBmp_Height;                                     //始点のy座標
            if (Scaley < _y-_HalfBmp_Height) Scaley = _y-_HalfBmp_Height;                          //拡大後のy座標
    }

    private void Judge_LeftAndRight(float _HalfBmp_Height,float _HalfBmp_Width,float _x,float _y){
            if (x > _x-_HalfBmp_Height) x = _x-_HalfBmp_Height;                                     //始点のx座標
            if (Scalex < _x-_HalfBmp_Height) Scalex = _x-_HalfBmp_Height;                          //拡大後のx座標
            if (y > _y-_HalfBmp_Width) y = _y-_HalfBmp_Width;                                       //始点のy座標
            if (Scaley < _y-_HalfBmp_Width) Scaley = _y-_HalfBmp_Width;                            //拡大後のy座標
    }
    
    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    public float x;                                                                                //始点のx座標
    public float y;                                                                                //始点のy座標
    public float Scalex;                                                                           //拡大後のx座標
    public float Scaley;                                                                           //拡大後のy座標
    OriginalView owner;                                                                             //OriginalViewを格納

}
