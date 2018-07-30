package com.example.akiya.deliverymyfeelings.Game;

/**
 * Created by akiya on 2018/05/17.
 */
import android.graphics.Bitmap;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;

import static com.example.akiya.deliverymyfeelings.Main.OriginalView.SYSTEM_X;
import static com.example.akiya.deliverymyfeelings.Main.OriginalView.SYSTEM_Y;


public class Enemy {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public Enemy(OriginalView _ov) {
        owner=_ov;
        Init();                                      //初期化
    }

    public void Update() {
        if(AppearFlg==false)return;               //描画されていない場合抜ける
        DissapearAnimation();                      //穴に落ちた時のアニメーション用処理
        WallContact();                            //壁の当たり判定
        Move();                                   //移動処理
    }

    public void Draw(){
        if(AppearFlg==false)return;                //描画されないときこの処理を抜ける
        owner.Class_Bmp.DrawBitmap(bmp,x,y);       //描画処理
    }

    //作成された穴の上に敵がいたらアニメーションフラグをtrueにする
    public void HoleJudge(holePos _hole){
        if(_hole.x<x+bmp.getWidth()&&_hole.Scalex>x&&_hole.y<y+bmp.getHeight()&&_hole.Scaley>y){
            AnimationFlg=true;                     //フラグをtrueにする
        }
    }

    //ApperFlgを返す
    public boolean Check_EnemyApperFlg(){
        return AppearFlg;
    }


    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    private static final int TOPMARGIN=160;     //当たり判定をするときの上側の余白
    private static final int SIDEMARGIN=60;     //当たり判定をするときの下側の余白


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //初期化関数
    private void Init() {
        int rand;                                                       //ランダム値を格納する変数

        //座標
        rand=(int)SYSTEM_X-400;                                        //ランダム値を算出する範囲作成
        x=owner.GetRand(rand)+100;                                     //ランダム値を算出(100からrandまでの中から一つ)
        rand=(int)SYSTEM_Y-300;                                       //ランダム値を算出する範囲作成
        y=owner.GetRand(rand)+TOPMARGIN;                             //ランダム値を算出(TOPMARGINからrandまでの中から一つ)

        //移動用変数
        mx=owner.GetRand(13)-6;                                //移動量をランダム値で作成
        my=owner.GetRand(13)-6;                                //移動量をランダム値で作成

        //移動の単純性回避
        if(mx==0)mx=owner.GetRand(15)-8;                       //ランダム値が0だった場合もう一度だけ算出する
        if(my==0)my=owner.GetRand(15)-8;                       //ランダム値が0だった場合もう一度だけ算出する
        bmp=owner.Class_Bmp.SerchBmp("images/Enemy.png");    //ビットマップのロード
    }
    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //壁の当たり判定
    private void WallContact() {
        if (y < TOPMARGIN||y + bmp.getHeight() > SYSTEM_Y -TOPMARGIN/2){       //上下の当たり判定
            my=-my;                                                              //y方向の移動が逆向きに変更
        }
        if (x <SIDEMARGIN ||x + bmp.getWidth() > SYSTEM_X-SIDEMARGIN) {       //左右の当たり判定
            mx=-mx;                                                             //y方向の移動が逆向きに変更
        }
    }

    //移動処理
    private void Move() {
        if(AnimationFlg==false) {                                              //穴に落ちたアニメーションが行われていない場合処理される
            x += mx;                                                            //移動処理
            y += my;                                                            //移動処理
        }
    }

    //穴に落ちた時の処理
    private void DissapearAnimation() {
        if(AnimationFlg==true){                                                //アニメーションフラグがtrueになっているか確認する
            Alpha-=2;                                                           //透明化
            bmp=owner.Class_Bmp.Pixel_Alpha(bmp,Alpha);                       //透明化した画像をbmpに格納する
            if(Alpha<0)AppearFlg=false;                                       //完全に透明化したらAppearFlgをtrueにする
        }
    }



    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private  OriginalView owner=null;                                      //OriginalViewを格納
    public float x=0;                                                      //敵のx座標
    public float y=0;                                                      //敵のy座標
    public float mx=0;                                                     //移動用x方向変数
    public float my=0;                                                     //移動用y方向変数
    public int Alpha=255;                                                  //透明度
    public boolean AppearFlg=true;                                        //描画されているかどうか表すフラグ
    public boolean AnimationFlg=false;                                    //倒された時にtrueになるフラグ
    public Bitmap bmp;                                                      //enemy用bitmap


}

