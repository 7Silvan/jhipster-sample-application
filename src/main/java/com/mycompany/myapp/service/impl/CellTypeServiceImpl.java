package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CellType;
import com.mycompany.myapp.repository.CellTypeRepository;
import com.mycompany.myapp.service.CellTypeService;
import com.mycompany.myapp.service.dto.CellTypeDTO;
import com.mycompany.myapp.service.mapper.CellTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CellType}.
 */
@Service
@Transactional
public class CellTypeServiceImpl implements CellTypeService {

    private final Logger log = LoggerFactory.getLogger(CellTypeServiceImpl.class);

    private final CellTypeRepository cellTypeRepository;

    private final CellTypeMapper cellTypeMapper;

    public CellTypeServiceImpl(CellTypeRepository cellTypeRepository, CellTypeMapper cellTypeMapper) {
        this.cellTypeRepository = cellTypeRepository;
        this.cellTypeMapper = cellTypeMapper;
    }

    @Override
    public CellTypeDTO save(CellTypeDTO cellTypeDTO) {
        log.debug("Request to save CellType : {}", cellTypeDTO);
        CellType cellType = cellTypeMapper.toEntity(cellTypeDTO);
        cellType = cellTypeRepository.save(cellType);
        return cellTypeMapper.toDto(cellType);
    }

    @Override
    public CellTypeDTO update(CellTypeDTO cellTypeDTO) {
        log.debug("Request to update CellType : {}", cellTypeDTO);
        CellType cellType = cellTypeMapper.toEntity(cellTypeDTO);
        cellType = cellTypeRepository.save(cellType);
        return cellTypeMapper.toDto(cellType);
    }

    @Override
    public Optional<CellTypeDTO> partialUpdate(CellTypeDTO cellTypeDTO) {
        log.debug("Request to partially update CellType : {}", cellTypeDTO);

        return cellTypeRepository
            .findById(cellTypeDTO.getId())
            .map(existingCellType -> {
                cellTypeMapper.partialUpdate(existingCellType, cellTypeDTO);

                return existingCellType;
            })
            .map(cellTypeRepository::save)
            .map(cellTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CellTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CellTypes");
        return cellTypeRepository.findAll(pageable).map(cellTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CellTypeDTO> findOne(Long id) {
        log.debug("Request to get CellType : {}", id);
        return cellTypeRepository.findById(id).map(cellTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CellType : {}", id);
        cellTypeRepository.deleteById(id);
    }
}
