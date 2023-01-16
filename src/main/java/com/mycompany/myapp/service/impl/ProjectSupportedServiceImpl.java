package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ProjectSupported;
import com.mycompany.myapp.repository.ProjectSupportedRepository;
import com.mycompany.myapp.service.ProjectSupportedService;
import com.mycompany.myapp.service.dto.ProjectSupportedDTO;
import com.mycompany.myapp.service.mapper.ProjectSupportedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectSupported}.
 */
@Service
@Transactional
public class ProjectSupportedServiceImpl implements ProjectSupportedService {

    private final Logger log = LoggerFactory.getLogger(ProjectSupportedServiceImpl.class);

    private final ProjectSupportedRepository projectSupportedRepository;

    private final ProjectSupportedMapper projectSupportedMapper;

    public ProjectSupportedServiceImpl(
        ProjectSupportedRepository projectSupportedRepository,
        ProjectSupportedMapper projectSupportedMapper
    ) {
        this.projectSupportedRepository = projectSupportedRepository;
        this.projectSupportedMapper = projectSupportedMapper;
    }

    @Override
    public ProjectSupportedDTO save(ProjectSupportedDTO projectSupportedDTO) {
        log.debug("Request to save ProjectSupported : {}", projectSupportedDTO);
        ProjectSupported projectSupported = projectSupportedMapper.toEntity(projectSupportedDTO);
        projectSupported = projectSupportedRepository.save(projectSupported);
        return projectSupportedMapper.toDto(projectSupported);
    }

    @Override
    public ProjectSupportedDTO update(ProjectSupportedDTO projectSupportedDTO) {
        log.debug("Request to update ProjectSupported : {}", projectSupportedDTO);
        ProjectSupported projectSupported = projectSupportedMapper.toEntity(projectSupportedDTO);
        projectSupported = projectSupportedRepository.save(projectSupported);
        return projectSupportedMapper.toDto(projectSupported);
    }

    @Override
    public Optional<ProjectSupportedDTO> partialUpdate(ProjectSupportedDTO projectSupportedDTO) {
        log.debug("Request to partially update ProjectSupported : {}", projectSupportedDTO);

        return projectSupportedRepository
            .findById(projectSupportedDTO.getId())
            .map(existingProjectSupported -> {
                projectSupportedMapper.partialUpdate(existingProjectSupported, projectSupportedDTO);

                return existingProjectSupported;
            })
            .map(projectSupportedRepository::save)
            .map(projectSupportedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectSupportedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectSupporteds");
        return projectSupportedRepository.findAll(pageable).map(projectSupportedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectSupportedDTO> findOne(Long id) {
        log.debug("Request to get ProjectSupported : {}", id);
        return projectSupportedRepository.findById(id).map(projectSupportedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectSupported : {}", id);
        projectSupportedRepository.deleteById(id);
    }
}
