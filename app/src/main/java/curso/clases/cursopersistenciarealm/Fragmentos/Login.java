package curso.clases.cursopersistenciarealm.Fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import curso.clases.cursopersistenciarealm.Activities.MainActivity;
import curso.clases.cursopersistenciarealm.Activities.Products;
import curso.clases.cursopersistenciarealm.Activities.Tiendas;
import curso.clases.cursopersistenciarealm.Interface.IChangeFragments;
import curso.clases.cursopersistenciarealm.Models.Usuario;
import curso.clases.cursopersistenciarealm.R;
import curso.clases.cursopersistenciarealm.Util.Utilities;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment implements View.OnClickListener{

    Button btnLog,btnRegistrarse;
    EditText editTextUsuario,editTextPassword;
    IChangeFragments iChangeFragments;
    Realm realm;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }
    public Login(IChangeFragments iChangeFragments) {
        // Required empty public constructor
        this.iChangeFragments = iChangeFragments;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        btnLog = view.findViewById(R.id.btnLog);
        btnRegistrarse = view.findViewById(R.id.btnRegistrarse);

        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextUsuario = view.findViewById(R.id.editTextUsuario);

        btnLog.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistrarse:
                iChangeFragments.SendFragment(Utilities.fragmentos.getRegistro());
                break;
            case R.id.btnLog:
                ValidarDatos();
                break;
        }
    }
    private void ValidarDatos() {
        String user = editTextUsuario.getText().toString();
        String pass = editTextPassword.getText().toString();
        if(!user.equals("") && user != null && !pass.equals("") && pass != null){
            ObtenerLogin(user,pass);
        }
        else{
            Toast.makeText(getContext(),"favor de llenar los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void ObtenerLogin(String user,String pass) {
        realm = Realm.getDefaultInstance();
        RealmQuery<Usuario> query = realm.where(Usuario.class);
        RealmResults<Usuario> list = query.equalTo("nick",user).and().equalTo("password",pass).findAll();
        /*for(int i = 0; i < list.size(); i++ ){
            System.out.println("id: "+list.get(i).getId()+" usuario: "+list.get(i).getNombre());
        }*/
        if(list.size() == 1){
            Intent intent = new Intent(getContext(), Tiendas.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(),"Credenciales erroneas",Toast.LENGTH_LONG).show();
        }
    }
}