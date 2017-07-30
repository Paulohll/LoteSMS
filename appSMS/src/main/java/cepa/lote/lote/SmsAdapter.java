package cepa.lote.lote;

/**
 * Created by Paulo on 26/07/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herprogramacion.trickmarket.R;

import cepa.lote.lote.modelo.sms;

import java.util.List;


/**
 * Adaptador del recycler view
 */
public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder>
        implements ItemClickListener {

    /**
     * Lista de objetos {@link sms} que representan la fuente de datos
     * de inflado
     */
    private List<sms> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;


    public SmsAdapter(List<sms> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    /*
    Permite limpiar todos los elementos del recycler
     */

    @Override
    public SmsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new SmsViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(SmsViewHolder viewHolder, int i) {
        viewHolder.gsm.setText(items.get(i).getRep_sms_gsm());
        viewHolder.sms.setText(items.get(i).getRep_sms_det());
        viewHolder.estado.setText(items.get(i).getRep_sms_est());
        //  viewHolder.categoria.setText(items.get(i).getCategoria());
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     */

     @Override
    public void onItemClick(View view, int position) {
       /* DetailActivity.launch(
                (Activity) context, items.get(position).getIdMeta());*/
    }


    public static class SmsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView sms;
        public TextView estado;
        public TextView gsm;

        public TextView prioridad;
        public ItemClickListener listener;

        public SmsViewHolder(View v, ItemClickListener listener) {
            super(v);
            sms = (TextView) v.findViewById(R.id.sms);
            prioridad = (TextView) v.findViewById(R.id.prioridad);
            gsm = (TextView) v.findViewById(R.id.gsm);
            estado = (TextView) v.findViewById(R.id.estado);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}


interface ItemClickListener {
    void onItemClick(View view, int position);
}
