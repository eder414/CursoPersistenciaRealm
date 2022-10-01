package curso.clases.cursopersistenciarealm.Dialogs;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import curso.clases.cursopersistenciarealm.Interface.DialogListener;
import curso.clases.cursopersistenciarealm.Interface.IDialogTiendaListener;
import curso.clases.cursopersistenciarealm.R;

public class TiendaDialog extends AppCompatDialogFragment implements View.OnClickListener{
    EditText editTextNombreTienda,editTextDireccion;
    private IDialogTiendaListener iDialogTiendaListener;
    Button btnImageTienda;
    ImageView imageTienda;

    private static final int IMAG_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int RESUL_OK = -1;

    Uri uriImage;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_tienda_dialog,null);
        editTextNombreTienda = view.findViewById(R.id.editTextNombreTienda);
        editTextDireccion = view.findViewById(R.id.editTextDireccion);

        btnImageTienda = view.findViewById(R.id.btnImageTienda);
        btnImageTienda.setOnClickListener(this);

        imageTienda = view.findViewById(R.id.imageTienda);

        builder.setView(view).setTitle("Añadir Tienda")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombre = editTextNombreTienda.getText().toString();
                        String direccion = editTextDireccion.getText().toString();

                        iDialogTiendaListener.GuardarTienda(nombre,direccion,uriImage);
                    }
                });

        return builder.create();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iDialogTiendaListener =(IDialogTiendaListener)  context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString() + "must implement IDialogTiendaListener");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        //window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (width * 0.85), (int) (height * 0.50));

        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View view) {
        pickPhotoFromGallery();
    }


    private void pickPhotoFromGallery() {
        //check permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //permiso denegado
                String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show ppopup for runtime permission
                requestPermissions(permissions,PERMISSION_CODE);
            }
            else{
                pickImageFromGallery();
            }
        }
        else{
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAG_PICK_CODE);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    });

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESUL_OK && requestCode == IMAG_PICK_CODE){
            // set image to imageview
            imageTienda.setImageURI(data.getData());
            this.uriImage = data.getData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(getContext(),"Permiso denegado...",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
