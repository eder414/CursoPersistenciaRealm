package curso.clases.cursopersistenciarealm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import curso.clases.cursopersistenciarealm.Adapters.RVAdapterProducts;
import curso.clases.cursopersistenciarealm.Adapters.RVAdapterTiendas;
import curso.clases.cursopersistenciarealm.Dialogs.ProductDialog;
import curso.clases.cursopersistenciarealm.Dialogs.TiendaDialog;
import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Interface.IChangeDialog;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListener;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListenerProducto;
import curso.clases.cursopersistenciarealm.Models.Producto;
import curso.clases.cursopersistenciarealm.Models.Tiendas;
import curso.clases.cursopersistenciarealm.R;
import io.realm.Realm;
import io.realm.RealmList;

public class ModificarTienda extends AppCompatActivity implements View.OnClickListener, DialogListener {
    Button btnGuardar, btnCancelar;
    EditText nombre,direccion;
    FloatingActionButton fabAddProducto;
    Boolean isAllFabsVisible;
    Realm realm;

    ItemClickListenerProducto itemClickListenerProducto;
    IChangeDialog iChangeDialog;

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

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

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
        iChangeDialog = new IChangeDialog() {
            @Override
            public void ChangeDialog(AppCompatDialogFragment dialogFragment, Tiendas tienda,int productId) {
                dialogFragment.show(getSupportFragmentManager(),"Añadir Producto");
            }
        };
        rvAdapterProducts = new RVAdapterProducts(this.lProducto,mInflater,this,itemClickListenerProducto,tienda,iChangeDialog);
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
            case R.id.btnGuardar:
                GuardarTienda();
                break;
            case R.id.btnCancelar:
                AbrirDialogBox();
                break;
        }
    }

    private void GuardarTienda() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tienda.setNombre(nombre.getText().toString());
                tienda.setDireccion(direccion.getText().toString());
                realm.copyToRealmOrUpdate(tienda);
            }
        });
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
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                rvAdapterProducts.eliminarProducto(item.getGroupId(),tienda);
                Toast.makeText(curso.clases.cursopersistenciarealm.Activities.ModificarTienda.this,"Eliminado",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                rvAdapterProducts.editatProducto(item.getGroupId(),tienda,item.getItemId());
                Toast.makeText(curso.clases.cursopersistenciarealm.Activities.ModificarTienda.this,"Editar",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

}