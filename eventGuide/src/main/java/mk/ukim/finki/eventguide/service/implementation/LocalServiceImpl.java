package mk.ukim.finki.eventguide.service.implementation;

import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocalServiceImpl implements LocalService {

    private final LocalRepository localRepository;

    public LocalServiceImpl(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public Optional<Local> findById(Long id) {
        return this.localRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.localRepository.deleteById(id);
    }
}
