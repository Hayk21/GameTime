package com.example.hayk.gametime.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.activities.BombActivity;
import com.example.hayk.gametime.activities.RadioActivity;
import com.example.hayk.gametime.activities.RadioOptionsActivity;
import com.example.hayk.gametime.adapters.ScoreItem;
import com.example.hayk.gametime.custom_views.ScorePaper;
import com.example.hayk.gametime.database.DBFunctions;
import com.example.hayk.gametime.database.DBHelper;
import com.example.hayk.gametime.objects.DatabaseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RadioGameFragment extends Fragment {

    ScorePaper mScorePaper;
    TextView mVoice, mSong, mTeamNamePlace, mNumberOfLose, mNumberOfRightAnswers;
    LinearLayout mButtonsLinear, mNumbersLinear;
    ImageView mNoButton, mYesButton;
    Chronometer mTimer;
    Animation mAlphaAnimation, mAlphaAnimation2, mBubbleEffect, mJumpEffect, mAlphaUp, mAlphaDown, mTranslate, mTranslateToLeft;
    DBFunctions mDBFunctions;
    int mQueueOfTeams = 0;
    List<DatabaseObject> mListOfVoice, mRemoveList, mListOfSongs, mRemoveListForSongs;
    Random mRandom;
    DatabaseObject mDatabaseObject, mDatabaseObjectSong;
    ScoreItem mScoreItem;
    FragmentManager mFragmentManager;
    RadioMelodyFragment mRadioMelodyFragment;
    FragmentTransaction mFragmentTransaction;
    boolean mAnyTeamWin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mScorePaper = (ScorePaper) getActivity().findViewById(R.id.score_place);
        mVoice = (TextView) view.findViewById(R.id.voice);
        mSong = (TextView) view.findViewById(R.id.name_of_song);
        mTeamNamePlace = (TextView) view.findViewById(R.id.place_for_team_name);
        mNumberOfLose = (TextView) view.findViewById(R.id.number_of_lose);
        mNumberOfRightAnswers = (TextView) view.findViewById(R.id.number_of_right_answers);
        mButtonsLinear = (LinearLayout) view.findViewById(R.id.buttons_linear);
        mNumbersLinear = (LinearLayout) view.findViewById(R.id.numbers_linear);
        mNoButton = (ImageView) view.findViewById(R.id.no_button);
        mYesButton = (ImageView) view.findViewById(R.id.yes_button);
        mTimer = (Chronometer) view.findViewById(R.id.timer);

        mAlphaAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_for_text);
        mAlphaAnimation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_for_text);
        mBubbleEffect = AnimationUtils.loadAnimation(getActivity(), R.anim.bubble_effect);
        mJumpEffect = AnimationUtils.loadAnimation(getActivity(), R.anim.jump_effect);
        mAlphaUp = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_up);
        mAlphaDown = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_down);
        mTranslate = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_from_left);
        mTranslateToLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_to_left);


        mDBFunctions = new DBFunctions(getActivity());
        mRandom = new Random();
        mFragmentManager = getActivity().getFragmentManager();
        mRadioMelodyFragment = new RadioMelodyFragment();

        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSong.setText(getActivity().getString(R.string.this_voice_is));
                mSong.startAnimation(mAlphaAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAlphaAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSong.setText(mDatabaseObject.getData());
                mSong.startAnimation(mBubbleEffect);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBubbleEffect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mVoice.setVisibility(View.VISIBLE);
                mVoice.setText(mDatabaseObject.getData());
                mSong.setText("");
                mVoice.startAnimation(mJumpEffect);
                if (mQueueOfTeams == 0) {
                    mTeamNamePlace.setText(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE));
                } else {
                    mTeamNamePlace.setText(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_TWO));
                }
                mTeamNamePlace.setVisibility(View.VISIBLE);
                mNumberOfLose.setVisibility(View.VISIBLE);
                mNumberOfRightAnswers.setVisibility(View.VISIBLE);
                mTeamNamePlace.startAnimation(mTranslate);
                mNumberOfLose.startAnimation(mAlphaUp);
                mNumberOfRightAnswers.startAnimation(mAlphaUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        mJumpEffect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startTimer();
                mNoButton.setClickable(true);
                mYesButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTimer.setCountDown(true);
            mTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    long second = SystemClock.elapsedRealtime() - mTimer.getBase();

                    if (second > 0) {
                        if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TIME_NUMBER) != null) {
                            mSong.setText(getActivity().getString(R.string.time_end));
                        } else {
                            String word = getActivity().getString(R.string.without_team_text_1).concat(" ").concat(mNumberOfRightAnswers.getText().toString()).concat(" ").concat(getActivity().getString(R.string.without_team_text_2));
                            mSong.setText(word);
                        }
                        functionsAfterGame();
                    }
                }
            });
        } else {
            mTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    long second = SystemClock.elapsedRealtime() - mTimer.getBase();
                    long time;
                    if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TIME_NUMBER) != null) {
                        time = Long.parseLong(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TIME_NUMBER)) * 1000;
                    } else {
                        time = 90000;
                    }
                    if (second > time) {
                        if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TIME_NUMBER) != null) {
                            String word = getActivity().getString(R.string.without_team_text_1).concat(" ").concat(mNumberOfRightAnswers.getText().toString()).concat(" ").concat(getActivity().getString(R.string.without_team_text_2));
                            mSong.setText(word);
                        } else {
                            String word = getActivity().getString(R.string.without_team_text_1).concat(" ").concat(mNumberOfRightAnswers.getText().toString()).concat(" ").concat(getActivity().getString(R.string.without_team_text_2));
                            mSong.setText(word);
                        }
                        functionsAfterGame();
                    }
                }
            });
        }

        mSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null) {
                    if (!mAnyTeamWin) {
                        mQueueOfTeams++;
                        startGame();
                        mNumberOfLose.setText("7");
                        mNumberOfRightAnswers.setText("0");
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(getActivity().getString(R.string.play_again_or_exit));
                        builder.setPositiveButton("ԽԱՂԱԼ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mQueueOfTeams++;
                                mScorePaper.restartGame();
                                startGame();
                            }
                        });
                        builder.setNegativeButton("ԴՈՒՐՍ ԳԱԼ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    String word = getActivity().getString(R.string.without_team_text_1).concat(" ").concat(mNumberOfRightAnswers.getText().toString()).concat(" ").concat(getActivity().getString(R.string.without_team_text_2));
                    if (mNumberOfLose.getText().toString().equals("0") || mSong.getText().toString().equals(word)) {
                        mDBFunctions.updateBombGameWorks(mRemoveList, DBHelper.RADIO_VOICE_TABLE);
                        mDBFunctions.updateBombGameWorks(mRemoveListForSongs, getArguments().getString(RadioActivity.MELODY));
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.radio_game_activity, mRadioMelodyFragment, RadioActivity.RADIO_MELODY_FRAGMENT);
                        mFragmentTransaction.commit();
                    } else {
                        if (mVoice.getText().toString().equals(getActivity().getString(R.string.choose_the_voice_without_teams))) {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.before_choose_voice), Toast.LENGTH_SHORT).show();
                        } else {
                            mVoice.setClickable(false);
                            startTimer();
                        }
                    }
                }
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNumber = Integer.parseInt(mNumberOfLose.getText().toString());
                if (currentNumber != 0) {
                    currentNumber--;
                    String currentText = ((Integer) currentNumber).toString();
                    mNumberOfLose.setText(currentText);
                    if (mListOfSongs.isEmpty()) {
                        if (!mRemoveListForSongs.isEmpty()) {
                            mDBFunctions.updateBombGameWorks(mRemoveListForSongs, getArguments().getString(RadioActivity.MELODY));
                            mRemoveListForSongs.clear();
                            mListOfSongs = mDBFunctions.getData(getArguments().getString(RadioActivity.MELODY), DBHelper.RADIO_SONG, "20");
                        }
                    }
                    int number = mRandom.nextInt(mListOfSongs.size());
                    mDatabaseObjectSong = mListOfSongs.get(number);
                    mListOfSongs.remove(number);
                    mRemoveListForSongs.add(mDatabaseObjectSong);
                    mSong.setText(mDatabaseObjectSong.getData());
                } else {
                    mSong.setTextSize(30);
                    if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null) {
                        mSong.setText(getActivity().getString(R.string.chance_end));
                    } else {
                        String word = getActivity().getString(R.string.without_team_text_1).concat(" ").concat(mNumberOfRightAnswers.getText().toString()).concat(" ").concat(getActivity().getString(R.string.without_team_text_2));
                        mSong.setText(word);
                    }
                    functionsAfterGame();
                }
            }
        });

        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNumber = Integer.parseInt(mNumberOfRightAnswers.getText().toString());
                currentNumber++;
                String currentText = ((Integer) currentNumber).toString();
                mNumberOfRightAnswers.setText(currentText);
                if (mListOfSongs.isEmpty()) {
                    if (!mRemoveListForSongs.isEmpty()) {
                        mDBFunctions.updateBombGameWorks(mRemoveListForSongs, getArguments().getString(RadioActivity.MELODY));
                        mRemoveListForSongs.clear();
                        mListOfSongs = mDBFunctions.getData(getArguments().getString(RadioActivity.MELODY), DBHelper.RADIO_SONG, "20");
                    }
                }
                int number = mRandom.nextInt(mListOfSongs.size());
                mDatabaseObjectSong = mListOfSongs.get(number);
                mListOfSongs.remove(number);
                mRemoveListForSongs.add(mDatabaseObjectSong);
                mSong.setText(mDatabaseObjectSong.getData());
            }
        });

        mVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListOfVoice != null) {
                    if (mListOfVoice.isEmpty()) {
                        if (!mRemoveList.isEmpty()) {
                            mDBFunctions.updateBombGameWorks(mRemoveList, DBHelper.RADIO_VOICE_TABLE);
                            mRemoveList.clear();
                            mListOfVoice = mDBFunctions.getData(DBHelper.RADIO_VOICE_TABLE, DBHelper.RADIO_VOICE, "20");
                        }
                    }
                } else {
                    mListOfVoice = mDBFunctions.getData(DBHelper.RADIO_VOICE_TABLE, DBHelper.RADIO_VOICE, "20");
                }
                int number = mRandom.nextInt(mListOfVoice.size());
                mDatabaseObject = mListOfVoice.get(number);
                mListOfVoice.remove(number);
                if (mRemoveList != null) {
                    mRemoveList.add(mDatabaseObject);
                } else {
                    mRemoveList = new ArrayList<>();
                    mRemoveList.add(mDatabaseObject);
                }

                mVoice.setTextSize(40);
                mVoice.setText(mDatabaseObject.getData());
            }
        });
        if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null) {
            mVoice.setClickable(false);
            mSong.setClickable(false);
        } else {
            mVoice.setClickable(true);
            mSong.setClickable(true);
        }
        mNoButton.setClickable(false);
        mYesButton.setClickable(false);

        startGame();

    }

    public void startGame() {
        if (getArguments() != null) {
            if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null) {

                if (mListOfVoice != null) {
                    if (mListOfVoice.isEmpty()) {
                        if (!mRemoveList.isEmpty()) {
                            mDBFunctions.updateBombGameWorks(mRemoveList, DBHelper.RADIO_VOICE_TABLE);
                            mRemoveList.clear();
                            mListOfVoice = mDBFunctions.getData(DBHelper.RADIO_VOICE_TABLE, DBHelper.RADIO_VOICE, "20");
                        }
                    }
                } else {
                    mListOfVoice = mDBFunctions.getData(DBHelper.RADIO_VOICE_TABLE, DBHelper.RADIO_VOICE, "20");
                }
                int number = mRandom.nextInt(mListOfVoice.size());
                mDatabaseObject = mListOfVoice.get(number);
                mListOfVoice.remove(number);
                if (mRemoveList != null) {
                    mRemoveList.add(mDatabaseObject);
                } else {
                    mRemoveList = new ArrayList<>();
                    mRemoveList.add(mDatabaseObject);
                }

                switch (mQueueOfTeams) {
                    case 0:
                        mSong.setTextSize(35);
                        mSong.setText(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE));
                        mSong.startAnimation(mAlphaAnimation);
                        break;
                    case 1:
                        mSong.setTextSize(35);
                        mSong.setText(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_TWO));
                        mSong.startAnimation(mAlphaAnimation);
                        break;
                    case 2:
                        Bundle args = new Bundle();
                        if (mAnyTeamWin) {
                            args.putBoolean(RadioActivity.BOOLEAN_FOR_QUEUE_OF_TEAMS, true);
                        } else if (mScorePaper.getCount()) {
                            args.putBoolean(RadioActivity.BOOLEAN_FOR_QUEUE_OF_TEAMS, true);
                        } else {
                            args.putBoolean(RadioActivity.BOOLEAN_FOR_QUEUE_OF_TEAMS, false);
                        }
                        mRadioMelodyFragment.setArguments(args);
                        mDBFunctions.updateBombGameWorks(mRemoveList, DBHelper.RADIO_VOICE_TABLE);
                        mDBFunctions.updateBombGameWorks(mRemoveListForSongs, getArguments().getString(RadioActivity.MELODY));
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.radio_game_activity, mRadioMelodyFragment, RadioActivity.RADIO_MELODY_FRAGMENT);
                        mFragmentTransaction.commit();
                        break;
                }

            } else {
                mTeamNamePlace.setVisibility(View.GONE);
                mNumberOfLose.setVisibility(View.VISIBLE);
                mNumberOfRightAnswers.setVisibility(View.VISIBLE);

                mVoice.setTextSize(30);
                mVoice.setText(getActivity().getString(R.string.choose_the_voice_without_teams));
                mSong.setText(getActivity().getString(R.string.play_radio_without_teams));
            }
        }
    }

    public void startTimer() {
        long time;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TIME_NUMBER) != null) {
                time = Long.parseLong(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TIME_NUMBER)) * 1000;
            } else {
                mNoButton.setClickable(true);
                mYesButton.setClickable(true);
                mSong.setClickable(false);
                time = 90000;
            }
            mTimer.setBase(SystemClock.elapsedRealtime() + time);
        }
        mTimer.start();
        if (mListOfSongs != null) {
            if (mListOfSongs.isEmpty()) {
                if (!mRemoveListForSongs.isEmpty()) {
                    mDBFunctions.updateBombGameWorks(mRemoveListForSongs, DBHelper.RADIO_VOICE_TABLE);
                    mRemoveListForSongs.clear();
                    mListOfSongs = mDBFunctions.getData(getArguments().getString(RadioActivity.MELODY), DBHelper.RADIO_SONG, "20");
                }
            }
        } else {
            mListOfSongs = mDBFunctions.getData(getArguments().getString(RadioActivity.MELODY), DBHelper.RADIO_SONG, "20");
        }
        int number = mRandom.nextInt(mListOfSongs.size());
        mDatabaseObjectSong = mListOfSongs.get(number);
        mListOfSongs.remove(number);
        if (mRemoveListForSongs != null) {
            mRemoveListForSongs.add(mDatabaseObjectSong);
        } else {
            mRemoveListForSongs = new ArrayList<>();
            mRemoveListForSongs.add(mDatabaseObjectSong);
        }

        mSong.setText(mDatabaseObjectSong.getData());
    }

    public void functionsAfterGame() {
        mTimer.stop();
        if (getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null) {
            if (mQueueOfTeams == 0) {
                mScoreItem = new ScoreItem();
                String firstTeam = mNumberOfRightAnswers.getText().toString();
                mScoreItem.setFirstTeamScore(firstTeam);
                mScoreItem.setSecondTeamScore("-");
                mScorePaper.addAdapterItem(mScoreItem);
            } else {
                String secondTeam = mNumberOfRightAnswers.getText().toString();
                mScoreItem.setSecondTeamScore(secondTeam);
                mScorePaper.addAdapterItem(mScoreItem);
                int[] array = mScorePaper.getFinalScores();
                int point = getActivity().getIntent().getIntExtra(RadioOptionsActivity.POINTS_NUMBER, 0);
                if (array[0] >= point && array[1] >= point) {
                    getActivity().getIntent().putExtra(RadioOptionsActivity.POINTS_NUMBER, point + 10);
                    mSong.setTextSize(30);
                    mSong.setText(getActivity().getString(R.string.game_continue));
                } else if (array[0] >= point) {
                    mSong.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                    mSong.setTextSize(35);
                    String word = getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE).concat(" ").concat(getActivity().getString(R.string.team_win));
                    mSong.setText(word);
                    mAnyTeamWin = true;
                } else if (array[1] >= point) {
                    mSong.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                    mSong.setTextSize(35);
                    String word = getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_TWO).concat(" ").concat(getActivity().getString(R.string.team_win));
                    mSong.setText(word);
                    mAnyTeamWin = true;
                }
            }
            mNoButton.setClickable(false);
            mYesButton.setClickable(false);
            mNumberOfLose.startAnimation(mAlphaDown);
            mNumberOfRightAnswers.startAnimation(mAlphaDown);
            mVoice.startAnimation(mAlphaDown);
            mVoice.setVisibility(View.INVISIBLE);
            mTeamNamePlace.startAnimation(mTranslateToLeft);
            mTeamNamePlace.setVisibility(View.INVISIBLE);
            mNumberOfLose.setVisibility(View.INVISIBLE);
            mNumberOfRightAnswers.setVisibility(View.INVISIBLE);
            mSong.setClickable(true);
        } else {
            mNoButton.setClickable(false);
            mYesButton.setClickable(false);
            mSong.setClickable(true);
        }
    }
}
