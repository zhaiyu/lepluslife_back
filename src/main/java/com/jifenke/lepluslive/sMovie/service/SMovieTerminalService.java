package com.jifenke.lepluslive.sMovie.service;

import com.jifenke.lepluslive.sMovie.domain.entities.SMovieTerminal;
import com.jifenke.lepluslive.sMovie.repository.SMovieTerminalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lss on 17-5-3.
 */
@Service
@Transactional(readOnly = true)
public class SMovieTerminalService {
    @Inject
    private SMovieTerminalRepository sMovieTerminalRepository;

    public List<SMovieTerminal> findAll() {
        return sMovieTerminalRepository.findAll();
    }
}
