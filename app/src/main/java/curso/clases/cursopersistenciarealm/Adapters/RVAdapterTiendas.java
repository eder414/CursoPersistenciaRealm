package curso.clases.cursopersistenciarealm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import curso.clases.cursopersistenciarealm.Activities.ModificarTienda;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListener;
import curso.clases.cursopersistenciarealm.Models.Producto;
import curso.clases.cursopersistenciarealm.Models.Tiendas;
import curso.clases.cursopersistenciarealm.R;
import io.realm.Realm;

public class RVAdapterTiendas extends RecyclerView.Adapter<RVAdapterTiendas.ViewHolder> {
    private List<Tiendas> lTiendas;
    private LayoutInflater mInflater;
    private Context context;
    private ItemClickListener itemClickListener;

    public RVAdapterTiendas(List<Tiendas> lTiendas, LayoutInflater mInflater, Context context, ItemClickListener itemClickListener) {
        this.lTiendas = lTiendas;
        this.mInflater = mInflater;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.recycler_tiendas,parent,false);
        return new RVAdapterTiendas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(lTiendas.get(position));
    }

    @Override
    public int getItemCount() {
        return lTiendas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView imageView;
        TextView nombre,direccion;
        LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            nombre = view.findViewById(R.id.textNombre);
            direccion = view.findViewById(R.id.textDireccion);
            linearLayout = view.findViewById(R.id.linearLayout);
            linearLayout.setOnCreateContextMenuListener(this);
            imageView = view.findViewById(R.id.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
        void bindData(final Tiendas tienda){
            System.out.println("nombre: "+tienda.getNombre());
            nombre.setText(tienda.getNombre());
            direccion.setText(tienda.getDireccion());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Selecciona una opcion");
            contextMenu.add(this.getAdapterPosition(),1,0,"eliminar Tienda");
            contextMenu.add(this.getAdapterPosition(),2,1,"Editar Tienda");
        }
    }
    public void eliminarTienda(int position){
        int tiendasId = lTiendas.get(position).getId();
        Realm realm = Realm.getDefaultInstance();
        final Tiendas tienda = realm.where(Tiendas.class).equalTo("id",tiendasId).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tienda.deleteFromRealm();
                notifyDataSetChanged();
            }
        });
    }
    public void editarTienda(int position){
        int tiendasId = lTiendas.get(position).getId();

        Realm realm = Realm.getDefaultInstance();
        final Tiendas tienda = realm.where(Tiendas.class).equalTo("id",tiendasId).findFirst();

        Intent intent = new Intent(context, ModificarTienda.class);
        intent.putExtra("tiendaId", tienda.getId());

        context.startActivity(intent);
    }
}
