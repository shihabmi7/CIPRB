package com.ciprb.injury;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ciprb.injury.localdb.CiprbDatabase;

import java.io.IOException;

public class HouseHoldCharacteristicsActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "HouseHoldCharacteristicsActivity";
    private Context mContext = this;
    private ProgressDialog progressDialog;
    private Button button_cancel, button_next;
    private Spinner spinner_c03, spinner_c04, spinner_c05, spinner_c07, spinner_c08, spinner_c10;
    EditText edit_c01, edit_c02, edittext_c06, edittext_c11;

    //String person_id = "101323211";
    String person_id = "";
    TextView textView_person_id;

    PrefsValues prefsValues;
    Context context = this;

    RadioGroup rg_radio, rg_television, rg_disher_line, rg_mobile, rg_telephone, rg_refregarator, rg_bicycle,
            rg_motorcycle, rg_vcd, rg_electric_fan, rg_car_microbus, rg_boat, rg_water_pump, rg_almari, rg_table, rg_chair, rg_computer, rg_bed,
            rg_sofa;
    CiprbDatabase ciprbDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_characteristics);

        ciprbDatabase = new CiprbDatabase(context);
        ciprbDatabase.open();


        try {

            setTitle(getResources().getStringArray(R.array.survey_activity_title)[5]);
            initUI();

            prefsValues = new PrefsValues(mContext);
            // String value = prefsValues.getHouseUniqueId();

            String value = prefsValues.getPerson_id_for_house_hold_characteristics();

            int member = prefsValues.getMembersNo();

            if (value.length() > 0) {

                person_id = value;
                textView_person_id.setText("House Hold ID: " + value);

//                for (Person aPerson : ciprbDatabase.getAlivePersonList()) {
//
//                    person_id = aPerson.getPerson_id();
//                    textView_person_id.setText("House Hold ID: " + value);
//                    ciprbDatabase.close();
//                    break;
//                    //  list.add(aPerson.getMembers_name() + "." + aPerson.getPerson_id());
//                }

            } else {

                Toast.makeText(context, "Set House ID First && save a alive person data first", Toast.LENGTH_SHORT).show();
                finish();

            }


            //person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        }


    }

    private void initUI() {

        textView_person_id = (TextView) findViewById(R.id.textView_person_id);
        edit_c01 = (EditText) findViewById(R.id.edit_c01);
        edit_c02 = (EditText) findViewById(R.id.edit_c02);
        edittext_c06 = (EditText) findViewById(R.id.edittext_c06);
        edittext_c11 = (EditText) findViewById(R.id.edittext_c11);

        spinner_c03 = (Spinner) findViewById(R.id.spinner_c03);
        spinner_c04 = (Spinner) findViewById(R.id.spinner_c04);
        spinner_c05 = (Spinner) findViewById(R.id.spinner_c05);
        spinner_c07 = (Spinner) findViewById(R.id.spinner_c07);
        spinner_c08 = (Spinner) findViewById(R.id.spinner_c08);
        spinner_c10 = (Spinner) findViewById(R.id.spinner_c10);

        rg_radio = (RadioGroup) findViewById(R.id.rg_radio);
        rg_television = (RadioGroup) findViewById(R.id.rg_television);
        rg_disher_line = (RadioGroup) findViewById(R.id.rg_disher_line);
        rg_mobile = (RadioGroup) findViewById(R.id.rg_mobile);
        rg_telephone = (RadioGroup) findViewById(R.id.rg_telephone);
        rg_refregarator = (RadioGroup) findViewById(R.id.rg_refregarator);
        rg_bicycle = (RadioGroup) findViewById(R.id.rg_bicycle);
        rg_motorcycle = (RadioGroup) findViewById(R.id.rg_motorcycle);
        rg_vcd = (RadioGroup) findViewById(R.id.rg_vcd);
        rg_electric_fan = (RadioGroup) findViewById(R.id.rg_electric_fan);
        rg_car_microbus = (RadioGroup) findViewById(R.id.rg_car_microbus);
        rg_boat = (RadioGroup) findViewById(R.id.rg_boat);
        rg_water_pump = (RadioGroup) findViewById(R.id.rg_water_pump);
        rg_almari = (RadioGroup) findViewById(R.id.rg_almari);
        rg_table = (RadioGroup) findViewById(R.id.rg_table);
        rg_chair = (RadioGroup) findViewById(R.id.rg_chair);
        rg_computer = (RadioGroup) findViewById(R.id.rg_computer);
        rg_bed = (RadioGroup) findViewById(R.id.rg_bed);
        rg_sofa = (RadioGroup) findViewById(R.id.rg_sofa);


        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    void resetRadio() {


        rg_radio.clearCheck();
        rg_television.clearCheck();
        rg_disher_line.clearCheck();
        rg_mobile.clearCheck();
        rg_telephone.clearCheck();
        rg_refregarator.clearCheck();
        rg_bicycle.clearCheck();
        rg_motorcycle.clearCheck();
        rg_vcd.clearCheck();
        rg_electric_fan.clearCheck();
        rg_car_microbus.clearCheck();
        rg_boat.clearCheck();
        rg_water_pump.clearCheck();
        rg_almari.clearCheck();
        rg_table.clearCheck();
        rg_chair.clearCheck();
        rg_computer.clearCheck();
        rg_bed.clearCheck();
        rg_sofa.clearCheck();
    }

    String getRadioButtonGroupData() {


        String result = "";
        try {

            // get value from radio button

            if (rg_radio.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += "radio:" + rg_radio.indexOfChild(findViewById(rg_radio.getCheckedRadioButtonId()));
            }
            if (rg_television.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",television:" + rg_television.indexOfChild(findViewById(rg_television.getCheckedRadioButtonId()));
            }
            if (rg_disher_line.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",disher line:" + rg_disher_line.indexOfChild(findViewById(rg_disher_line.getCheckedRadioButtonId()));
            }
            if (rg_mobile.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",mobile:" + rg_mobile.indexOfChild(findViewById(rg_mobile.getCheckedRadioButtonId()));
            }
            if (rg_telephone.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",telephone:" + rg_telephone.indexOfChild(findViewById(rg_telephone.getCheckedRadioButtonId()));
            }
            if (rg_refregarator.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",fridge:" + rg_refregarator.indexOfChild(findViewById(rg_refregarator.getCheckedRadioButtonId()));
            }
            if (rg_bicycle.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",bicycle:" + rg_bicycle.indexOfChild(findViewById(rg_bicycle.getCheckedRadioButtonId()));
            }
            if (rg_motorcycle.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",motorcycle:" + rg_motorcycle.indexOfChild(findViewById(rg_motorcycle.getCheckedRadioButtonId()));
            }
            if (rg_vcd.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",vcd:" + rg_vcd.indexOfChild(findViewById(rg_vcd.getCheckedRadioButtonId()));
            }
            if (rg_electric_fan.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",electric fan:" + rg_electric_fan.indexOfChild(findViewById(rg_electric_fan.getCheckedRadioButtonId()));
            }
            if (rg_car_microbus.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",car:" + rg_car_microbus.indexOfChild(findViewById(rg_car_microbus.getCheckedRadioButtonId()));
            }
            if (rg_boat.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",boat:" + rg_boat.indexOfChild(findViewById(rg_boat.getCheckedRadioButtonId()));
            }
            if (rg_water_pump.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",water pump:" + rg_water_pump.indexOfChild(findViewById(rg_water_pump.getCheckedRadioButtonId()));
            }
            if (rg_almari.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",almari:" + rg_almari.indexOfChild(findViewById(rg_almari.getCheckedRadioButtonId()));
            }
            if (rg_table.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",table:" + rg_table.indexOfChild(findViewById(rg_table.getCheckedRadioButtonId()));
            }
            if (rg_chair.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",chair:" + rg_chair.indexOfChild(findViewById(rg_chair.getCheckedRadioButtonId()));
            }
            if (rg_computer.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",computer:" + rg_computer.indexOfChild(findViewById(rg_computer.getCheckedRadioButtonId()));
            }
            if (rg_bed.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",bed:" + rg_bed.indexOfChild(findViewById(rg_bed.getCheckedRadioButtonId()));
            }
            if (rg_sofa.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",sofa:" + rg_sofa.indexOfChild(findViewById(rg_sofa.getCheckedRadioButtonId()));
            }

            //  Toast.makeText(activity, result, Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();

        }


        return result.toString();
    }


    boolean checkSpinner() {


        if (spinner_c03.getSelectedItemPosition() != 0
                && spinner_c04.getSelectedItemPosition() != 0
                && spinner_c05.getSelectedItemPosition() != 0
                && spinner_c07.getSelectedItemPosition() != 0 &&
                spinner_c08.getSelectedItemPosition() != 0 &&
                edit_c01.getText().length() > 0 &&
                edit_c02.getText().length() > 0 &&
                edittext_c06.getText().length() > 0 &&
                edittext_c11.getText().length() > 0) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;

        } else {

            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();

            return false;
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == button_cancel) {

            this.finish();

        } else if (v == button_next && checkSpinner()) {

            if (InternetConnection.checkNetworkConnection(context)) {


                String url = ApplicationData.URL_CHARACTERISTIC + person_id;
                new PutAsync().execute(url, createJsonBody());

                Log.e("JSOn BOdy ", createJsonBody());
                Log.e("URL are ", url);

            } else

            {
                showAlert(this);

            }

        }

    }

    public void showAlert(final Activity activity) {

        if (InternetConnection.isAvailable(activity)) {


        } else {

            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage("Please check your connectivity.")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // finish();

                                }

                            })
                    .setNegativeButton("Retry",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Stop the activity
                                    //showAlert(activity);

                                }

                            }).show();
        }

    }


    String createJsonBody() {
        String jsonData = "{" +
                "\"c01\":\"" +
                edit_c01.getText().toString() +
                "\",\"c02\":\"" +
                edit_c02.getText().toString() +
                "\",\"c03\":\"" +
                ApplicationData.spilitStringFirst(spinner_c03.getSelectedItem().toString()) +
                "\",\"c04\":\"" +
                ApplicationData.spilitStringFirst(spinner_c04.getSelectedItem().toString()) +
                "\",\"c05\":\"" +
                ApplicationData.spilitStringFirst(spinner_c05.getSelectedItem().toString()) +
                "\",\"c06\":\"" +
                edittext_c06.getText().toString() +
                "\",\"c07\":\"" +
                ApplicationData.spilitStringFirst(spinner_c07.getSelectedItem().toString()) +
                "\",\"c08\":\"" +
                ApplicationData.spilitStringFirst(spinner_c08.getSelectedItem().toString()) +
                "\",\"c09\":\"" +
                getRadioButtonGroupData() +
                "\",\"c10\":\"" +
                ApplicationData.spilitStringFirst(spinner_c10.getSelectedItem().toString()) +
                "\",\"c11\":\"" +
                edittext_c11.getText().toString() +
                "\"}";
        return jsonData;
    }

    private class PutAsync extends AsyncTask<String, Void, String> {
        int value = 0;
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                Log.e("URL are ", params[0]);
                Log.e("URL are ", createJsonBody());
                // working code below
                value = ApplicationData.putRequestWithBody(params[0], params[1]);
                // result = ApplicationData.putRequestWithHeaderAndBody(params[0], params[1]);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

//            if (result.
//                    isEmpty()) {
//
//                person_id = value +"0101";
//
//                String url = ApplicationData.URL_CHARACTERISTIC + person_id;
//                new PutAsync().execute(url, createJsonBody());
//
//            } else {
//
//                prefsValues.setHouse_characteristics(true);
//
//                Toast.makeText(context, "SuccessFully Saved...", Toast.LENGTH_LONG).show();
//                finish();
//
//            }

            if (value == ApplicationData.STATUS_SUCCESS) {
                //// TODO: 3/22/2016

                prefsValues.setHouse_characteristics(true);

                Toast.makeText(context, "SuccessFully Saved...", Toast.LENGTH_LONG).show();
                finish();

            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
            }


        }

    }
}
