package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SocioEconomicActivity extends AppCompatActivity {

    private Button button_cancel,button_next;
    private TextView socio1,socio2,socio3,socio4,socio5,socio6,socio7,socio8,socio9,socio10,socio11,socio12,socio21,socio22,
            socio31,socio32,socio33,socio41,socio42,socio43,socio51,socio52,socio53,socio61,socio71,socio72,socio73,
            socio81,socio83,socio91,socio92,socio93,socio94,socio95,socio96,socio98,socio99,socio100,socio101,socio102,
            socio103,socio104,socio105,socio106;

    private RadioButton  socio311,socio312,socio321,socio322,socio323,socio331,socio332,socio333,socio334,socio335,socio336,socio337,socio411,
            socio421,socio422,socio431,socio432,socio433,socio434,socio435,socio511,socio512,socio513,socio521,socio522,socio523,
            socio524,socio531,socio532,socio533,socio534,socio535,socio536,socio711,socio712,socio713,socio714,socio721,socio722,socio731,socio732,socio733,socio734,
            socio735,socio736,socio737,socio738,socio811,socio812,socio813,socio814,socio815,socio831,socio832,socio833,socio834,
            socio835,socio836,socio837,socio911,socio912,socio921,socio922,socio931,socio932,socio941,socio942,socio951,socio952,socio961,socio962,
            socio981,socio982,socio991,socio992,socio1001,socio1002,socio1011,socio1012,socio1021,socio1022,socio1031,socio1032,socio1041,
            socio1042,socio1051,socio1052,socio1061,socio1062;

    private Spinner sp_socio1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio_economic);

        button_next=(Button)findViewById(R.id.button_next);
        button_cancel=(Button)findViewById(R.id.button_cancel);
        sp_socio1=(Spinner)findViewById(R.id.sp_socio1);

        socio1=(TextView)findViewById(R.id.socio1);
        socio2=(TextView)findViewById(R.id.socio2);
        socio3=(TextView)findViewById(R.id.socio3);
        socio4=(TextView)findViewById(R.id.socio4);
        socio5=(TextView)findViewById(R.id.socio5);
        socio6=(TextView)findViewById(R.id.socio6);
        socio7=(TextView)findViewById(R.id.socio7);
        socio8=(TextView)findViewById(R.id.socio8);
        socio9=(TextView)findViewById(R.id.socio9);
        socio10=(TextView)findViewById(R.id.socio10);

        socio11=(TextView)findViewById(R.id.socio12);
        socio12=(TextView)findViewById(R.id.socio14);
        socio21=(TextView)findViewById(R.id.socio21);
        socio22=(TextView)findViewById(R.id.socio22);
        socio31=(TextView)findViewById(R.id.socio31);
        socio32=(TextView)findViewById(R.id.socio32);
        socio33=(TextView)findViewById(R.id.socio33);
        socio41=(TextView)findViewById(R.id.socio41);
        socio42=(TextView)findViewById(R.id.socio42);
        socio43=(TextView)findViewById(R.id.socio43);
        socio51=(TextView)findViewById(R.id.socio51);
        socio52=(TextView)findViewById(R.id.socio52);
        socio53=(TextView)findViewById(R.id.socio53);
        socio61=(TextView)findViewById(R.id.socio61);
        socio81=(TextView)findViewById(R.id.socio81);
        socio83=(TextView)findViewById(R.id.socio83);
        socio71=(TextView)findViewById(R.id.socio71);
        socio72=(TextView)findViewById(R.id.socio72);
        socio73=(TextView)findViewById(R.id.socio73);
        socio91=(TextView)findViewById(R.id.socio91);
        socio92=(TextView)findViewById(R.id.socio92);
        socio93=(TextView)findViewById(R.id.socio93);
        socio94=(TextView)findViewById(R.id.socio94);
        socio95=(TextView)findViewById(R.id.socio95);
        socio96=(TextView)findViewById(R.id.socio96);
        socio98=(TextView)findViewById(R.id.socio98);
        socio99=(TextView)findViewById(R.id.socio99);
        socio100=(TextView)findViewById(R.id.socio100);
        socio101=(TextView)findViewById(R.id.socio101);
        socio102=(TextView)findViewById(R.id.socio102);
        socio103=(TextView)findViewById(R.id.socio103);
        socio104=(TextView)findViewById(R.id.socio104);
        socio105=(TextView)findViewById(R.id.socio105);
        socio106=(TextView)findViewById(R.id.socio106);

        socio311=(RadioButton)findViewById(R.id.socio311);
        socio312=(RadioButton)findViewById(R.id.socio312);

        socio321=(RadioButton)findViewById(R.id.socio321);
        socio322=(RadioButton)findViewById(R.id.socio322);
        socio323=(RadioButton)findViewById(R.id.socio323);

        socio331=(RadioButton)findViewById(R.id.socio331);
        socio332=(RadioButton)findViewById(R.id.socio332);
        socio333=(RadioButton)findViewById(R.id.socio333);
        socio334=(RadioButton)findViewById(R.id.socio334);
        socio335=(RadioButton)findViewById(R.id.socio335);
        socio336=(RadioButton)findViewById(R.id.socio336);
        socio337=(RadioButton)findViewById(R.id.socio337);

        socio411=(RadioButton)findViewById(R.id.socio411);

        socio421=(RadioButton)findViewById(R.id.socio421);
        socio422=(RadioButton)findViewById(R.id.socio422);

        socio431=(RadioButton)findViewById(R.id.socio431);
        socio432=(RadioButton)findViewById(R.id.socio432);
        socio433=(RadioButton)findViewById(R.id.socio433);
        socio434=(RadioButton)findViewById(R.id.socio434);
        socio435=(RadioButton)findViewById(R.id.socio435);

        socio511=(RadioButton)findViewById(R.id.socio511);
        socio512=(RadioButton)findViewById(R.id.socio512);
        socio513=(RadioButton)findViewById(R.id.socio513);

        socio521=(RadioButton)findViewById(R.id.socio521);
        socio522=(RadioButton)findViewById(R.id.socio522);
        socio523=(RadioButton)findViewById(R.id.socio523);
        socio524=(RadioButton)findViewById(R.id.socio524);

        socio531=(RadioButton)findViewById(R.id.socio531);
        socio532=(RadioButton)findViewById(R.id.socio532);
        socio533=(RadioButton)findViewById(R.id.socio533);
        socio534=(RadioButton)findViewById(R.id.socio534);
        socio535=(RadioButton)findViewById(R.id.socio535);
        socio536=(RadioButton)findViewById(R.id.socio536);

        socio711=(RadioButton)findViewById(R.id.socio711);
        socio712=(RadioButton)findViewById(R.id.socio712);
        socio713=(RadioButton)findViewById(R.id.socio713);
        socio714=(RadioButton)findViewById(R.id.socio714);

        socio721=(RadioButton)findViewById(R.id.socio721);
        socio722=(RadioButton)findViewById(R.id.socio722);

        socio731=(RadioButton)findViewById(R.id.socio731);
        socio732=(RadioButton)findViewById(R.id.socio732);
        socio733=(RadioButton)findViewById(R.id.socio733);
        socio734=(RadioButton)findViewById(R.id.socio734);
        socio735=(RadioButton)findViewById(R.id.socio735);
        socio736=(RadioButton)findViewById(R.id.socio736);
        socio737=(RadioButton)findViewById(R.id.socio737);
        socio738=(RadioButton)findViewById(R.id.socio738);

        socio811=(RadioButton)findViewById(R.id.socio811);
        socio812=(RadioButton)findViewById(R.id.socio812);
        socio813=(RadioButton)findViewById(R.id.socio813);
        socio814=(RadioButton)findViewById(R.id.socio814);
        socio815=(RadioButton)findViewById(R.id.socio815);

        socio831=(RadioButton)findViewById(R.id.socio831);
        socio832=(RadioButton)findViewById(R.id.socio832);
        socio833=(RadioButton)findViewById(R.id.socio833);
        socio834=(RadioButton)findViewById(R.id.socio834);
        socio835=(RadioButton)findViewById(R.id.socio835);
        socio836=(RadioButton)findViewById(R.id.socio836);
        socio837=(RadioButton)findViewById(R.id.socio837);

        socio911=(RadioButton)findViewById(R.id.socio911);
        socio912=(RadioButton)findViewById(R.id.socio912);

        socio921=(RadioButton)findViewById(R.id.socio921);
        socio922=(RadioButton)findViewById(R.id.socio922);

        socio931=(RadioButton)findViewById(R.id.socio931);
        socio932=(RadioButton)findViewById(R.id.socio932);

        socio941=(RadioButton)findViewById(R.id.socio941);
        socio942=(RadioButton)findViewById(R.id.socio942);

        socio951=(RadioButton)findViewById(R.id.socio951);
        socio952=(RadioButton)findViewById(R.id.socio952);

        socio961=(RadioButton)findViewById(R.id.socio961);
        socio962=(RadioButton)findViewById(R.id.socio962);

        socio981=(RadioButton)findViewById(R.id.socio981);
        socio982=(RadioButton)findViewById(R.id.socio982);

        socio991=(RadioButton)findViewById(R.id.socio991);
        socio992=(RadioButton)findViewById(R.id.socio992);

        socio1001=(RadioButton)findViewById(R.id.socio1001);
        socio1002=(RadioButton)findViewById(R.id.socio1002);

        socio1011=(RadioButton)findViewById(R.id.socio1011);
        socio1012=(RadioButton)findViewById(R.id.socio1012);

        socio1021=(RadioButton)findViewById(R.id.socio1021);
        socio1022=(RadioButton)findViewById(R.id.socio1022);

        socio1031=(RadioButton)findViewById(R.id.socio1031);
        socio1032=(RadioButton)findViewById(R.id.socio1032);

        socio1041=(RadioButton)findViewById(R.id.socio1041);
        socio1042=(RadioButton)findViewById(R.id.socio1042);

        socio1051=(RadioButton)findViewById(R.id.socio1051);
        socio1052=(RadioButton)findViewById(R.id.socio1052);

        socio1061=(RadioButton)findViewById(R.id.socio1061);
        socio1062=(RadioButton)findViewById(R.id.socio1062);





        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
