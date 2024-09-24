import container.DIContainer;
import main.java.ui.PrincipalMenu;




public class Main {

//    public static List<List<Component>> componentsByProject(){
//        List<Project> projects = new ProjectRepository().findAll();
//        return projects.stream()
//                .map(Project::getComponents)
//                .collect(Collectors.toList());
//    }
//

    public static void main(String[] args) {
        DIContainer container = DIContainer.initialize();
        PrincipalMenu principalMenu = container.get(PrincipalMenu.class);
        principalMenu.Menu();
    }
}
