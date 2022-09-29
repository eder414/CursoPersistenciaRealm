package curso.clases.cursopersistenciarealm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import curso.clases.cursopersistenciarealm.Fragmentos.Login;
import curso.clases.cursopersistenciarealm.Fragmentos.Registro;
import curso.clases.cursopersistenciarealm.Interface.IChangeFragments;
import curso.clases.cursopersistenciarealm.Models.Fragmentos;
import curso.clases.cursopersistenciarealm.R;
import curso.clases.cursopersistenciarealm.Util.Utilities;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity  {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    IChangeFragments iChangeFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_CursoPersistenciaRealm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iChangeFragments = new IChangeFragments() {
            @Override
            public <T> void SendFragment(T fragmento) {
                AbrirFragmento(fragmento);
            }
        };
        Utilities.fragmentos = new Fragmentos();

        Utilities.fragmentos.setRegistro(new Registro(iChangeFragments));
        Utilities.fragmentos.setLogin(new Login(iChangeFragments));

        AbrirFragmento(Utilities.fragmentos.getLogin());
    }


    private <T> void AbrirFragmento(T fragmento) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainActivityFragment, (Fragment) fragmento,null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

}