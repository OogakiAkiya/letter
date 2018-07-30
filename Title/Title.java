package com.example.akiya.deliverymyfeelings.Title;
import com.example.akiya.deliverymyfeelings.Library.DrawBitmapRelationship;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;


public class Title {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public Title(OriginalView _ov) {
        owner = _ov;
        Init();             //初期化
    }

    public int Update() {
        Leaf_Update();              //Leaf処理
        Touch();                     //画面が押されたか判定
        FadeOut();                  //画面が押された時からフェードアウトする処理
        String_Update();            //文字のアニメーション処理
        return End();              //1が返ってきたら次のシーンに
    }

    public void Draw() {
        Leaf_Draw();                //Leaf描画
        Title_Draw();               //タイトルフレームと文字描画
    }

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    private static final int LEAFMAX=35;        //葉っぱの数


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //初期化
    public void Init(){
        Leaf_Create();                                                                //Leaf作成
        Frame=owner.Class_Bmp.SerchBmp("images/Title.png");                 //背景ロード処理
        String=owner.Class_Bmp.SerchBmp("images/PleaseTouch.png");         //文字ロード処理
        owner.ResetOnDown();                                                        //タッチ状態のリセット
    }

    //Leafをリストに追加
    private void Leaf_Create() {
        for (int i = 0; i < LEAFMAX; i++) {
            Leaf obj = new Leaf(owner);                                             //メモリ確保
            Class_LeafList.add(obj);                                               //リストに追加
        }
    }

    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //Leaf処理
    private void Leaf_Update() {
        for(int element=0;element<Class_LeafList.size();element++) {              //リスト分だけ回す
            Class_LeafList.get(element).Update();
            if(Class_LeafList.get(element).Reset()==1){
                Leaf obj = new Leaf(owner);               //メモリ確保
                Class_LeafList.set(element,obj);
            }
        }
    }

    //画面をタップし手を離した瞬間に通る処理
    private void Touch(){
        if( owner.OnDown()==2){                         //画面から手が離れたら判定
            Alpha-=3;                                   //文字以外の透過処理
            StringAlphaflg=3;                           //文字がフェードアウトするフェードアウト処理
            owner.ResetOnDown();                        //タッチしている状態を表す変数をリセット
            owner.Class_sound.PlaySE(owner.SeId);     //画面タッチ用のSEを鳴らす
        }
    }

    //各Bitmapの透過率変化
    private void FadeOut(){
        if(Alpha<255)Alpha-=3;                          //一度画面が押された場合全体のAlpha値を下げる
    }


    //文字の透過率の変更(StringAlphaflgが1&2で文字のフェードインとアウトする3で他のBitmapと同様にフェードアウトする)
    private void String_Update(){
        if(StringAlphaflg==0){                                  //フェードイン
            StringAlpha+=3;
            if(StringAlpha>250)StringAlphaflg=1;               //限界まで透過率を下げたらフェードインへ
        }
        if(StringAlphaflg==1){                                  //フェードアウト
            StringAlpha-=3;
            if(StringAlpha<5)StringAlphaflg=0;                 //限界まで透過率を上げたらフェードアウトへ
        }
        if(StringAlphaflg==3)StringAlpha=Alpha;                //画面タッチ後周りのBitmapと同様に透過処理をする
    }

    //タイトルを抜けるか判断する
    private int End() {
        if(Alpha<0)return 1;                                      //フェードアウトが終わると1を返す
        return 0;
    }
    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //Leafの描画
    private void Leaf_Draw() {
        for(int element=0;element<Class_LeafList.size();element++) {
            Class_LeafList.get(element).Draw(Alpha);
        }
    }

    //タイトルフレームと文字の描画
    private void Title_Draw(){
        owner.Class_Bmp.DrawBitmap(Frame,0,0,Alpha);                    //タイトルフレームの描画
        owner.Class_Bmp.DrawBitmap(String,0,0,StringAlpha);            //文字描画
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Bitmap Frame;                                                  //タイトルフレームBitmap
    private Bitmap String;                                                 //文字Bitmap
    private int Alpha=255;                                                 //全体の透過処理用変数
    private int StringAlpha=0;                                            //文字の透過処理用変数
    private int StringAlphaflg=0;                                         //文字処理用のフラグ
    private OriginalView owner =null;                                      //OriginalViewを格納
    private ArrayList<Leaf> Class_LeafList=new ArrayList<Leaf>();            //Leaf用の可変長配列

}
