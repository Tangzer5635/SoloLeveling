package start;

import models.facades.IModel;
import models.facades.ModelImpl;
import presenteur.Presenteur;
import views.facades.IView;
import views.facades.ViewConsoleImpl;

public class MainConsole {
    public static void main(String[] args) {
        IModel model = new ModelImpl();
        IView view = new ViewConsoleImpl();
        Presenteur presenteur = new Presenteur(model, view);

        presenteur.start();
    }
}
