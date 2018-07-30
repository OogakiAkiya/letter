package com.example.akiya.deliverymyfeelings.Main;


import com.example.akiya.deliverymyfeelings.End.End;
import com.example.akiya.deliverymyfeelings.Game.GameClass;
import com.example.akiya.deliverymyfeelings.Story.StoryMain;
import com.example.akiya.deliverymyfeelings.Title.Title;

public class MainGame {
    //==============================================================================================
    //public
    //==============================================================================================
    //コンストラクタ
    public  MainGame(OriginalView _ov) {
        owner = _ov;
        Init();                                        //初期化
    }

    public void Update () {
        Judge_Update();
    }         //シーンごとのUpdateを呼び出す

    public void Draw() {Judge_Draw();}                //シーンごとのDrawを呼び出す

    //----------------------------------------------------------------------------------------------
    //定数
    //----------------------------------------------------------------------------------------------
    private static final int TITLE=0;               //シーン管理用の定数
    private static final int STORY=1;               //シーン管理用の定数
    private static final int GAME=2;                //シーン管理用の定数
    private static final int END=3;                 //シーン管理用の定数


    //==============================================================================================
    //private
    //==============================================================================================

    //----------------------------------------------------------------------------------------------
    //コンストラクタ内の関数
    //----------------------------------------------------------------------------------------------
    //初期化
    private void Init() {
        Judge=TITLE;                                                                    //初期値設定
        MemoryAllocation();                                                             //引数で渡したシーンのクラスが作成される
        owner.Class_sound.PlayBGM("Sound/op.mp3");                             //BGM再生
        owner.SeId=owner.Class_sound.LoadSE("Sound/se.mp3");                  //SEロード処理
    }
    //----------------------------------------------------------------------------------------------
    //Update内の関数
    //----------------------------------------------------------------------------------------------
    //シーンごとのUpdateシーンチェンジ管理
    private void Judge_Update() {
        switch (Judge) {
            case TITLE:                                                                //タイトル
                if(Class_title.Update()==1){                                          //Updateが1を返して来たらシーンチェンジ
                    Judge=STORY;                                                      //シーンチェンジ
                    MemoryAllocation();                                               //不要なクラスの削除
                }
                break;

            case STORY:                                                              //ストーリー
                if(Class_story.Update()==1){                                         //Updateが1を返して来たらシーンチェンジ
                    Judge=GAME;                                                      //シーンチェンジ
                    MemoryAllocation();                                               //不要なクラスの削除
                }
                break;

            case GAME:                                                               //ゲーム
                if(Class_game.Update()==1){                                         //Updateが1を返して来たらシーンチェンジ
                    score=Class_game.Return_Score()*Class_game.ENEMYAMOUNT*10;    //スコアの作成
                    Judge=END;                                                      //シーンチェンジ
                    MemoryAllocation();                                              //不要なクラスの削除
                }
                break;

            case END:                                                                //リザルト
                if(Class_end.Update()==1){                                          //Updateが1を返して来たらシーンチェンジ
                    owner.Class_sound.PlayBGM("Sound/op.mp3");              //SE再生ゲーム
                    Judge=TITLE;                                                    //シーンチェンジ
                    MemoryAllocation();                                              //不要なクラスの削除
                }
                break;
        }
    }

    //引数で渡されたシーンのクラスを作成
    private void MemoryAllocation(){
        switch(Judge){
            case TITLE:                                                              //タイトル作成
                if(Class_title==null) Class_title=new Title(owner);
                break;
            case STORY:                                                              //ストーリー
                if(Class_story==null)Class_story=new StoryMain(owner);
                break;
            case GAME:                                                               //ゲーム
                if(Class_game==null) Class_game=new GameClass(owner);
                break;
            case END:                                                                //エンド
                if(Class_end==null)Class_end=new End(owner);
                break;
        }
        MemoryRelease();                                                             //必要のないクラスを削除
    }

    //必要のないクラスを削除
    private void MemoryRelease() {
        switch (Judge) {
            case TITLE:                                                             //タイトル
                Class_story = null;
                Class_game = null;
                Class_end = null;
                break;
            case STORY:                                                             //ストーリー
                Class_title = null;
                Class_game = null;
                Class_end = null;
                break;
            case GAME:                                                              //ゲーム
                Class_title = null;
                Class_story = null;
                Class_end = null;
                break;
            case END:                                                              //リザルト
                Class_title = null;
                Class_story = null;
                Class_game = null;
                break;
        }
    }

    //----------------------------------------------------------------------------------------------
    //Draw内の関数
    //----------------------------------------------------------------------------------------------
    //シーンごとの描画
    private void Judge_Draw() {
        switch (Judge) {
            case TITLE:Class_title.Draw();                                        //タイトル
                break;
            case STORY:Class_story.Draw();                                        //ストーリー
                break;
            case GAME:Class_game.Draw();                                          //ゲーム
                break;
            case END:Class_end.Draw(score);                                       //リザルト
                break;
        }
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private int Judge;                                                              //現在のシーンを管理
    private int score;                                                              //スコア
    private OriginalView owner =null;                                               //OriginalViewを格納
    private Title Class_title=null;                                                //タイトルクラス
    private StoryMain Class_story=null;                                            //ストーリークラス
    private GameClass Class_game=null;                                             //ゲームクラス
    private End Class_end=null;                                                    //エンドクラス


}