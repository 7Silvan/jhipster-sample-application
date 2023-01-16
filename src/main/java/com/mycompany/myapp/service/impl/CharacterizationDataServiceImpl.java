package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CharacterizationData;
import com.mycompany.myapp.repository.CharacterizationDataRepository;
import com.mycompany.myapp.service.CharacterizationDataService;
import com.mycompany.myapp.service.dto.CharacterizationDataDTO;
import com.mycompany.myapp.service.mapper.CharacterizationDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CharacterizationData}.
 */
@Service
@Transactional
public class CharacterizationDataServiceImpl implements CharacterizationDataService {

    private final Logger log = LoggerFactory.getLogger(CharacterizationDataServiceImpl.class);

    private final CharacterizationDataRepository characterizationDataRepository;

    private final CharacterizationDataMapper characterizationDataMapper;

    public CharacterizationDataServiceImpl(
        CharacterizationDataRepository characterizationDataRepository,
        CharacterizationDataMapper characterizationDataMapper
    ) {
        this.characterizationDataRepository = characterizationDataRepository;
        this.characterizationDataMapper = characterizationDataMapper;
    }

    @Override
    public CharacterizationDataDTO save(CharacterizationDataDTO characterizationDataDTO) {
        log.debug("Request to save CharacterizationData : {}", characterizationDataDTO);
        CharacterizationData characterizationData = characterizationDataMapper.toEntity(characterizationDataDTO);
        characterizationData = characterizationDataRepository.save(characterizationData);
        return characterizationDataMapper.toDto(characterizationData);
    }

    @Override
    public CharacterizationDataDTO update(CharacterizationDataDTO characterizationDataDTO) {
        log.debug("Request to update CharacterizationData : {}", characterizationDataDTO);
        CharacterizationData characterizationData = characterizationDataMapper.toEntity(characterizationDataDTO);
        characterizationData = characterizationDataRepository.save(characterizationData);
        return characterizationDataMapper.toDto(characterizationData);
    }

    @Override
    public Optional<CharacterizationDataDTO> partialUpdate(CharacterizationDataDTO characterizationDataDTO) {
        log.debug("Request to partially update CharacterizationData : {}", characterizationDataDTO);

        return characterizationDataRepository
            .findById(characterizationDataDTO.getId())
            .map(existingCharacterizationData -> {
                characterizationDataMapper.partialUpdate(existingCharacterizationData, characterizationDataDTO);

                return existingCharacterizationData;
            })
            .map(characterizationDataRepository::save)
            .map(characterizationDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CharacterizationDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CharacterizationData");
        return characterizationDataRepository.findAll(pageable).map(characterizationDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CharacterizationDataDTO> findOne(Long id) {
        log.debug("Request to get CharacterizationData : {}", id);
        return characterizationDataRepository.findById(id).map(characterizationDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CharacterizationData : {}", id);
        characterizationDataRepository.deleteById(id);
    }
}
