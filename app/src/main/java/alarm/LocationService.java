package alarm;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.fragment.assessment.GPSTracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunil on 25-10-2016.
 */
public class LocationService extends Service {

    private boolean gps_enabled = false;
    String gpsenable;
    private LocationListener locListener;
    private LocationManager locManager;
    Handler ha = new Handler();
    //    private LocationListener locListener = new MyLocationListener();
    GPSTracker gps;
    String dev_id,latitude,longitude,altitiude,accuracy,time,degree,speed;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        StartForground();
        checklocation();
        return START_STICKY;

//        int res = super.onStartCommand(intent, flags, startId);


        /*if (gps_enabled) {
//            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
        }*/




        /*gps = new GPSTracker(this);
        new CountDownTimer(30000000, 60000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
                Log.d("shubham location","timer");
                if(gps.canGetLocation()) {

                    final double latitude = gps.getLatitude();
                    final double longitude = gps.getLongitude();
                    final double speed = gps.getLatitude();
                    Log.d("shubham", latitude + "\n" + longitude);
                    String address="";
//                    LocationAddress.getAddressFromLocation(latitude,longitude,LocationService.this,new GeocoderHandler());
                    Geocoder geocoder = new Geocoder(LocationService.this, Locale.ENGLISH);

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if(addresses != null) {
                            Address returnedAddress = addresses.get(0);
                            StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                            for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                            }
                            Log.d("shubham",strReturnedAddress.toString());
                            address=strReturnedAddress.toString();
                            if(address.equals("")){
//                                LocationUpdateAPI(latitude+"",longitude);
                            }
                            else{
//                                LocationUpdateAPI(address,longitude);
                            }
                        }
                        else{
                            Log.d("shubham","No Address returned!");
//                                LocationUpdateAPI(latitude+"",longitude);
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.d("shubham","Canont get Address!");
//                        LocationUpdateAPI(latitude+"",longitude);
                    }
                }
            }

            public void onFinish() {
                start();
            }

        }.start();*/


    }

    private void checklocation() {
        new CountDownTimer(30000000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("shubhamlocation","It's true");
                if (gps_enabled){
                    gpsenable="1";
                }
                else{
                    gpsenable="0";
                }
                locListener = new MyLocationListener();
                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not supporting binding
        return null;
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                // This needs to stop getting the location data and save the battery power.
//                locManager.removeUpdates(locListener);

//               for(int sp=0; sp<location.getLatitude()-1)
                 longitude = location.getLongitude()+"";
                 latitude = location.getLatitude()+"";
                Double lon = location.getLatitude();

                Double lan = location.getLongitude();
                 altitiude = location.getAltitude()+"";
                 accuracy = location.getAccuracy()+"";
                 time = location.getTime()+"";
                 degree = location.getBearing()+"";
                 speed = location.getSpeed()+"";
//				speedinmph = 16;
//				 speedtest=16;
                int speedinmph = (int) ((location.getSpeed() * 2236) / 1000);
                int speedinkmhr = (int) ((location.getSpeed() * 3600) / 1000);
try {
    addAGps();
}
catch (Exception e){
    e.printStackTrace();
}

                Log.d("shubhamlocation", longitude + " " + " " + latitude + speed + "" + " " + speedinkmhr + "");

                /*Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(getActivity(), Locale.getDefault());
				try {
					addresses = geocoder.getFromLocation(lon, lan, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

//					address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
					 city = addresses.get(0).getLocality();
					 state = addresses.get(0).getSubLocality();
					 country = addresses.get(0).getCountryName();
					String postalCode = addresses.get(0).getPostalCode();
					String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
				}
				catch (Exception e){
					e.printStackTrace();
				}*/
				/*SharedPreferences sp = getActivity().getSharedPreferences("LoginLocation", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
                *//*SharedPreferences sharedPreferences=getSharedPreferences("Logi", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sharedPreferences.edit();*//*
//				editor.putString(sharrrrr, String.valueOf(city + state+country));
				editor.putString(sharrrrrlatlng, latitude + londitude);
                *//*edit.putString("nm",Value);
                edit.putBoolean("acceptedtrue",true);*//*
				editor.commit();*/
//                edit.commit();
//				Toast.makeText(getActivity(), "Password Created Successfully..", Toast.LENGTH_LONG).show();
//					int speedmph = (int) (location.getSpeed() * 2.2369);

//                editTextShowLocation.setText(londitude + "\n" + latitude + "\n" + altitiude + "\n" + accuracy + "\n" + time + "\n" + "Speed in MPH: " + speedinmph + "mph");

				/*x = location.getLatitude();
				y = location.getLongitude();*/

				/*try {
					geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
					addresses = geocoder.getFromLocation(x, y, 1);
					StringBuilder str = new StringBuilder();
					if (geocoder.isPresent()) {
						Toast.makeText(getActivity(),
								"geocoder present", Toast.LENGTH_SHORT).show();
						Address returnAddress = addresses.get(0);

						String localityString = returnAddress.getLocality();
						String city = returnAddress.getCountryName();
						String region_code = returnAddress.getCountryCode();
						String zipcode = returnAddress.getPostalCode();

						str.append(localityString + "");
						str.append(city + "" + region_code + "");
						str.append(zipcode + "");

						address.setText(str);
						Toast.makeText(getActivity(), str,
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getActivity(),
								"geocoder not present", Toast.LENGTH_SHORT).show();
					}

// } else {
// Toast.makeText(getApplicationContext(),
// "address not available", Toast.LENGTH_SHORT).show();
// }
				} catch (IOException e) {
// TODO Auto-generated catch block

					Log.e("tag", e.getMessage());
				}*/

//                progress.setVisibility(View.GONE);
				/*if (speedinmph>1){

	try {
//					for (int i = 0; i < 10; i++) {
//						Thread.sleep(1000);

		Toast.makeText(getActivity(), "Now your Call has been blocked", Toast.LENGTH_LONG).show();
		ComponentName receiver = new ComponentName(getActivity(), PhoneCallReceiver.class);
		PackageManager pm = getActivity().getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);




	}
	catch (Exception e) {

	}

}*/
                /*ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //call function
                        ha.postDelayed(this, 10000);
                        if (speed1 == true) {
                            if (speedinmph > 10) {

                                try {
//					for (int i = 0; i < 10; i++) {
//						Thread.sleep(1000);

                                    Toast.makeText(getActivity(), "Now your Call has been blocked", Toast.LENGTH_LONG).show();
                                    ComponentName receiver = new ComponentName(getActivity(), PhoneCallReceiver.class);
                                    PackageManager pm = getActivity().getPackageManager();

                                    pm.setComponentEnabledSetting(receiver,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                                    ComponentName smsreceiversms3 = new ComponentName(getActivity(), SMSReceiver.class);
                                    PackageManager smspm3 = getActivity().getPackageManager();

                                    smspm3.setComponentEnabledSetting(smsreceiversms3,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


                                } catch (Exception e) {

                                }

                            }
                        } else if (speed2 == true) {
                            if (speedinmph > 15) {

                                try {
//					for (int i = 0; i < 10; i++) {
//						Thread.sleep(1000);

                                    Toast.makeText(getActivity(), "Now your Call has been blocked15", Toast.LENGTH_LONG).show();
                                    ComponentName receiver = new ComponentName(getActivity(), PhoneCallReceiver.class);
                                    PackageManager pm = getActivity().getPackageManager();

                                    pm.setComponentEnabledSetting(receiver,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                                    ComponentName smsreceiversms3 = new ComponentName(getActivity(), SMSReceiver.class);
                                    PackageManager smspm3 = getActivity().getPackageManager();

                                    smspm3.setComponentEnabledSetting(smsreceiversms3,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


                                } catch (Exception e) {

                                }

                            }
                        } else if (speed3 == true) {
                            if (speedinmph > 20) {

                                try {
//									Toast.makeText(getActivity(), "Now your Call has been blocked20", Toast.LENGTH_LONG).show();
                                    ComponentName receiver = new ComponentName(getActivity(), PhoneCallReceiver.class);
                                    PackageManager pm = getActivity().getPackageManager();

                                    pm.setComponentEnabledSetting(receiver,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                                    ComponentName smsreceiversms3 = new ComponentName(getActivity(), SMSReceiver.class);
                                    PackageManager smspm3 = getActivity().getPackageManager();

                                    smspm3.setComponentEnabledSetting(smsreceiversms3,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


                                } catch (Exception e) {

                                }

                            }
                        } else if (speed4 == true) {
                            if (speedinmph > 30) {

                                try {
//					for (int i = 0; i < 10; i++) {
//						Thread.sleep(1000);

                                    Toast.makeText(getActivity(), "Now your Call has been blocked30", Toast.LENGTH_LONG).show();
                                    ComponentName receiver = new ComponentName(getActivity(), PhoneCallReceiver.class);
                                    PackageManager pm = getActivity().getPackageManager();

                                    ComponentName smsreceiversms3 = new ComponentName(getActivity(), SMSReceiver.class);
                                    PackageManager smspm3 = getActivity().getPackageManager();

                                    smspm3.setComponentEnabledSetting(smsreceiversms3,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                                    smspm3.setComponentEnabledSetting(smsreceiversms3,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                                } catch (Exception e) {

                                }

                            }
                        } else if (speed5 == true) {
                            if (speedinmph > speedActivity.Actualvalue) {
                                try {
                                    Toast.makeText(getActivity(), "Now your Call has been blocked40", Toast.LENGTH_LONG).show();
                                    ComponentName receiver = new ComponentName(getActivity(), PhoneCallReceiver.class);
                                    PackageManager pm = getActivity().getPackageManager();

                                    pm.setComponentEnabledSetting(receiver,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                                    ComponentName smsreceiversms3 = new ComponentName(getActivity(), SMSReceiver.class);
                                    PackageManager smspm3 = getActivity().getPackageManager();

                                    smspm3.setComponentEnabledSetting(smsreceiversms3,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


                                } catch (Exception e) {

                                }
                            }
                        }


                    }
                }, 10000);
*/
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }

    private void addAGps(){
        String datetime=System.currentTimeMillis()+"";
        Log.d("datetime",datetime);
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String date_time=sdf.format(d);
        Log.d("datetime","date:time:-"+date_time);
        String api = "http://mygpstracker.co.in/api/api_loc.php?"+"imei="+dev_id+"&dt="+date_time+"&lat="+latitude+"&lng="+longitude+"&altitude="+altitiude+"&angle="+degree+"&speed="+speed+"&loc_valid="+gpsenable+"&param=batp=100"+"|"+"acc="+accuracy+"|";
//        final String data="{\"email\":\""+email+"\",\"password\":\""+password+"\"}";
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
//                            JSONObject jsonObject=new JSONObject(response);
//                            String status=jsonObject.optString("status");
//                            String Usersname=jsonObject.getString("message");
//                            if (Usersname.startsWith("Welcome")){
//                                JSONObject jsonObject1=jsonObject.getJSONObject("data");
//                                String token =jsonObject1.getString("_token");
//                                String uid=jsonObject1.getString("unique_id");
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(),"Login ID or password is incorrect",Toast.LENGTH_LONG).show();
//                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
//                objresponse.put("request", "Login");
////                objresponse.put("data",data);
//                objresponse.put("_token", "");
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void StartForground() {
//        LocationChangeDetector locationChangeDetector = new LocationChangeDetector(context);
//        locationChangeDetector.getLatAndLong();
        Notification notification = new NotificationCompat.Builder(this)
                .setOngoing(false)
                .setSmallIcon(android.R.color.transparent)

                //.setSmallIcon(R.drawable.picture)
                .build();
        startForeground(101,  notification);

    }

}
            /*Geocoder geocoder;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Log.d("shubham",locationAddress);
//            tvAddress.setText(locationAddress);
        }
    }


    /*public void LocationUpdateAPI(final String finalAddress, final double longitude){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EmployeeTracking.LOCATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sunil", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Postdat", "" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loc_latitude", finalAddress);
                params.put("loc_langitude", longitude+"");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                params.put("loc_date", formattedDate);
                params.put("loc_user_id", Pref.GetStringPref(getApplicationContext(), Pref.USER_ID, ""));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/