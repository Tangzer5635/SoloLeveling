package start;

import models.facades.IModel;
import models.facades.ModelImpl;
import presenteur.Presenteur;
import views.facades.IView;
import views.facades.ViewJFrameImpl;

public class MainApp {
    public static void main(String[] args) {
        IModel model = new ModelImpl();
        IView view = new ViewJFrameImpl();
        Presenteur presenteur = new Presenteur(model, view);

        presenteur.start();
    }
}