package io.agileintellegence.pomtool.services;

import io.agileintellegence.pomtool.domain.Backlog;
import io.agileintellegence.pomtool.domain.Project;
import io.agileintellegence.pomtool.exceptions.ProjectIdException;
import io.agileintellegence.pomtool.repositories.BacklogRepository;
import io.agileintellegence.pomtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            String projectIdentifier = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(projectIdentifier);

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));

            }

            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }


    public Project findProjectByProjectId(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project Does not exists");
        }
        return project;

    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }


    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw  new ProjectIdException("Project with Id: " + projectId + " is not exists");
        }

        projectRepository.delete(project);
    }


}
