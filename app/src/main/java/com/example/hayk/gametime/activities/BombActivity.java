package com.example.hayk.gametime.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hayk.gametime.fragments.BombFirstFragment;
import com.example.hayk.gametime.R;
import com.example.hayk.gametime.fragments.BombSecondFragment;

public class BombActivity extends AppCompatActivity {

    public static final String BOMB_FISRT_FRAGMENT = "BombFirstFragment";
    public static final String BOMB_SECOND_FRAGMENT = "BombSecondFragment";

    BombFirstFragment mBombFirstFragment = new BombFirstFragment();
    BombSecondFragment mBombSecondFragment = new BombSecondFragment();
    FragmentManager mFragmentManager = getFragmentManager();
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bomb);

        setTitle("ԲՈՄԲ ԽԱՂ");
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.bombActiviyContainer,mBombFirstFragment,BOMB_FISRT_FRAGMENT);
        mFragmentTransaction.commit();
        BombFirstFragment.onStartClickListener startClickListener = new BombFirstFragment.onStartClickListener() {
            @Override
            public void startClicked(int number) {
                Bundle args = new Bundle();
                args.putInt("Number",number);
                mBombSecondFragment.setArguments(args);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.bombActiviyContainer,mBombSecondFragment,BOMB_SECOND_FRAGMENT);
                mFragmentTransaction.commit();
            }
        };
        mBombFirstFragment.setOnStartListener(startClickListener);
    }

    @Override
    public void onBackPressed() {
        if(mFragmentManager.findFragmentByTag(BOMB_SECOND_FRAGMENT)!=null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.you_realy_want_to_exit));
            builder.setPositiveButton("ԱՅՈ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BombActivity.this.finish();
                }
            });
            builder.setNegativeButton("ՈՉ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            BombActivity.this.finish();
        }
    }
}
