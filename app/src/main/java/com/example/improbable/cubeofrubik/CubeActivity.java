package com.example.improbable.cubeofrubik;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class CubeActivity extends AppCompatActivity {

    public int move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        move = 1000;
        displayCube(findViewById(R.id.a00));
    }
    public void displayCube(View view) {
        ((TextView)findViewById(R.id.a03)).setText((Character.toString(Engine.a[5][0][0])));
        ((TextView)findViewById(R.id.a04)).setText((Character.toString(Engine.a[5][0][1])));
        ((TextView)findViewById(R.id.a05)).setText((Character.toString(Engine.a[5][0][2])));
        ((TextView)findViewById(R.id.a13)).setText((Character.toString(Engine.a[5][1][0])));
        ((TextView)findViewById(R.id.a14)).setText((Character.toString(Engine.a[5][1][1])));
        ((TextView)findViewById(R.id.a15)).setText((Character.toString(Engine.a[5][1][2])));
        ((TextView)findViewById(R.id.a23)).setText((Character.toString(Engine.a[5][2][0])));
        ((TextView)findViewById(R.id.a24)).setText((Character.toString(Engine.a[5][2][1])));
        ((TextView)findViewById(R.id.a25)).setText((Character.toString(Engine.a[5][2][2])));

        ((TextView)findViewById(R.id.a30)).setText((Character.toString(Engine.a[3][0][0])));
        ((TextView)findViewById(R.id.a31)).setText((Character.toString(Engine.a[3][0][1])));
        ((TextView)findViewById(R.id.a32)).setText((Character.toString(Engine.a[3][0][2])));
        ((TextView)findViewById(R.id.a40)).setText((Character.toString(Engine.a[3][1][0])));
        ((TextView)findViewById(R.id.a41)).setText((Character.toString(Engine.a[3][1][1])));
        ((TextView)findViewById(R.id.a42)).setText((Character.toString(Engine.a[3][1][2])));
        ((TextView)findViewById(R.id.a50)).setText((Character.toString(Engine.a[3][2][0])));
        ((TextView)findViewById(R.id.a51)).setText((Character.toString(Engine.a[3][2][1])));
        ((TextView)findViewById(R.id.a52)).setText((Character.toString(Engine.a[3][2][2])));

        ((TextView)findViewById(R.id.a33)).setText((Character.toString(Engine.a[0][0][0])));
        ((TextView)findViewById(R.id.a34)).setText((Character.toString(Engine.a[0][0][1])));
        ((TextView)findViewById(R.id.a35)).setText((Character.toString(Engine.a[0][0][2])));
        ((TextView)findViewById(R.id.a43)).setText((Character.toString(Engine.a[0][1][0])));
        ((TextView)findViewById(R.id.a44)).setText((Character.toString(Engine.a[0][1][1])));
        ((TextView)findViewById(R.id.a45)).setText((Character.toString(Engine.a[0][1][2])));
        ((TextView)findViewById(R.id.a53)).setText((Character.toString(Engine.a[0][2][0])));
        ((TextView)findViewById(R.id.a54)).setText((Character.toString(Engine.a[0][2][1])));
        ((TextView)findViewById(R.id.a55)).setText((Character.toString(Engine.a[0][2][2])));

        ((TextView)findViewById(R.id.a36)).setText((Character.toString(Engine.a[2][0][0])));
        ((TextView)findViewById(R.id.a37)).setText((Character.toString(Engine.a[2][0][1])));
        ((TextView)findViewById(R.id.a38)).setText((Character.toString(Engine.a[2][0][2])));
        ((TextView)findViewById(R.id.a46)).setText((Character.toString(Engine.a[2][1][0])));
        ((TextView)findViewById(R.id.a47)).setText((Character.toString(Engine.a[2][1][1])));
        ((TextView)findViewById(R.id.a48)).setText((Character.toString(Engine.a[2][1][2])));
        ((TextView)findViewById(R.id.a56)).setText((Character.toString(Engine.a[2][2][0])));
        ((TextView)findViewById(R.id.a57)).setText((Character.toString(Engine.a[2][2][1])));
        ((TextView)findViewById(R.id.a58)).setText((Character.toString(Engine.a[2][2][2])));

        ((TextView)findViewById(R.id.a36)).setText((Character.toString(Engine.a[2][0][0])));
        ((TextView)findViewById(R.id.a37)).setText((Character.toString(Engine.a[2][0][1])));
        ((TextView)findViewById(R.id.a38)).setText((Character.toString(Engine.a[2][0][2])));
        ((TextView)findViewById(R.id.a46)).setText((Character.toString(Engine.a[2][1][0])));
        ((TextView)findViewById(R.id.a47)).setText((Character.toString(Engine.a[2][1][1])));
        ((TextView)findViewById(R.id.a48)).setText((Character.toString(Engine.a[2][1][2])));
        ((TextView)findViewById(R.id.a56)).setText((Character.toString(Engine.a[2][2][0])));
        ((TextView)findViewById(R.id.a57)).setText((Character.toString(Engine.a[2][2][1])));
        ((TextView)findViewById(R.id.a58)).setText((Character.toString(Engine.a[2][2][2])));

        ((TextView)findViewById(R.id.a63)).setText((Character.toString(Engine.a[1][0][0])));
        ((TextView)findViewById(R.id.a64)).setText((Character.toString(Engine.a[1][0][1])));
        ((TextView)findViewById(R.id.a65)).setText((Character.toString(Engine.a[1][0][2])));
        ((TextView)findViewById(R.id.a73)).setText((Character.toString(Engine.a[1][1][0])));
        ((TextView)findViewById(R.id.a74)).setText((Character.toString(Engine.a[1][1][1])));
        ((TextView)findViewById(R.id.a75)).setText((Character.toString(Engine.a[1][1][2])));
        ((TextView)findViewById(R.id.a83)).setText((Character.toString(Engine.a[1][2][0])));
        ((TextView)findViewById(R.id.a84)).setText((Character.toString(Engine.a[1][2][1])));
        ((TextView)findViewById(R.id.a85)).setText((Character.toString(Engine.a[1][2][2])));

        ((TextView)findViewById(R.id.a93)).setText((Character.toString(Engine.a[4][0][0])));
        ((TextView)findViewById(R.id.a94)).setText((Character.toString(Engine.a[4][0][1])));
        ((TextView)findViewById(R.id.a95)).setText((Character.toString(Engine.a[4][0][2])));
        ((TextView)findViewById(R.id.a103)).setText((Character.toString(Engine.a[4][1][0])));
        ((TextView)findViewById(R.id.a104)).setText((Character.toString(Engine.a[4][1][1])));
        ((TextView)findViewById(R.id.a105)).setText((Character.toString(Engine.a[4][1][2])));
        ((TextView)findViewById(R.id.a113)).setText((Character.toString(Engine.a[4][2][0])));
        ((TextView)findViewById(R.id.a114)).setText((Character.toString(Engine.a[4][2][1])));
        ((TextView)findViewById(R.id.a115)).setText((Character.toString(Engine.a[4][2][2])));
        for (int i=0; i<12; i++)
            for (int j=0; j<9; j++) {
                int resID = getResources().getIdentifier("a"+i+j, "id", getPackageName());
                TextView tV = ((TextView) findViewById(resID));
                Log.d("TAGA", "a"+i+j);
                if (tV.getText().toString().equals("R"))
                    tV.setBackgroundColor(Color.parseColor("#FF0000"));
                if (tV.getText().toString().equals("B"))
                    tV.setBackgroundColor(Color.parseColor("#0000FF"));
                if (tV.getText().toString().equals("G"))
                    tV.setBackgroundColor(Color.parseColor("#00FF00"));
                if (tV.getText().toString().equals("Y"))
                    tV.setBackgroundColor(Color.parseColor("#FFFF00"));
                if (tV.getText().toString().equals("O"))
                    tV.setBackgroundColor(Color.parseColor("#FFA500"));
                if (tV.getText().toString().equals("W"))
                    tV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (tV.getText().toString().equals(" "))
                    tV.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
    }
    public void doScramble(View view) {
        String ret = "";

        try {
            InputStream inputStream = this.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        /*try{
            Engine.scramble(25);
            displayCube(findViewById(R.id.a00));
            ((Button) findViewById(R.id.scramble)).setEnabled(false);
            ((Button) findViewById(R.id.solve)).setEnabled(true);
        }
        catch (Exception e)
        {
        }*/
        Toast.makeText(this, ret, Toast.LENGTH_LONG).show();
    }

    public void doSolve(View view) {
        String data = Engine.playerMoveList;
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();

        /*try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }*/

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        /*try {
            if (move >= Engine.moves_list.size()) {
                Engine.moves_list = new ArrayList<>();
                Engine.solve();
                ((Button) findViewById(R.id.solve)).setText("Next");
                ((Button) findViewById(R.id.scramble)).setEnabled(false);
                move = 0;
                ((TextView) findViewById(R.id.moves)).setText("Solved in "+Engine.moves_list.size()+" steps. Click next to go through it");
            }
            else {
                ((TextView) findViewById(R.id.moves)).setText(Engine.moves_list.get(move)+" ("+(move+1)+"/"+Engine.moves_list.size()+")");
                Engine.move(Engine.moves_list.get(move));
                displayCube(findViewById(R.id.a00));
                move += 1;
                if (move >= Engine.moves_list.size()) {
                    ((Button) findViewById(R.id.solve)).setText("Solve");
                    ((Button) findViewById(R.id.scramble)).setEnabled(true);
                    ((Button) findViewById(R.id.solve)).setEnabled(false);
                }
            }
        }
        catch (Exception e)
        {

        }*/
    }

    public void addToList(String data)
    {
        Engine.playerMoveList += data+";";
    }

    public void doMovement(View view) throws Exception {
        switch (view.getId()) {
            case (R.id.li):
                Engine.move("li");
                addToList("li");
                break;
            case (R.id.l):
                Engine.move("l");
                addToList("l");
                break;
            case (R.id.r):
                Engine.move("r");
                addToList("r");
                break;
            case (R.id.ri):
                Engine.move("ri");
                addToList("ri");
                break;
            case (R.id.bi):
                Engine.move("bi");
                addToList("bi");
                break;
            case (R.id.b):
                Engine.move("b");
                addToList("b");
                break;
            case (R.id.u):
                Engine.move("u");
                addToList("u");
                break;
            case (R.id.ui):
                Engine.move("ui");
                addToList("ui");
                break;
            case (R.id.d):
                Engine.move("d");
                addToList("d");
                break;
            case (R.id.di):
                Engine.move("di");
                addToList("di");
                break;
            case (R.id.f):
                Engine.move("f");
                addToList("f");
                break;
            case (R.id.fi):
                Engine.move("fi");
                addToList("fi");
                break;
        }
        displayCube(view);
    }
}
