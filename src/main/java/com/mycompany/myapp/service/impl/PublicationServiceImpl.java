package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Publication;
import com.mycompany.myapp.repository.PublicationRepository;
import com.mycompany.myapp.service.PublicationService;
import com.mycompany.myapp.service.dto.PublicationDTO;
import com.mycompany.myapp.service.mapper.PublicationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Publication}.
 */
@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final Logger log = LoggerFactory.getLogger(PublicationServiceImpl.class);

    private final PublicationRepository publicationRepository;

    private final PublicationMapper publicationMapper;

    public PublicationServiceImpl(PublicationRepository publicationRepository, PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    @Override
    public PublicationDTO save(PublicationDTO publicationDTO) {
        log.debug("Request to save Publication : {}", publicationDTO);
        Publication publication = publicationMapper.toEntity(publicationDTO);
        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    @Override
    public PublicationDTO update(PublicationDTO publicationDTO) {
        log.debug("Request to update Publication : {}", publicationDTO);
        Publication publication = publicationMapper.toEntity(publicationDTO);
        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    @Override
    public Optional<PublicationDTO> partialUpdate(PublicationDTO publicationDTO) {
        log.debug("Request to partially update Publication : {}", publicationDTO);

        return publicationRepository
            .findById(publicationDTO.getId())
            .map(existingPublication -> {
                publicationMapper.partialUpdate(existingPublication, publicationDTO);

                return existingPublication;
            })
            .map(publicationRepository::save)
            .map(publicationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Publications");
        return publicationRepository.findAll(pageable).map(publicationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicationDTO> findOne(Long id) {
        log.debug("Request to get Publication : {}", id);
        return publicationRepository.findById(id).map(publicationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Publication : {}", id);
        publicationRepository.deleteById(id);
    }
}
