package curso.clases.cursopersistenciarealm.Adapters;

import android.content.Context;
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

import curso.clases.cursopersistenciarealm.Interface.ItemClickListener;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListenerProducto;
import curso.clases.cursopersistenciarealm.Models.Producto;
import curso.clases.cursopersistenciarealm.Models.Tiendas;
import curso.clases.cursopersistenciarealm.R;

public class RVAdapterProducts extends RecyclerView.Adapter<RVAdapterProducts.ViewHolder>{
    private List<Producto> lProducto;
    private LayoutInflater mInflater;
    private Context context;
    private ItemClickListenerProducto itemClickListenerProducto;

    public RVAdapterProducts(List<Producto> lProducto, LayoutInflater mInflater, Context context, ItemClickListenerProducto itemClickListenerProducto, Tiendas tienda) {
        this.lProducto = lProducto;
        this.mInflater = mInflater;
        this.context = context;
        this.itemClickListenerProducto = itemClickListenerProducto;
    }

    @NonNull
    @Override
    public RVAdapterProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.recycler_products,parent,false);
        return new RVAdapterProducts.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterProducts.ViewHolder holder, int position) {
        holder.bindData(lProducto.get(position));
    }

    @Override
    public int getItemCount() {
        return lProducto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView imageView;
        TextView nombre,precio,cantidad;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            nombre = view.findViewById(R.id.textNombre);
            precio = view.findViewById(R.id.textPrecio);
            cantidad = view.findViewById(R.id.textCantidad);
            imageView = view.findViewById(R.id.imageView);

        }

        public void bindData(Producto producto) {
            nombre.setText(producto.getNombre());
            precio.setText(""+producto.getPrecio());
            cantidad.setText(""+producto.getCantidad());

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Selecciona una opcion");
            contextMenu.add(this.getAdapterPosition(),1,0,"Eliminar Producto");
            contextMenu.add(this.getAdapterPosition(),2,1,"Editar Producto");
        }


    }
}
