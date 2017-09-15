package com.example.hayk.gametime.fragments;


import android.app.Fragment;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.database.DBFunctions;
import com.example.hayk.gametime.database.DBHelper;
import com.example.hayk.gametime.objects.DatabaseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class BombSecondFragment extends Fragment {
    GifImageView mBombImage;
    TextView mTextLooser, mTextWork,mTextQuestion;
    SoundPool mSoundPool;
    int mSoundOfTimer, mSoundOfBoom, mNumberOfPlayers;
    boolean mTetxChanged = false;
    Runnable mRun;
    Handler mMyHandler;
    DBFunctions mDBFunctions;
    DatabaseObject mDatabaseObject, mDatabaseObject2;
    List<DatabaseObject> mList,mListOfQuestions, mRemoveList = new ArrayList<>(),mRemoveListOfQuestions = new ArrayList<>();
    Random mRandom = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bomb_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mDBFunctions = new DBFunctions(getActivity());
        mBombImage = (GifImageView) view.findViewById(R.id.bomb_image);
        ((GifDrawable) mBombImage.getDrawable()).stop();
        mTextLooser = (TextView) view.findViewById(R.id.text_for_looser);
        mTextWork = (TextView) view.findViewById(R.id.work_text);
        mTextQuestion = (TextView) view.findViewById(R.id.text_for_question);

        mTextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextLooser.setVisibility(View.GONE);
                mBombImage.setVisibility(View.VISIBLE);
                mBombImage.setImageResource(R.drawable.bomb_gif4);
                mBombImage.setClickable(true);
                ((GifDrawable) mBombImage.getDrawable()).stop();
                mTextQuestion.setText(getString(R.string.place_for_question));
                mTextQuestion.setClickable(false);
                mTextWork.setText(getString(R.string.choose_work));
                mTextWork.setClickable(true);
            }
        });

        mTextQuestion.setClickable(false);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundOfTimer = mSoundPool.load(getActivity(), R.raw.taymer2, 1);
        mSoundOfBoom = mSoundPool.load(getActivity(), R.raw.boom2, 1);
        mNumberOfPlayers = getArguments().getInt("Number");
        mBombImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((GifDrawable) mBombImage.getDrawable()).isPlaying() && mTetxChanged) {
                    mTextWork.setClickable(false);
                    if (mListOfQuestions == null) {
                        mListOfQuestions = mDBFunctions.getData(DBHelper.BOMB_TABLE,DBHelper.BOMB_QUESTION,"10");
                        int number = mRandom.nextInt(mListOfQuestions.size());
                        mDatabaseObject2 = mListOfQuestions.get(number);
                        mListOfQuestions.remove(number);
                        mRemoveListOfQuestions.add(mDatabaseObject2);
                        mTextQuestion.setText(mDatabaseObject2.getData());
                    } else if (!mListOfQuestions.isEmpty()) {
                        int number = mRandom.nextInt(mListOfQuestions.size());
                        mDatabaseObject2 = mListOfQuestions.get(number);
                        mListOfQuestions.remove(number);
                        mRemoveListOfQuestions.add(mDatabaseObject2);
                        mTextQuestion.setText(mDatabaseObject2.getData());
                    } else if (!mRemoveListOfQuestions.isEmpty()) {
                        mDBFunctions.updateBombGameWorks(mRemoveListOfQuestions,DBHelper.BOMB_TABLE);
                        mRemoveListOfQuestions.clear();
                        mListOfQuestions = mDBFunctions.getData(DBHelper.BOMB_TABLE,DBHelper.BOMB_QUESTION,"10");
                        int number = mRandom.nextInt(mListOfQuestions.size());
                        mDatabaseObject2 = mListOfQuestions.get(number);
                        mListOfQuestions.remove(number);
                        mRemoveListOfQuestions.add(mDatabaseObject2);
                        mTextQuestion.setText(mDatabaseObject2.getData());
                    }
                    ((GifDrawable) mBombImage.getDrawable()).start();
                    mSoundPool.play(mSoundOfTimer, 1, 1, 0, 33, 2);
                    mMyHandler = new Handler();
                    mRun = new Runnable() {
                        @Override
                        public void run() {
                            mSoundPool.pause(mSoundOfTimer);
                            mSoundPool.play(mSoundOfBoom, 1, 1, 0, 1, 1);
                            mBombImage.setImageResource(R.drawable.finish);
                            ((GifDrawable) mBombImage.getDrawable()).setLoopCount(2);
                            ((GifDrawable) mBombImage.getDrawable()).addAnimationListener(new AnimationListener() {
                                @Override
                                public void onAnimationCompleted(int loopNumber) {
                                    if (loopNumber == 1) {
                                        mBombImage.setVisibility(View.GONE);
                                        mTextLooser.setVisibility(View.VISIBLE);
                                        mTextQuestion.setText(getString(R.string.play_again));
                                        mTextQuestion.setClickable(true);
                                    }
                                }
                            });
                        }
                    };
                    mMyHandler.postDelayed(mRun, mNumberOfPlayers * 5000);
                }else if(!((GifDrawable) mBombImage.getDrawable()).isPlaying() && !mTetxChanged){
                    Toast.makeText(getActivity(), getString(R.string.before_bomb), Toast.LENGTH_SHORT).show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Կանգնեցնել խախը");
                    builder.setPositiveButton("ԱՅՈ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mBombImage.setImageResource(R.drawable.bomb_gif4);
                            ((GifDrawable) mBombImage.getDrawable()).stop();
                            mSoundPool.autoPause();
                            mMyHandler.removeCallbacks(mRun);
                        }
                    });
                    builder.setNegativeButton("ՈՉ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        mTextWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTetxChanged && mList == null) {
                    mList = mDBFunctions.getData(DBHelper.BOMB_TABLE_WORKS,DBHelper.BOMB_WORK,"10");
                    int number = mRandom.nextInt(mList.size());
                    mDatabaseObject = mList.get(number);
                    mList.remove(number);
                    mRemoveList.add(mDatabaseObject);
                    mTextWork.setText(mDatabaseObject.getData());
                    mTetxChanged = true;
                } else if (!mList.isEmpty()) {
                    int number = mRandom.nextInt(mList.size());
                    mDatabaseObject = mList.get(number);
                    mList.remove(number);
                    mRemoveList.add(mDatabaseObject);
                    mTextWork.setText(mDatabaseObject.getData());
                } else if (!mRemoveList.isEmpty()) {
                    mDBFunctions.updateBombGameWorks(mRemoveList,DBHelper.BOMB_TABLE_WORKS);
                    mRemoveList.clear();
                    mList = mDBFunctions.getData(DBHelper.BOMB_TABLE_WORKS,DBHelper.BOMB_WORK,"10");
                    int number = mRandom.nextInt(mList.size());
                    mDatabaseObject = mList.get(number);
                    mList.remove(number);
                    mRemoveList.add(mDatabaseObject);
                    mTextWork.setText(mDatabaseObject.getData());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mRemoveList != null &&!mRemoveList.isEmpty()){
            mDBFunctions.updateBombGameWorks(mRemoveList,DBHelper.BOMB_TABLE_WORKS);
        }
        if(mRemoveListOfQuestions != null && !mRemoveListOfQuestions.isEmpty()){
            mDBFunctions.updateBombGameWorks(mRemoveListOfQuestions,DBHelper.BOMB_TABLE);
        }
        if (mRun != null) {
            mSoundPool.autoPause();
            mMyHandler.removeCallbacks(mRun);
        }
    }
}
