package mk.ukim.finki.eventguide.fetchData;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.LocalType;
import mk.ukim.finki.eventguide.model.Role;
import mk.ukim.finki.eventguide.model.dto.CreateLocalDto;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import mk.ukim.finki.eventguide.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInit {
    private final LocalRepository localRepository;

    public DataInit(LocalRepository localRepository, RoleRepository roleRepository) {
        this.localRepository = localRepository;
        this.roleRepository = roleRepository;
    }

    private final RoleRepository roleRepository;

    @PostConstruct
    public void seedRoles() {
        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.saveAll(List.of(
                    new Role("USER"),
                    new Role("ADMIN")
            ));
        }
    }

    @PostConstruct
    public void init() {
        List<CreateLocalDto> localDtos = LoadDataFromCsv.loadObjectList(CreateLocalDto.class, "places.csv");
        List<CreateLocalDto> uniqueList = localDtos
                .stream()
                .distinct()
                .toList();

        for (CreateLocalDto dto : uniqueList) {
            LocalType localType;

            if (dto.PrimaryType.contains("bar")) {
                localType = LocalType.BAR;
            } else if (dto.PrimaryType.contains("restaurant")) {
                localType = LocalType.RESTAURANT;
            } else if (dto.PrimaryType.contains("cafe") || dto.PrimaryType.contains("coffee")) {
                localType = LocalType.CAFE;
            } else if (dto.PrimaryType.contains("night_club")) {
                localType = LocalType.NIGHT_CLUB;
            } else if (dto.PrimaryType.contains("pub")) {
                localType = LocalType.PUB;
            } else if (dto.PrimaryType.contains("hotel")) {
                localType = LocalType.RESTAURANT;
            } else {
                localType = LocalType.BAR;
            }

            localRepository.save(new Local(dto.Name, dto.Address, dto.OpeningHours.get(0), dto.PhoneNumber, dto.LocalWebsite, dto.Image, localType, null));
        }
    }
}
