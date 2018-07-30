package com.example.akiya.deliverymyfeelings.Library;

import android.graphics.Bitmap;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;


public class Number {
    //クラス
    public Number(OriginalView _ov){
        owner=_ov;
        NumberList=new Bitmap[10];                                                                 //Bitmap配列
    }

    //一括でBitmapロードする
    public void LoadBitmap(String _pass,String _extension){
        for(int i=0;i<10;i++)
        {
            NumberList[i]=owner.Class_Bmp.SerchBmp(_pass+i+_extension);                     //画像がリスト内にあるか探す
        }
    }

    //_numを描画する
    public void DrawNumber(int _num,float _x,float _y) {
        int num1,num2;
        int val=String.valueOf(_num).length();                                                      //桁数がわかる
        num2=_num;
        for(int i=0;i<val;i++){                                                                     //桁数分だけ回す
            num1=num2%10;                                                                           //一桁目の値をとってくる
            num2=num2/10;                                                                           //一桁目の値を削る
            owner.Class_Bmp.DrawBitmap(NumberList[num1],_x-(60*i),_y,(float)1.0,(float)1.0);  //描画
        }
    }

    //_numを描画する(透過処理付き)
    public void DrawNumber(int _num,float _x,float _y,int _Alpha) {
        int num1,num2;
        int val=String.valueOf(_num).length();                                                      //桁数がわかる
        num2=_num;
        for(int i=0;i<val;i++){                                                                    //桁数分だけ回す
            num1=num2%10;                                                                           //一桁目の値をとってくる
            num2=num2/10;                                                                           //一桁目の値を削る
            owner.Class_Bmp.DrawBitmap(NumberList[num1],_x-(70*i),_y,_Alpha);                  //描画(透明)
        }
    }

    //_numを描画する(透過処理&余白を調節可能)
    public void DrawNumber(int _num,float _x,float _y,float _margin,int _alpha) {
        int num1,num2;
        int val=String.valueOf(_num).length();                                                                         //桁数がわかる
        num2=_num;
        for(int i=0;i<val;i++){                                                                                        //桁数分だけ回す
            num1=num2%10;                                                                                              //一桁目の値をとってくる
            num2=num2/10;                                                                                              //一桁目の値を削る
            owner.Class_Bmp.DrawBitmap(NumberList[num1],_x-((NumberList[0].getWidth()+_margin)*i),_y,_alpha);    //描画(_marginで間隔を調節している)
        }
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private OriginalView owner;                 //OriginalViewの管理
    private Bitmap[] NumberList;                //Bitmapを扱うクラス
}
