package cepa.lote.lote;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.herprogramacion.trickmarket.R;

import cepa.lote.lote.modelo.sms;
import cepa.lote.lote.tools.Constantes;
import cepa.lote.lote.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class InFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String TAG = "LogsAndroid";
    private static final  String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView lista;
    private SmsAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Gson gson = new Gson();

    private SwipeRefreshLayout refreshLayout;

    // TODO: Rename and change types of parameters

    public InFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InFragment newInstance(int sectionNumber) {
        InFragment fragment = new InFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_in, container, false);
        lista = (RecyclerView) v.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,true);


        lista.setLayoutManager(lManager);

        cargarAdaptador();

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );

    return  v;

    }


    public void cargarAdaptador() {
        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_DATA,
                                (String)null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
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

    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("data");
                    // Parsear con Gson
                    sms[] smses = gson.fromJson(mensaje.toString(), sms[].class);


                    // Inicializar adaptador
                    adapter = new SmsAdapter(Arrays.asList(smses), getActivity());
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }


    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<sms>> {

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List<sms> doInBackground(Void... params) {
            // Simulación de la carga de items

            HashSet<sms> items = new HashSet<sms>();
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptado
            return null;
        }

        @Override
        protected void onPostExecute(List<sms> result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos


            // Añadir elementos nuevos
            cargarAdaptador();

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }



    // TODO: Rename method, update argument and hook method into UI event



}
