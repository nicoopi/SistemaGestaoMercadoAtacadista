import controller.ClienteFisicoController;
import view.ClienteFisicoView;

public class Main {
    public static void main(String[] args) {
        ClienteFisicoView view = new ClienteFisicoView();
        ClienteFisicoController controller = new ClienteFisicoController(view);
        view.setController(controller);
        view.exibirMenu();
    }
}
