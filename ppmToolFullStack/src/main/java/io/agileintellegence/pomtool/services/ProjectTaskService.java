package io.agileintellegence.pomtool.services;

import io.agileintellegence.pomtool.domain.Backlog;
import io.agileintellegence.pomtool.domain.Project;
import io.agileintellegence.pomtool.domain.ProjectTask;
import io.agileintellegence.pomtool.exceptions.ProjectIdException;
import io.agileintellegence.pomtool.exceptions.ProjectNotFoundException;
import io.agileintellegence.pomtool.repositories.BacklogRepository;
import io.agileintellegence.pomtool.repositories.ProjectRepository;
import io.agileintellegence.pomtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private  ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //Exceptions: Project not found

        // All PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);


        if (backlog == null) {
            throw new ProjectNotFoundException( "Project Not Found!");
        }
        // set the backlog to the projectTask
        projectTask.setBacklog(backlog);
        // Update the Backlog sequence
        Integer backLogSequence = backlog.getPTSequence();

        backLogSequence++;

        backlog.setPTSequence(backLogSequence);
        //Add sequence to project task
        projectTask.setProjectSequence(projectIdentifier + "-" + backLogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        // Initial priority when priority is null
        if( projectTask.getPriority() == 0 || projectTask.getPriority()==null){ // In the future we need to check if priority is 0
            projectTask.setPriority(3);
        }

        // Initial Status when status is null

        if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {

        if (projectRepository.findByProjectIdentifier(backlog_id) == null){
            throw new ProjectNotFoundException("Project with id: " + backlog_id + " not found");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        //make sure we are searching on the right backlog
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if (backlog == null){
            throw new ProjectNotFoundException("Project with id: " + backlog_id + " not found");
        }
        // make sure our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if (projectTask == null){
            throw new ProjectNotFoundException("Project task not " + pt_id + " found");
        }
        //make sure  that the  backlog/project id in the path corresponds right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task "+ pt_id + " does not exists in project: " + backlog_id);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id ){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTaskRepository.delete(projectTask);

    }
}
