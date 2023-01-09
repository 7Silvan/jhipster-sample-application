package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.repository.CellModelRepository;
import com.mycompany.myapp.service.CellModelService;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.mapper.CellModelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CellModel}.
 */
@Service
@Transactional
public class CellModelServiceImpl implements CellModelService {

    private final Logger log = LoggerFactory.getLogger(CellModelServiceImpl.class);

    private final CellModelRepository cellModelRepository;

    private final CellModelMapper cellModelMapper;

    public CellModelServiceImpl(CellModelRepository cellModelRepository, CellModelMapper cellModelMapper) {
        this.cellModelRepository = cellModelRepository;
        this.cellModelMapper = cellModelMapper;
    }

    @Override
    public CellModelDTO save(CellModelDTO cellModelDTO) {
        log.debug("Request to save CellModel : {}", cellModelDTO);
        CellModel cellModel = cellModelMapper.toEntity(cellModelDTO);
        cellModel = cellModelRepository.save(cellModel);
        return cellModelMapper.toDto(cellModel);
    }

    @Override
    public CellModelDTO update(CellModelDTO cellModelDTO) {
        log.debug("Request to update CellModel : {}", cellModelDTO);
        CellModel cellModel = cellModelMapper.toEntity(cellModelDTO);
        cellModel.setIsPersisted();
        cellModel = cellModelRepository.save(cellModel);
        return cellModelMapper.toDto(cellModel);
    }

    @Override
    public Optional<CellModelDTO> partialUpdate(CellModelDTO cellModelDTO) {
        log.debug("Request to partially update CellModel : {}", cellModelDTO);

        return cellModelRepository
            .findById(cellModelDTO.getId())
            .map(existingCellModel -> {
                cellModelMapper.partialUpdate(existingCellModel, cellModelDTO);

                return existingCellModel;
            })
            .map(cellModelRepository::save)
            .map(cellModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CellModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CellModels");
        return cellModelRepository.findAll(pageable).map(cellModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CellModelDTO> findOne(String id) {
        log.debug("Request to get CellModel : {}", id);
        return cellModelRepository.findById(id).map(cellModelMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete CellModel : {}", id);
        cellModelRepository.deleteById(id);
    }
}
