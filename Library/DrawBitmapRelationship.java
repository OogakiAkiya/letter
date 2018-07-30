package com.example.akiya.deliverymyfeelings.Library;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.example.akiya.deliverymyfeelings.Game.holePos;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;


public class DrawBitmapRelationship {
    //コンストラクタ
    public  DrawBitmapRelationship(OriginalView _ov){
        owner=_ov;
        Class_BitmapList=new BitmapList(_ov);
    }

    //----------------------------------------------------------------------------------------------
    //文字描画
    //----------------------------------------------------------------------------------------------
    //文字描画
    public void DrawString(float _x, float _y, String _str, int _color) {
        if (owner.GetCanvas() == null) return;                                                     //Canvasが見つからなければ処理から抜ける
        owner.GetPaint().setColor(_color);                                                          //色の設定
        owner.GetPaint().setTextSize(50);                                                           //サイズの設定
        owner.GetCanvas().drawText(_str, _x, _y, owner.GetPaint());                                //文字描画
    }

    //----------------------------------------------------------------------------------------------
    //Bitmap描画
    //----------------------------------------------------------------------------------------------
    //Bitmap描画
    public void DrawBitmap (Bitmap _bmp,float _x,float _y){
        if(owner.GetCanvas()==null)return;                                                         //Canvasが見つからなければ処理から抜ける
        Matrix m=new Matrix();                                                                      //行列の作成
        m.postScale(1.0f*owner.Getpar_sd_x(),1.0f*owner.Getpar_sd_x());                      //拡大
        m.postTranslate(_x*owner.Getpar_sd_x(),_y*owner.Getpar_sd_x());                     //解像度対応させて一の設定
        owner.GetCanvas().drawBitmap(_bmp,m,owner.GetPaint());                                     //描画
    }

    //Bitmap描画(透過処理)
    public void DrawBitmap (Bitmap _bmp,float _x,float _y,int _Alpha){
        if(owner.GetCanvas()==null)return;                                                         //Canvasが見つからなければ処理から抜ける
        Paint p = new Paint();
        p.setAlpha(_Alpha);                                                                         //透明度の設定
        Matrix m=new Matrix();                                                                      //行列の作成
        m.postScale(1.0f*owner.Getpar_sd_x(),1.0f*owner.Getpar_sd_x());                      //拡大
        m.postTranslate(_x*owner.Getpar_sd_x(),_y*owner.Getpar_sd_x());                     //解像度対応させて位置の設定
        owner.GetCanvas().drawBitmap(_bmp,m,p);                                                     //描画

    }

    //Bitmap描画(拡大して描画)
    public void DrawBitmap(Bitmap _bmp,float _x,float _y,float _ex,float _ey){
        if(owner.GetCanvas()==null)return;                                                         //Canvasが見つからなければ処理から抜ける
        _x=_x*owner.Getpar_sd_x();                                                                  //解像度対応
        _y=_y*owner.Getpar_sd_x();                                                                  //解像度対応
        Matrix m=new Matrix();                                                                      //行列の作成
        m.postScale((float)_ex*owner.Getpar_sd_x(),(float)_ey*owner.Getpar_sd_x());         //拡大
        m.postTranslate(_x,_y);                                                                     //位置の設定
        owner.GetCanvas().drawBitmap(_bmp,m,owner.GetPaint());                                     //描画
    }

    //Bitmap描画(回転して拡大描画)
    public void DrawBitmap(Bitmap _bmp,float _x,float _y,float _rot,double _ex,double _ey){
        if(owner.GetCanvas()==null)return;                                                         //Canvasが見つからなければ処理から抜ける
        Matrix m=new Matrix();                                                                      //行列の作成
        if(_rot !=0){
            float bw = _bmp.getWidth()/2;                                                           //画面の幅/2
            float bh = _bmp.getHeight()/2;                                                          //画面の高さ/2
            m.setTranslate(-bw,-bh);                                                                //画像の設定画像を-bw,-bh移動
            m.postRotate(_rot);                                                                     //回転を合成　c++でいうworld=rot*mat
            m.postTranslate(bw,bh);                                                                 //画像を元の位置に戻す
        }
        m.postScale((float)_ex*owner.Getpar_sd_x(),(float)_ey*owner.Getpar_sd_x());         //拡大
        m.postTranslate(_x*owner.Getpar_sd_x(),_y*owner.Getpar_sd_x());                     //解像度対応させて一の設定
        owner.GetCanvas().drawBitmap(_bmp,m,owner.GetPaint());                                     //描画
    }

    //----------------------------------------------------------------------------------------------
    //Pixel_Alpha　部分的な透過処理
    //----------------------------------------------------------------------------------------------
    //画像全体の透過しそのビットマップを返す
    public Bitmap Pixel_Alpha(Bitmap _bmp,int _Alpha){
        if (!_bmp.isMutable()) _bmp = _bmp.copy(Bitmap.Config.ARGB_8888, true);          //画像を編集できる状態にする
        int width = _bmp.getWidth();                                                                //画像横幅
        int height = _bmp.getHeight();                                                              //画像の縦

        int[] pixels = new int[width * height];                                                     //ピクセル情報を保持する配列
        _bmp.getPixels(pixels, 0, width, 0, 0, width, height);                           //Bitmapのピクセルデータを取得

        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                int Alpha = pixels[x + y * width];                                                  // 透過率をintとして取得
                Alpha = Alpha >>> 24;                                                               //アルファ値をいじれるようにする
                if (Alpha != 0) {                                                                   //色情報の削除
                    Alpha -=(255-_Alpha);                                                           //加える透過率の値、0(はっきり)～255(透明)
                    if (Alpha < 0) Alpha = 0;                                                       //0以下の場合0にする
                }
                Alpha = Alpha << 24;                                                                //色情報を戻す

                int Color = pixels[x + y * width];                                                  //ピクセル情報をintとして取得
                Color = Color << 8;                                                                 //Bitmapのピクセルの色情報から透過率の削除
                Color = Color >>> 8;                                                                //移動した分戻る
                pixels[x + y * width] = Alpha ^ Color;                                              //透過情報と色情報の合成
            }
        }
        _bmp.setPixels(pixels, 0, width, 0, 0, width, height);                           //透過度が変わったBitmapデータの作成
        return _bmp;
    }

    //指定した範囲のみを透過しビットマップを返す
    public Bitmap Pixel_Alpha(Bitmap _Bmp, int _StartX, int _StartY, int _Width, int _Height, int _Alpha)
    {
        if (!_Bmp.isMutable()) _Bmp = _Bmp.copy(Bitmap.Config.ARGB_8888, true);          //画像を編集できる状態にする
        int Width=_Width-_StartX;                                                                   //切り取り横範囲の作成
        int Height=_Height-_StartY;                                                                 //切り取り縦範囲の作成

        int[] pixels=new int[Width*Height];                                                        //ピクセル情報を保持する配列
        _Bmp.getPixels(pixels, 0, Width,_StartX,_StartY, Width, Height);                     //Bitmapのピクセルデータを取得

        for (int y=0; y<Height; y++) {
            for (int x=0; x<Width; x++) {
                int Alpha = pixels[x + y * Width];                                                  // 透過率をintとして取得
                Alpha = Alpha >>> 24;                                                               //アルファ値をいじれるようにする
                if (Alpha != 0) {                                                                   //色情報の削除
                    Alpha -=(255-_Alpha);                                                           //加える透過率の値、0(はっきり)～255(透明)
                    if (Alpha < 0) Alpha = 0;                                                       //0以下の場合0にする
                }
                Alpha = Alpha << 24;                                                                //色情報を戻す

                int Color = pixels[x + y * Width];                                                  //ピクセル情報をintとして取得
                Color = Color << 8;                                                                 //Bitmapのピクセルの色情報から透過率の削除
                Color = Color >>> 8;                                                                //移動した分戻る

                pixels[x + y * Width] = Alpha ^ Color;                                              //透過情報と色情報の合成
            }
        }
        _Bmp.setPixels(pixels, 0, Width,_StartX,_StartY, Width, Height);                      //透過度が変わったBitmapデータの作成
        return _Bmp;
    }

    //_holeで渡された範囲を透過
    public Bitmap Pixel_Alpha(Bitmap _Bmp, holePos _hole, int _Alpha)
    {
        //ピクセルをいじれる形に変換
        if (!_Bmp.isMutable()) _Bmp = _Bmp.copy(Bitmap.Config.ARGB_8888, true);          //画像を編集できる状態にする
        int Width=(int)(_hole.Scalex-_hole.x);                                                     //切り取り横範囲の作成
        int Height=(int)(_hole.Scaley-_hole.y);                                                    //切り取り縦範囲の作成

        int[] pixels=new int[Width*Height];                                                        //ピクセル情報を保持する配列
        _Bmp.getPixels(pixels, 0, Width,(int)_hole.x,(int)_hole.y, Width, Height);           //Bitmapのピクセルデータを取得

        for (int y=0; y<Height; y++) {
            for (int x=0; x<Width; x++) {
                int Alpha = pixels[x + y * Width];                                                  // 透過率をintとして取得
                Alpha = Alpha >>> 24;                                                               //アルファ値をいじれるようにする
                if (Alpha != 0) {                                                                   //色情報の削除
                    Alpha -=(255-_Alpha);                                                           //加える透過率の値、0(はっきり)～255(透明)
                    if (Alpha < 0) Alpha = 0;                                                       //0以下の場合0にする
                }
                Alpha = Alpha << 24;                                                                //色情報を戻す

                int Color = pixels[x + y * Width];                                                  //ピクセル情報をintとして取得
                Color = Color << 8;                                                                 // Bitmapのピクセルの色情報から透過率の削除
                Color = Color >>> 8;                                                                // 移動した分戻る

                pixels[x + y * Width] = Alpha ^ Color;                                              // 透過情報と色情報の合成
            }
        }
        _Bmp.setPixels(pixels, 0, Width,(int)_hole.x,(int)_hole.y, Width, Height);           //透過度が変わったBitmapデータの作成
        return _Bmp;

    }

    //_passのBitmpが既にロードされていた場合その画像が返ってくる(なければロードして返す)
    public Bitmap SerchBmp(String _pass){return Class_BitmapList.SearchBitmap(_pass);}

    //BitmapListでロードしている内容を全て削除する
    public void ClearBmp(){Class_BitmapList.Clear();}

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private OriginalView owner =null;                                                              //OriginalViewを保持するクラス
    private BitmapList Class_BitmapList=null;                                                     //Bitmapを一括で管理している

}
