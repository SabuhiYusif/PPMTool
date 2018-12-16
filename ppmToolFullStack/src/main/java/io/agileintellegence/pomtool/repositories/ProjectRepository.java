package io.agileintellegence.pomtool.repositories;

import io.agileintellegence.pomtool.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);


}
