package curso.clases.cursopersistenciarealm.Fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import curso.clases.cursopersistenciarealm.Interface.IChangeFragments;
import curso.clases.cursopersistenciarealm.Models.Usuario;
import curso.clases.cursopersistenciarealm.R;
import curso.clases.cursopersistenciarealm.Util.Utilities;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment implements View.OnClickListener{
    Button btnRegistro,btnCancelar,btnSearchImage;
    EditText editTextNombre,editTextNick,editTextPassword;
    IChangeFragments iChangeFragments;
    Realm realm;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Registro() {
        // Required empty public constructor
    }
    public Registro(IChangeFragments iChangeFragments) {
        // Required empty public constructor
        this.iChangeFragments = iChangeFragments;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro newInstance(String param1, String param2) {
        Registro fragment = new Registro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnRegistro = view.findViewById(R.id.btnRegistro);

        btnCancelar.setOnClickListener(this);
        btnRegistro.setOnClickListener(this);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextNick = view.findViewById(R.id.editTextNick);
        editTextPassword  = view.findViewById(R.id.editTextPassword);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCancelar:
                iChangeFragments.SendFragment(Utilities.fragmentos.getLogin());
                break;
            case R.id.btnRegistro:
                ValidarDatos();
                break;
        }
    }

    private void ValidarDatos() {
        String nombre = editTextNombre.getText().toString();
        String nick = editTextNick.getText().toString();
        String password = editTextPassword.getText().toString();
        if(nombre != null && !nombre.equals("") && nick != null && !nick.equals("") && password != null && !password.equals("")){
            SaveUser(nombre,nick,password);
        }
    }

    private void SaveUser(String nombre,String nick,String password) {
        realm = Realm.getDefaultInstance();
        Usuario usuario = new Usuario();
        usuario.setNick(nick);
        usuario.setNombre(nombre);
        usuario.setPassword(password);

        Number lastId = realm.where(Usuario.class).max("id");

        int nextId;

        if(lastId == null){
            nextId = 1;
        }
        else{
            nextId = lastId.intValue() + 1;
        }

        usuario.setId(nextId);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(usuario);
                ShowData();
            }
        });
    }
    private void ShowData(){
        /*List<Usuario> lUsuarios = realm.where(Usuario.class).findAll();
        for(int i = 0; i < lUsuarios.size(); i++){
            System.out.println("Usuario: "+lUsuarios.get(i).getNombre()+" con id: "+lUsuarios.get(i).getId());
        }*/
        Toast.makeText(getContext(),"Usuario Guardado",Toast.LENGTH_LONG).show();
    }
}