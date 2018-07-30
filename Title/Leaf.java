package com.example.akiya.deliverymyfeelings.Title;

import android.graphics.Bitmap;

import com.example.akiya.deliverymyfeelings.Library.DrawBitmapRelationship;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;

import static android.view.View.ALPHA;
import static com.example.akiya.deliverymyfeelings.Main.OriginalView.SYSTEM_X;
import static com.example.akiya.deliverymyfeelings.Main.OriginalView.SYSTEM_Y;

public class Leaf {

    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public Leaf(OriginalView _ov) {
        owner = _ov;
        Load_Bitmap();                                                //Bitmapのロード処理
        Init();                                                       //座標など変数の初期化
    }

    public void Update() {
        Move();                                                       //移動処理
        CountJudge();                                                 //countの管理
    }

    public void Draw(int _Alpha) {Draw_Bitmap(_Alpha);}

    //Leafを削除するかどうか判定する
    public int Reset() {
        if (x < 0||y + bmp[0].getHeight() > SYSTEM_Y) return 1;     //画面の左側か下に重なったら1を返す
        return 0;
    }

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    private static final int XMARGIN=400;           //初期座標の余白X
    private static final int YMARGIN=300;           //初期座標の余白Y
    private static final int LEFT=0;                //方向を表す
    private static final int RIGHT=1;               //方向を表す


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //Bitmapのロード処理
    private void Load_Bitmap() {
        if (bmp[0] == null || bmp[1] == null) {
            bmp[0] = owner.Class_Bmp.SerchBmp("images/桜.png");
            bmp[1] = owner.Class_Bmp.SerchBmp("images/桜2.png");
        }
    }

    //変数初期化
    private void Init() {
        x = (float) (SYSTEM_X + owner.GetRand(XMARGIN));        //x座標の初期化
        y = (float) owner.GetRand((int) SYSTEM_Y) - YMARGIN;    //ｙ座標の初期化
        vector = owner.GetRand(2);                         //ランダム値を代入
        count = owner.GetRand(100);                        //ランダム値を代入
    }

    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //Leafのフラグ処理
    private void CountJudge() {
        if (count > 60&&vector == LEFT) {                          //左向きの時に処理する
            vector = RIGHT;                                        //進む方向を右に変更
            return;
        }

        if (count > 100) {                                         //右向きの時に処理する
            vector = LEFT;                                         //進む方向を左に変更
            count = 0;                                             //カウント初期化
        }
        count++;                                                   //カウントアップ処理
    }

    //移動処理
    private void Move() {
        if (vector == LEFT) Movement(-4,2);                 //左の移動処理
        if(vector==RIGHT) Movement(2,1);                    //右の移動処理
    }

    //引数分だけxとｙ座標を移動する
    private void Movement(float _x,float _y){
        x += _x;
        y += _y;
    }

    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //Bitmapの描画
    private void Draw_Bitmap(int _Alpha) {
        if (vector == LEFT) owner.Class_Bmp.DrawBitmap(bmp[0], x, y,_Alpha);       //左向きの時
        if (vector==RIGHT) owner.Class_Bmp.DrawBitmap(bmp[1], x, y,_Alpha);        //右向きの時
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private static Bitmap bmp[]=new Bitmap[2];          //Bitmap用の配列
    private float x;                                    //x座標
    private float y;                                    //y座標
    private int vector;                                //方向を表す
    private int count;                                 //方向転換するタイミングを管理する
    private OriginalView owner=null;                   //OriginalViewを格納

}