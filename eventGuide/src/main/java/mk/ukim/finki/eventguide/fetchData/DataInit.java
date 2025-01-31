package mk.ukim.finki.eventguide.fetchData;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.LocalType;
import mk.ukim.finki.eventguide.model.dto.CreateLocalDto;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInit {
    private final LocalRepository localRepository;

    public DataInit(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }


    @PostConstruct
    public void init() {
        List<CreateLocalDto> localDtos = LoadDataFromCsv.loadObjectList(CreateLocalDto.class, "places.csv");
        for (CreateLocalDto dto : localDtos) {
            localRepository.save(new Local(dto.Name, dto.Address, dto.OpeningHours.get(0), 0, LocalType.BAR, null));
        }
    }
}
