package cepa.lote.lote;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import cepa.lote.lote.modelo.sms;
import cepa.lote.lote.tools.Constantes;
import cepa.lote.lote.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.herprogramacion.trickmarket.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    private Gson gson = new Gson();
    private static final String TAG = "LogsAndroid";

    public static final String SMS_SENT = "SMS_SENT";
    public static final String SMS_DELIVERED = "SMS_DELIVERED";
    private final BroadcastReceiver envioSMSBR = new EnvioSMSBroadcastReceiver();
    private final BroadcastReceiver recepcionSMSBR = new RecepcionSMSBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    setToolbar(); // Añadir la toolbar

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // MenuItem item = menu.findItem(R.id.action_shop);

        // Obtener drawable del item
       // LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Actualizar el contador
       // Utils.setBadgeCount(this, icon, 3);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /* bif (id == R.id.action_shop) {
            showSnackBar("Carrito de compras");
            return true;
        };*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * Muestra una {@link Snackbar} prefabricada
     *
     * @param msg Mensaje a proyectar
     */
    private void showSnackBar(String msg) {
        Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * Crea una instancia del view pager con los datos
     * predeterminados
     *
     * @param viewPager Nueva instancia
     */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(InFragment.newInstance(1), getString(R.string.title_section1));
        adapter.addFragment(OutFragment.newInstance(2), getString(R.string.title_section2));

        viewPager.setAdapter(adapter);
    }

    /**
     * Método onClick() del FAB
     *
     * @param v View presionado
     */
    public void onFabClick(View v) {

        consultarData();

    }


    /**
     * Un {@link FragmentPagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }




    private void EnviarMensaje (String Numero, String Mensaje, int var){
        try {


            PendingIntent piEnvio= PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT),0);
            PendingIntent piRecepcion = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
            SmsManager sms = SmsManager.getDefault();

            sms.sendTextMessage(Numero,null,Mensaje,piEnvio,piRecepcion);

            registerReceiver(envioSMSBR, new IntentFilter(SMS_SENT));
            registerReceiver(recepcionSMSBR, new IntentFilter(SMS_DELIVERED));
          //  Toast.makeText(getApplicationContext(), Integer.toString(var), Toast.LENGTH_SHORT).show();
        }

        catch (Exception e) {
           // Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void consultarData() {
        // Petición GET
        VolleySingleton.
                getInstance(getApplicationContext ()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_DATA,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarEnvio(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }

                        )
                );
    }


   public void esperarY(Runnable r, int tiempo) {

        Handler handler = new Handler();

        handler.postDelayed(r,tiempo*1000);
    }


    public void actualizarPas( String id){
        HashMap<String, String> map = new HashMap<>();

        // Mapeo previo
        map.put("rep_sms_id", id);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getApplicationContext ()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.UPDATE_PAS,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                              //  procesarRespuestaActualizar(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );
    }


    private void procesarEnvio(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("data");
                    // Parsear con Gson



                    final sms[] smses = gson.fromJson(mensaje.toString(), sms[].class);
                    int size= smses.length;
                    int i=0;

                    while( i < size){

                       final String ide=smses[i].getRep_sms_id();

                        esperarY((new Runnable() {
                            public void run() {
                                // acciones que se ejecutan tras los milisegundos
                                actualizarPas(ide);
                            }
                        }), 1);

                        EnviarMensaje(smses[i].getRep_sms_gsm(),smses[i].getRep_sms_det(),i);

                        i++;
                    }


                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getApplicationContext (),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }

}