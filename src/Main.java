import main.java.repository.*;
import main.java.service.*;
import main.java.ui.*;

public class Main {
    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        DevisRepository devisRepository = new DevisRepository();
        DevisService devisService = new DevisService(devisRepository);

        ComponentRepository componentRepository = new ComponentRepository();
        WorkForceRepository workForceRepository = new WorkForceRepository(componentRepository);
        MaterialRepository materialRepository = new MaterialRepository(componentRepository);
        ProjectRepository projectRepository = new ProjectRepository(clientRepository,componentRepository,materialRepository,workForceRepository);
        ProjectService projectService = new ProjectService(projectRepository);
        DevisMenu devisMenu = new DevisMenu(devisService,projectService);
        ClientService clientService = new ClientService(clientRepository);
        ClientMenu clientMenu = new ClientMenu(clientService);
        MaterialService materialService = new MaterialService(materialRepository);
        ComponentService componentService = new ComponentService(componentRepository);
        MaterialMenu materialMenu = new MaterialMenu(materialService,componentService);
        WorkForceService workForceService = new WorkForceService(workForceRepository);
        WorkForceMenu workForceMenu = new WorkForceMenu(workForceService,componentService);
        ProjectMenu projectMenu = new ProjectMenu(projectService,clientMenu,materialMenu, workForceMenu);
        PrincipalMenu principalMenu = new PrincipalMenu(projectMenu,devisMenu,clientMenu);
        principalMenu.Menu();
    }
}
