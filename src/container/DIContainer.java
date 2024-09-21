package container;

import main.java.repository.impl.*;
import main.java.service.*;
import main.java.ui.*;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void register(Class<T> type, T instance) {
        instances.put(type, instance);
    }

    public <T> T get(Class<T> type) {
        return type.cast(instances.get(type));
    }


    public static DIContainer initialize() {
        DIContainer container = new DIContainer();

        container.register(ClientRepository.class, new ClientRepository());
        container.register(DevisRepository.class, new DevisRepository());
        container.register(ComponentRepository.class, new ComponentRepository());
        container.register(ProjectRepository.class, new ProjectRepository());
        container.register(DevisService.class, new DevisService(container.get(DevisRepository.class)));
        container.register(ProjectService.class, new ProjectService(container.get(ProjectRepository.class)));
        container.register(ClientService.class, new ClientService(container.get(ClientRepository.class)));
        container.register(ComponentService.class, new ComponentService(container.get(ComponentRepository.class)));

        container.register(WorkForceRepository.class, new WorkForceRepository(container.get(ComponentRepository.class)));
        container.register(MaterialRepository.class, new MaterialRepository(container.get(ComponentRepository.class)));
        container.register(MaterialService.class, new MaterialService(container.get(MaterialRepository.class), container.get(ComponentRepository.class)));
        container.register(WorkForceService.class, new WorkForceService(container.get(WorkForceRepository.class), container.get(ComponentRepository.class)));

        container.register(DevisMenu.class, new DevisMenu(container.get(DevisService.class), container.get(ProjectService.class)));
        container.register(ClientMenu.class, new ClientMenu(container.get(ClientService.class)));
        container.register(MaterialMenu.class, new MaterialMenu(container.get(MaterialService.class), container.get(ComponentService.class)));
        container.register(WorkForceMenu.class, new WorkForceMenu(container.get(WorkForceService.class), container.get(ComponentService.class)));
        container.register(ComponentMenu.class , new ComponentMenu(
                container.get(MaterialMenu.class),
                container.get(WorkForceMenu.class)
        ));

        container.register(ProjectMenu.class, new ProjectMenu(
                container.get(ProjectService.class),
                container.get(ClientMenu.class),
                container.get(MaterialMenu.class),
                container.get(WorkForceMenu.class)
        ));
        container.register(CostCalculationMenu.class, new CostCalculationMenu(
                container.get(ProjectRepository.class),
                container.get(ComponentRepository.class),
                container.get(MaterialService.class),
                container.get(WorkForceService.class),
                container.get(DevisService.class),
                container.get(DevisMenu.class)
        ));
        container.register(PrincipalMenu.class, new PrincipalMenu(
                container.get(ProjectMenu.class),
                container.get(DevisMenu.class),
                container.get(ClientMenu.class),
                container.get(CostCalculationMenu.class),
                container.get(ComponentMenu.class)
        ));

        return container;
    }
}