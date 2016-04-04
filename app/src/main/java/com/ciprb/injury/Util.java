package com.ciprb.injury;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by coder on 4/1/16.
 */
public class Util extends AppCompatActivity {

    private static Util util = null;
    private Util() {

    }

    public StringBuilder readFile(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {

            InputStream inputStream = openFileInput("/data/data/com.ciprb.injury/files/"+fileName);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
        }
        catch (Exception e) {
            Log.e("Exception", "File read failed: " + e.toString());
        }
        return stringBuilder;
    }


    public void writeToFile(String fileName, String data) {
        try {

            FileOutputStream outputStreamWriter = openFileOutput(fileName, Context.MODE_APPEND);
            outputStreamWriter.write(data.getBytes());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static Util getInstance(){

        if(util == null) {
            util = new Util();
        }

        return util;
    }

    /*

    JSONParser parser = new JSONParser();
    public JSONArray getJsonArray(String fileName) {

        try {

            JSONArray jsonArray = null;
            InputStream inputStream = openFileInput(fileName);
            Object obj = parser.parse(new InputStreamReader(inputStream));
            if(obj != null) {

                jsonArray = (JSONArray)obj;
                Log.d("AMLOG:: ", "houseHoldList: " + jsonArray.size());
                for(int i = 0; i < jsonArray.size(); i++) {
                    Log.d("AMLOG:: ", "houseHold: " + jsonArray.get(i));
                }
            }
            inputStream.close();
            return jsonArray;

        }
        catch (Exception e) {
            Log.e("getJsonArray", "File write failed: " + e.toString());
        }
        return null;
    }
*/

}
