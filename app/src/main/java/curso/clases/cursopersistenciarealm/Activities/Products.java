package curso.clases.cursopersistenciarealm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import curso.clases.cursopersistenciarealm.Dialogs.ProductDialog;
import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Models.Producto;
import curso.clases.cursopersistenciarealm.Models.Usuario;
import curso.clases.cursopersistenciarealm.R;
import io.realm.Realm;

public class Products extends AppCompatActivity implements View.OnClickListener, DialogListener {
    FloatingActionButton fabAdd;
    Boolean isAllFabsVisible;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        isAllFabsVisible = false;
        fabAdd = findViewById(R.id.fabAdd);

        /*fabAdd.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"Añade Producto",Toast.LENGTH_LONG).show();
        });*/
        fabAdd.setOnClickListener(this);

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
        ProductDialog productDialog = new ProductDialog();
        productDialog.show(getSupportFragmentManager(),"Añadir Producto");
    }

    @Override
    public void GuardarProducto(String editTextNombre, String editTextPrecio, String editTextCantidad) {
        //Toast.makeText(getApplicationContext(),"precio: "+editTextPrecio,Toast.LENGTH_LONG).show();
        realm = Realm.getDefaultInstance();

        Producto producto = new Producto();
        producto.setNombre(editTextNombre);
        producto.setCantidad(Integer.parseInt(editTextCantidad));
        producto.setPrecio(Float.parseFloat(editTextPrecio));

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
                realm.copyToRealm(producto);
            }
        });
    }
}