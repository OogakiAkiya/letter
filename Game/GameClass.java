package com.example.akiya.deliverymyfeelings.Game;

import android.graphics.Bitmap;
import java.util.ArrayList;
import com.example.akiya.deliverymyfeelings.Main.OriginalView;
import com.example.akiya.deliverymyfeelings.Library.Number;


public class GameClass {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public GameClass(OriginalView _ov) {
        owner = _ov;
        Init();                         //初期化
        LoadBitmap();                   //Bitmapのロード
    }

    public int Update() {
        Class_Play.Update();            //自機処理
        Enemy_Update();                  //敵機処理
        CreateHole();                    //新しい穴を作成する
        Hole_Update();                   //穴処理
        return End();                   //ゲーム終了処理
    }

    public void Draw() {
        BackGround_Draw();              //背景描画
        Class_Play.Draw();             //自機の描画
        Enemy_Draw();                   //敵機の描画
        UI_Draw();                      //UI関連描画
    }

    //スコアを返す
    public int Return_Score(){
        return RemainPercent;
    }

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    public static final int ENEMYAMOUNT=10;                //敵の総数


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //初期化
    private void  Init() {
        Class_Play = new Player(owner);                                           //自機クラス
        for (int ec = 0; ec < Class_EList.length; ec++) {
            Class_EList[ec] = new Enemy(owner);                                   //敵機クラスのリスト
        }
        Class_number=new Number(owner);                                           //スコアを管理するクラス
        LetterArea=owner.SYSTEM_X*(owner.SYSTEM_Y-200);                         //操作外面の面積を算出
    }

    //Bitmapのロード処理
    private void  LoadBitmap (){
        LetterFront=owner.Class_Bmp.SerchBmp("images/背景front.png");         //手前の手紙の画像
        LetterBack=owner.Class_Bmp.SerchBmp("images/手紙バック.png");        //裏側の手紙画像
        LetterShadow=owner.Class_Bmp.SerchBmp("images/LetterShadow.png");   //手紙の影
        Table=owner.Class_Bmp.SerchBmp("images/Table.png");                  //背景
        Fade= owner.Class_Bmp.SerchBmp("images/Fade.png");                   //フェード処理用
        UI_Percent=owner.Class_Bmp.SerchBmp("images/Letter%.png");          //「％」の画像
        UI_String=owner.Class_Bmp.SerchBmp("images/UI.png");                //UI画像
        Class_number.LoadBitmap("images/Letter",".png");          //スコア表示用数字画像
    }

    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //敵機処理
    private void Enemy_Update() {
        for (int ec = 0; ec < Class_EList.length; ec++) {
            if (Class_EList[ec] != null) {
                Class_EList[ec].Update();
            }
        }
    }

    //Holeが薄く消えていくためにUpdateを呼び出す処理
    private void Hole_Update() {
        for(int element=0;element<Class_HoleList.size();element++){
            LetterFront=Class_HoleList.get(element).Update(LetterFront);
        }
    }

    //穴の作成
    private void CreateHole() {
        if(Class_Play.Return_HolePermission()==true) {
            if(Class_HoleList.isEmpty())owner.Class_sound.PlayBGM("Sound/Horror.mp3");      //SE変更処理
            hole obj = new hole(owner);                                                              //holeの作成
            obj.Create(Class_Play.ReturnPos());                                                     //作成後の処理
            Judge_OverlapHole(obj.ReturnPos());                                                      //表示されている背景の面積を求める
            for (int ec = 0; ec < Class_EList.length; ec++) {
                Class_EList[ec].HoleJudge(obj.ReturnPos());                                         //敵の判定
            }
            Class_HoleList.add(obj);                                                                //holeをリストに追加
            Class_Play.ResetPlayerHolePos();                                                        //自機の保持する穴の座標を初期化
            Class_Play.ResetHoleFlg();                                                              //穴作成フラグリセット
        }
    }

    //穴を作成時重複している穴の面積をカウントしないようにする
    private void Judge_OverlapHole(holePos _judgedHole){
        LetterArea-=(_judgedHole.Scalex-_judgedHole.x)*(_judgedHole.Scaley-_judgedHole.y);         //穴をあけたところの面積をマイナスする
        RemainPercent=(int)(LetterArea/(600*640)*100);                                             //合計からパーセンテージを求める
        for(hole element:Class_HoleList){
            LetterArea+=Get_OverlapArea(element,_judgedHole);                                       //重なっていた部分を足す
        }
    }

    //既存のholeと重なった時に重なっている面積を求める
    private float Get_OverlapArea(hole _obj,holePos _judgedHole){
        float x,y;
        if(Judge_Overlap(_obj,_judgedHole)) {                                                       //重なっていたらその面積を算出し返り値として渡す
            x=Get_OverlapAreaX(_obj,_judgedHole);                                                   //重なっているときのX方向の長さを求める
            y=Get_OverlapAreaY(_obj,_judgedHole);                                                   //重なっているときのY方向の長さを求める
            return x*y;
        }
        return 0;
    }

    //重なっているか判断する
    private boolean Judge_Overlap(hole _obj,holePos _judgedHole) {
        if (_obj.ReturnPos().x < _judgedHole.Scalex &&
                _obj.ReturnPos().Scalex > _judgedHole.x &&
                _obj.ReturnPos().y < _judgedHole.Scaley &&
                _obj.ReturnPos().Scaley > _judgedHole.y)return true;
                return false;
    }

    //重なっているときのX方向の長さを求める
    private float Get_OverlapAreaX(hole _obj,holePos _judgedHole){
        float x,Scalex;
        if(_obj.ReturnPos().x<_judgedHole.x){                                                       //x座標で値が大きい方をxに代入する
            x=_judgedHole.x;
        }else{
            x=_obj.ReturnPos().x;
        }

        if(_obj.ReturnPos().Scalex<_judgedHole.Scalex) {                                           //x座標で値が小さい方をxに代入する
            Scalex = _obj.ReturnPos().Scalex;
        }else{
            Scalex=_judgedHole.Scalex;
        }

        return Scalex-x;                                                                            //返り値はxの長さ
    }

    // Get_OverlapAreaY
    // 概要：重なっているときのY方向の長さを求める
    // 引数：_obj :
    //       _
    // 戻り値：
    private float Get_OverlapAreaY(hole _obj,holePos _judgedHole){
        float y,Scaley;
        if(_obj.ReturnPos().y<_judgedHole.y){                                                       //y座標で値が大きい方をyに代入する
            y=_judgedHole.y;
        }else{
            y=_obj.ReturnPos().y;
        }

        if(_obj.ReturnPos().Scaley<_judgedHole.Scaley) {                                           //y座標で値が小さい方をyに代入する
            Scaley = _obj.ReturnPos().Scaley;
        }else{
            Scaley=_judgedHole.Scaley;
        }

        return Scaley-y;                                                                            //返り値はyの長さ
    }

    //敵が全て描画されなくなったら返り値に1を返す
    private int End() {
        if(EndCount>0)EndCount+=1;                                                                 //フェードアウト処理用のカウントアップ処理
        for (int ec = 0; ec < Class_EList.length; ec++) {
            if (Class_EList[ec].Check_EnemyApperFlg()){                                            //敵が描画されているかどうか確認する
                break;
            }else{
                if(ec==Class_EList.length-1)EndCount++;                                           //フェードアウト処理のカウントアップ処理開始
            }
        }
        if (EndCount>255) {return 1;}                                                              //フェードアウト終了後返り値1を渡す
        return 0;
    }

    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //背景描画
    private void BackGround_Draw(){
        owner.Class_Bmp.DrawBitmap(Table,0,-100);                                               //背景描画
        owner.Class_Bmp.DrawBitmap(LetterShadow,58,Class_Play.TOPMARGIN+20,99);       //手紙の影描画
        owner.Class_Bmp.DrawBitmap(LetterBack,38,Class_Play.TOPMARGIN);                          //裏側の手紙描画
        owner.Class_Bmp.DrawBitmap(LetterFront,38,Class_Play.TOPMARGIN);                         //表側の手紙描画
    }

    //敵機描画
    private void Enemy_Draw() {
        for (int ec = 0; ec < Class_EList.length; ec++) {
            Class_EList[ec].Draw();                                                                     //敵機描画
        }
    }

    //UI描画
    private void UI_Draw(){
        Class_number.DrawNumber(RemainPercent,480,30,-15,255);                   //スコアを描画
        owner.Class_Bmp.DrawBitmap(UI_Percent,540,30);                                          //％を描画
        owner.Class_Bmp.DrawBitmap(UI_String,130,30);                                           //文字描画
        if(EndCount!=0)owner.Class_Bmp.DrawBitmap(Fade,0,0,EndCount);                          //フェードアウト処理
    }


    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Bitmap LetterFront;                                                                     //手前の手紙Bitmap
    private Bitmap LetterBack;                                                                      //裏側の手紙Bitmap
    private Bitmap LetterShadow;                                                                    //手紙の影Bitmap
    private Bitmap Table;                                                                           //机Bitmap
    private Bitmap Fade;                                                                            //フェードインアウトBitmap
    private Bitmap UI_Percent;                                                                      //%のBitmap
    private Bitmap UI_String;                                                                       //文字Bitmap
    private float LetterArea;                                                                       //手紙の面積
    private int RemainPercent=100;                                                                  //穴の開いていないところのパーセンテージ
    private int EndCount=0;                                                                         //カウンター
    private Player Class_Play = null;                                                               //自機クラス
    private Enemy[] Class_EList=new Enemy[ENEMYAMOUNT];                                            //敵機クラスリスト
    private ArrayList<hole> Class_HoleList=new ArrayList<hole>();                                     //穴クラス可変長配列
    private Number Class_number=null;                                                              //スコア表示用クラス
    private OriginalView owner =null;                                                              //OriginalViewを格納

}
