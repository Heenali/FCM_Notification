package com.example.heenali.demo_notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String gcmRegID, jsonGcm1;
    UserFunctions UF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UF = new UserFunctions(MainActivity.this);
        GetGCM();

    }
    private void GetGCM()
    {

        gcmRegID = FirebaseInstanceId.getInstance().getToken();
        Log.e("gcm_reg_json", gcmRegID + "");
      //  new RegGCMService().execute();

    }

    private class RegGCMService extends AsyncTask<Void, Void, String>
    {
        private ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

          /*  loading = new ProgressDialog(MainActivity.this);
            loading.getWindow().setBackgroundDrawable(new

                    ColorDrawable(android.graphics.Color.TRANSPARENT));
            loading.setIndeterminate(true);
            loading.setCancelable(false);
            loading.show();
            loading.setContentView(R.layout.my_progress);*/
        }

        @Override
        protected String doInBackground(Void... params) {
            try
            {
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei_num = mngr.getDeviceId();
                JSONObject prmsLogin = new JSONObject();
                prmsLogin.put("UniqueId", "0");
            prmsLogin.put("AppName", "TrukkerUAE");
            prmsLogin.put("DeviceId", gcmRegID.toString());
            prmsLogin.put("TokenId", "AIzaSyBsAz5tFhhmaJ11kdkfjb1zLNViv7dYZAs");
            prmsLogin.put("DeviceInfo", "0");
            prmsLogin.put("OS", "Android");
            prmsLogin.put("IMEINo", imei_num);

            Log.e("gcm_reg_json", prmsLogin + "");

            jsonGcm1 = UF.LoginUser("login/RegisterDevice", prmsLogin);
            //  Log.e("gcm_reg_response", jsonGcm + "");
        } catch (Exception e) {
        // e.printStackTrace();
    }
        return jsonGcm1;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                if (jsonGcm1 != null) {
                    if (jsonGcm1.equalsIgnoreCase("0")) {

                    } else
                        {
                        JSONObject jobj = new JSONObject(jsonGcm1);
                        String status = jobj.getString("status");
                        if (status.equalsIgnoreCase("1")) {

                            //registation of notification done
                            SharedPreferences prefs;
                            String prefName = "Notification_regi";
                            prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("id", 1);
                            editor.commit();




                        } else {
                           /* String servermsg = jobj.getString("message");
                            UF.msg(servermsg);*/
                        }
                    }
                }
                else
                {
                    // UF.msg(String.valueOf(R.string.internet_slow));
                }
            }
            catch (Exception e)
            {
                //UF.msg(String.valueOf(R.string.internet_slow));
            }

        }
    }
}
