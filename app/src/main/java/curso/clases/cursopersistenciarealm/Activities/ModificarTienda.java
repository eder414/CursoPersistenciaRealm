package curso.clases.cursopersistenciarealm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import curso.clases.cursopersistenciarealm.Adapters.RVAdapterProducts;
import curso.clases.cursopersistenciarealm.Adapters.RVAdapterTiendas;
import curso.clases.cursopersistenciarealm.Dialogs.ProductDialog;
import curso.clases.cursopersistenciarealm.Dialogs.TiendaDialog;
import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListener;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListenerProducto;
import curso.clases.cursopersistenciarealm.Models.Producto;
import curso.clases.cursopersistenciarealm.Models.Tiendas;
import curso.clases.cursopersistenciarealm.R;
import io.realm.Realm;
import io.realm.RealmList;

public class ModificarTienda extends AppCompatActivity implements View.OnClickListener, DialogListener {

    EditText nombre,direccion;
    FloatingActionButton fabAddProducto;
    Boolean isAllFabsVisible;
    Realm realm;

    ItemClickListenerProducto itemClickListenerProducto;
    RVAdapterProducts rvAdapterProducts;
    private LayoutInflater mInflater;
    curso.clases.cursopersistenciarealm.Models.Tiendas tienda;
    List<Producto> lProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_tienda);
        Intent intent = getIntent();
        int tiendasId = intent.getIntExtra("tiendaId",0);

        realm = Realm.getDefaultInstance();
        tienda = realm.where(Tiendas.class).equalTo("id",tiendasId).findFirst();


        nombre = findViewById(R.id.editTextNombreTienda);
        direccion = findViewById(R.id.editTextDireccion);

        nombre.setText(tienda.getNombre());
        direccion.setText(tienda.getDireccion());

        isAllFabsVisible = false;
        fabAddProducto = findViewById(R.id.fabAddProducto);

        fabAddProducto.setOnClickListener(this);
        mInflater = LayoutInflater.from(this);
        this.lProducto = tienda.getProductos();
        itemClickListenerProducto = new ItemClickListenerProducto() {
            @Override
            public void onItemClick(Tiendas tienda, int position, View view) {

            }
        };
        rvAdapterProducts = new RVAdapterProducts(this.lProducto,mInflater,this,itemClickListenerProducto,tienda);
        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));

        recyclerView.setAdapter(rvAdapterProducts);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddProducto:
                AbrirDialogBox();
                break;
        }
    }

    private void AbrirDialogBox() {
        ProductDialog productDialog = new ProductDialog();
        productDialog.show(getSupportFragmentManager(),"Añadir Producto");
    }

    @Override
    public void GuardarProducto(String editTextNombre, String editTextPrecio, String editTextCantidad) {
        Producto producto = new Producto();

        producto.setNombre(editTextNombre);
        producto.setPrecio(Float.parseFloat(editTextPrecio));
        producto.setCantidad(Integer.parseInt(editTextCantidad));


        Number lastId = realm.where(Producto.class).max("id");

        int nextId;

        if(lastId == null){
            nextId = 1;
        }
        else{
            nextId = lastId.intValue() + 1;
        }

        producto.setId(nextId);



        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                lProducto.add(producto);
                tienda.setProductos((RealmList<Producto>) lProducto);
                realm.copyToRealmOrUpdate(tienda);
            }
        });
        rvAdapterProducts.notifyDataSetChanged();
    }
}