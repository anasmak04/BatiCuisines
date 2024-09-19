import container.DIContainer;
import main.java.ui.*;

public class Main {
    public static void main(String[] args) {
        DIContainer container = DIContainer.initialize();
        PrincipalMenu principalMenu = container.get(PrincipalMenu.class);
        principalMenu.Menu();
    }
}
