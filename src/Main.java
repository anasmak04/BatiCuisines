import container.DIContainer;
import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.repository.impl.ProjectRepository;
import main.java.ui.PrincipalMenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {



//    public static List<List<Component>> componentsByProject(){
//        List<Project> projects = new ProjectRepository().findAll();
//        return projects.stream()
//                .map(project -> project.getComponents())
//                .collect(Collectors.toList());
//    }


    public static void main(String[] args) {
        DIContainer container = DIContainer.initialize();
        PrincipalMenu principalMenu = container.get(PrincipalMenu.class);
        principalMenu.Menu();

    }
}
