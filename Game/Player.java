package com.example.akiya.deliverymyfeelings.Game;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;
import static com.example.akiya.deliverymyfeelings.Main.OriginalView.SYSTEM_X;
import static com.example.akiya.deliverymyfeelings.Main.OriginalView.SYSTEM_Y;


public class Player {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public Player(OriginalView _ov) {
        owner = _ov;
        Class_HolePos=new holePos(_ov);                         //穴の座標を一時保持するクラス
        Load_Bitmap();                                           //Bitmapロード処理
    }

    public void Update() {
        if(Vector==0) Init();                                   //初期座標設定
        Touch_First();                                          //タッチ処理始点記録
        Vector = ContactDecision_Autside();                    //壁の当たり判定
        Touch_Second();                                        //タッチ処理終点記録
        Move();                                                //移動処理
        Animation();                                           //アニメーション処理
        if(count<255)count++;                                 //フェードイン
    }

    public void Draw() {PlayerBitmap_Draw();}

    //holeflgを返す
    public void ResetHoleFlg(){holeflg=0;}

    //一時保管していた座標を返す
    public holePos ReturnPos() {
        holePos newhole = new holePos(owner);                  //返り値用のクラスを作成
        newhole.x = Class_HolePos.x;                          //x座標をセットする
        newhole.y = Class_HolePos.y;                          //y座標をセットする
        newhole.Scalex = Class_HolePos.Scalex;               //Scalex座標をセットする
        newhole.Scaley = Class_HolePos.Scaley;               //Scaley座標をセットする
        return newhole;                                       //作成したものを返り値とする
    }

    //holePosflgを返す
    public boolean Return_HolePermission() {
        return holePosflg;
    }

    //一時保管用のクラスの値をリセット
    public void ResetPlayerHolePos(){Class_HolePos.Reset();}

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    public static final int DOWN=4;                                  //自機の方向下
    public static final int UP=3;                                    //自機の方向上
    public static final int LEFT=2;                                 //自機の方向左
    public static final int RIGHT=1;                                //自機の方向右
    public static final int TOPMARGIN=120;                         //画面の上側の余白
    public static final int AMOUNTMOVEMENT=10;                    //移動量


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    private void Load_Bitmap() {
        player[0] = owner.Class_Bmp.SerchBmp("images/自機.png");                           //自機の画像
        player[1] = owner.Class_Bmp.SerchBmp("images/自機Open.png");
    }
    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //初期化
    private void Init() {
        x=(owner.SYSTEM_X)/2;                                                                      //初期座標x
        y=owner.SYSTEM_Y-player[0].getWidth();                                                    //初期座標y
        Vector=LEFT;                                                                               //自機進む方向
        owner.ResetOnDown();                                                                       //タッチ状態初期化
        Class_HolePos.Reset();                                                                    //穴の一時保管クラスを初期化
    }

    //タッチされている位置を取得
    private void Touch_First() {
            if (owner.OnDown() == 1) {                                                             //画面がタッチされている場合
                Touch_x = owner.GetTX();                                                          //タッチされたところのx座標を保存
                Touch_y = owner.GetTY();                                                          //タッチされたところのy座標を保存
            }
    }

    //手を離した位置を取得
    private void Touch_Second() {
            if (owner.OnDown() == 2) {                                                                       //画面から手が離れた時
                Touch_afterx = owner.GetTX();                                                               //手を離れたx座標を保存
                Touch_aftery = owner.GetTY();                                                               //手を離れたy座標を保存
                if (holeflg > 1 && holePosflg == false) Class_HolePos.Update(player[0],x, y,Vector);     //一回以上方向転換が行われているとき穴座標の更新を行う(壁にぶつかると初期化される)
                MoveDesision();                                                                              //取得した座標から自機の進む方向を決める
            }
    }

    //外側の壁当たり判定
    private int ContactDecision_Autside() {
        if (Vector == UP) {                                                                         //上側の壁の当たり判定
            if (y < TOPMARGIN-(player[0].getWidth()/2)) {                                          //当たり判定を行う位置
                HoleMake_Permission();                                                              //穴を作成するか判断する
                return RIGHT;                                                                      //自機が右に進むように処理する
            }
        }
        if (Vector == DOWN) {                                                                       //下側の壁の当たり判定
            if (y +player[0].getHeight() > SYSTEM_Y ) {                                            //当たり判定を行う位置
                HoleMake_Permission();                                                              //穴を作成するか判断する
                return LEFT;                                                                       //自機が左に進むように処理する
            }
        }
        if (Vector == RIGHT) {                                                                     //右側の壁の当たり判定
            if (x + player[0].getWidth() > SYSTEM_X) {                                             //当たり判定を行う位置
                HoleMake_Permission();                                                              //穴を作成するか判断する
                return DOWN;                                                                       //自機が下に進むように処理する
            }
        }
        if (Vector == LEFT) {                                                                      //左側の壁の当たり判定
            if (x < 15) {                                                                           //当たり判定を行う位置
                HoleMake_Permission();                                                              //穴を作成するか判断する
                return UP;                                                                         //自機が上に進むように処理する
            }
        }
        if(holePosflg==true)holePosflg=false;                                                     //もし作成された後であったならフラグの初期化
        return Vector;                                                                             //壁に当たっていなかったら今現在の方向を返り値としてもつ
    }

    //穴の作成が可能かどうか判断する
    private void HoleMake_Permission() {
        if(holePosflg==false&&holeflg>2)holePosflg = true;
    }

    //タッチされた情報から進む方向を決める
    private void MoveDesision() {
        //上
        if (Touch_aftery - Touch_y < -80 ) {                                                      //タッチした時から離した瞬間
            if(Vector!=UP&&Vector!=DOWN) {                                                        //来た方向に戻れないようにする
                Vector = UP;                                                                       //上に移動
                HoleFlg_Count();                                                                    //フラグのカウントアップと穴の初期座標設定
            }
        }
        //下
        if (Touch_aftery - Touch_y > 80 ) {                                                       //タッチした時から離した瞬間
            if(Vector!=UP&&Vector!=DOWN) {                                                        //来た方向に戻れないようにする
                Vector = DOWN;                                                                     //下に移動
                HoleFlg_Count();                                                                    //フラグのカウントアップと穴の初期座標設定
            }
        }
        //右
        if (Touch_afterx - Touch_x > 80 ) {                                                        //タッチした時から離した瞬間
            if (Vector != LEFT && Vector != RIGHT) {                                              //来た方向に戻れないようにする
                Vector = RIGHT;                                                                    //右に移動
                HoleFlg_Count();                                                                    //フラグのカウントアップと穴の初期座標設定
            }
        }
        //左
        if (Touch_afterx - Touch_x < -80 ) {                                                      //タッチした時から離した瞬間
            if(Vector!=LEFT&&Vector!=RIGHT) {                                                     //来た方向に戻れないようにする
                Vector = LEFT;                                                                     //左に移動
                HoleFlg_Count();                                                                    //フラグのカウントアップと穴の初期座標設定
            }
        }
        owner.ResetOnDown();                                                                        //タッチ状態をリセット
        holePosflg = false;                                                                        //イレギュラーが起こらないための穴作成フラグの初期化
    }

    //フラグのカウントアップと穴の初期座標設定
    private void HoleFlg_Count() {
        holeflg++;                                                                                  //カウントアップ処理
        if (holeflg == 2) {                                                                         //自機が方向を二回変更したとき初期座標とする
            Class_HolePos.Init(x,y);                                                               //初期座標の設定
        }
    }

    //移動処理
    private void Move() {
        switch (Vector) {
            case UP:                                                                               //上方向の移動
                y -= AMOUNTMOVEMENT;
                break;
            case DOWN:                                                                             //下方向の移動
                y += AMOUNTMOVEMENT;
                break;
            case RIGHT:                                                                            //右方向の移動
                x += AMOUNTMOVEMENT;
                break;
            case LEFT:                                                                             //左方向の移動
                x -= AMOUNTMOVEMENT;
                break;
        }

    }

    //アニメーション処理
    private void Animation() {
        if(AnimationCounter>5) {                                                                  //アニメーションカウンターが5以上になったら以下の処理を通る
            if (BitmapStatas == 0) {
                BitmapStatas = 1;                                                                  //描画用Bitmap配列の要素の値をここで決定する
            } else {
                BitmapStatas = 0;                                                                  //描画用Bitmap配列の要素の値をここで決定する
            }
            AnimationCounter=0;                                                                    //アニメーションカウンターが5以上になったら0にリセット
        }
        AnimationCounter++;                                                                        //カウントアップ処理
    }


    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //自機描画
    private  void PlayerBitmap_Draw() {
        if(Vector==UP)owner.Class_Bmp.DrawBitmap(player[BitmapStatas], x, y, 0, 1.0, 1.0);          //上方向に移動中の自機描画
        if(Vector==DOWN)owner.Class_Bmp.DrawBitmap(player[BitmapStatas], x, y, 180, 1.0, 1.0);      //下方向に移動中の自機描画
        if(Vector==LEFT)owner.Class_Bmp.DrawBitmap(player[BitmapStatas], x, y, 270, 1.0, 1.0);      //左方向に移動中の自機描画
        if(Vector==RIGHT)owner.Class_Bmp.DrawBitmap(player[BitmapStatas], x, y, 90, 1.0, 1.0);      //右方向に移動中の自機描画
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Bitmap player[]=new Bitmap[2];              //自機Bitmap
    private int BitmapStatas=0;                         //Bitmapの配列の要素
    private int AnimationCounter=0;                    //アニメーション用カウンター
    private float Touch_x=0;                            //一度目のタッチｘ
    private float Touch_y=0;                            //一度目のタッチｙ
    private float Touch_afterx=0;                       //二度目のタッチｘ
    private float Touch_aftery=0;                       //二度目のタッチy
    private float x=0;                                   //座標ｘ
    private float y=0;                                   //座標ｙ
    private int Vector=0;                                //自機方向保持
    private int count=0;                                 //フェードイン用のカウンター
    private int holeflg=0;                               //穴を作成するために何点の座標をとっているのかを管理
    private boolean holePosflg=false;                   //穴を作成してよいか判断するフラグ
    private holePos Class_HolePos=null;                 //穴の座標を一時保存する
    private OriginalView owner =null;                    //OriginalViewを格納

}
