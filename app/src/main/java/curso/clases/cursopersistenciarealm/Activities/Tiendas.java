package curso.clases.cursopersistenciarealm.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import curso.clases.cursopersistenciarealm.Adapters.RVAdapterTiendas;
import curso.clases.cursopersistenciarealm.Dialogs.ProductDialog;
import curso.clases.cursopersistenciarealm.Dialogs.TiendaDialog;
import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Interface.IDialogTiendaListener;
import curso.clases.cursopersistenciarealm.Interface.ItemClickListener;
import curso.clases.cursopersistenciarealm.Models.Producto;
import curso.clases.cursopersistenciarealm.R;
import io.realm.Realm;

public class Tiendas extends AppCompatActivity implements View.OnClickListener, IDialogTiendaListener {
    FloatingActionButton fabAdd;
    Boolean isAllFabsVisible;
    Realm realm;

    ItemClickListener itemClickListener;
    RVAdapterTiendas rvAdapterTiendas;
    private LayoutInflater mInflater;

    List<curso.clases.cursopersistenciarealm.Models.Tiendas> lTiendas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendas);
        realm = Realm.getDefaultInstance();
        isAllFabsVisible = false;
        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(this);
        mInflater = LayoutInflater.from(this);
        this.lTiendas = GetTiendas();
        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(curso.clases.cursopersistenciarealm.Models.Tiendas tienda, int position, View view) {

            }
        };

        GetTiendas();

        rvAdapterTiendas = new RVAdapterTiendas(this.lTiendas,mInflater,this,itemClickListener);
        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));

        recyclerView.setAdapter(rvAdapterTiendas);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                rvAdapterTiendas.eliminarTienda(item.getGroupId());
                Toast.makeText(Tiendas.this,"Eliminado",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                rvAdapterTiendas.editarTienda(item.getGroupId());
                Toast.makeText(Tiendas.this,"Editar",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private List<curso.clases.cursopersistenciarealm.Models.Tiendas> GetTiendas() {
        List<curso.clases.cursopersistenciarealm.Models.Tiendas> lTiendas = realm.where(curso.clases.cursopersistenciarealm.Models.Tiendas.class).findAll();
        return lTiendas;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAdd:
                AbrirDialogBox();
                break;
        }
    }

    private void AbrirDialogBox() {
        TiendaDialog tiendaDialog = new TiendaDialog();
        tiendaDialog.show(getSupportFragmentManager(),"Añadir Tienda");
    }


    @Override
    public void GuardarTienda(String nombre, String direccion, Uri uriImage) {

        String s = getRealPathFromURI(uriImage);

        curso.clases.cursopersistenciarealm.Models.Tiendas tienda = new curso.clases.cursopersistenciarealm.Models.Tiendas();
        tienda.setNombre(nombre);
        tienda.setDireccion(direccion);
        tienda.setImagen(s);

        Number lastId = realm.where(curso.clases.cursopersistenciarealm.Models.Tiendas.class).max("id");

        int nextId;

        if(lastId == null){
            nextId = 1;
        }
        else{
            nextId = lastId.intValue() + 1;
        }

        tienda.setId(nextId);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(tienda);
            }
        });
        rvAdapterTiendas.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rvAdapterTiendas.notifyDataSetChanged();
    }
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}