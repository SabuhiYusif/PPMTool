package io.agileintellegence.pomtool.services;

import io.agileintellegence.pomtool.domain.Project;
import io.agileintellegence.pomtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        // TODO logic

        return projectRepository.save(project);
    }
}
