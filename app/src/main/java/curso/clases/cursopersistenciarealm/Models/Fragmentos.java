package curso.clases.cursopersistenciarealm.Models;

import curso.clases.cursopersistenciarealm.Fragmentos.Login;
import curso.clases.cursopersistenciarealm.Fragmentos.Registro;
import curso.clases.cursopersistenciarealm.Interface.IChangeFragments;

public class Fragmentos {
    Registro registro;
    Login login;
    IChangeFragments iChangeFragments;

    public Fragmentos() {
    }

    public Fragmentos(IChangeFragments iChangeFragments) {
        this.iChangeFragments = iChangeFragments;
    }
    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
