package com.protools.flowablePOC.services;


import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.task.api.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowService {
    private Logger logger =LogManager.getLogger(WorkflowService.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    // Process execution and setBusinessKey
    @Transactional
    public JSONObject startProcess(String ProcessKey, String BusinessKey){
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
        processInstanceBuilder.businessKey(BusinessKey).processDefinitionKey(ProcessKey).start();
        //runtimeService.startProcessInstanceByKey(ProcessKey);
        List<ProcessInstance> liste = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(ProcessKey)
                .list();
        logger.info("Process Instance ID : " + liste.get(0).getId());
        logger.info("Added businessKey to the process :"+ liste.get(0).getBusinessKey() );


        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("id", liste.get(0).getId());
        jsonResponse.put("name",liste.get(0).getName());
        jsonResponse.put("processKey", ProcessKey);
        jsonResponse.put("startTime", liste.get(0).getStartTime());
        jsonResponse.put("businessKey", liste.get(0).getBusinessKey());
        jsonResponse.put("documentation", liste.get(0).getDescription());
        return(jsonResponse);

    }

    @Transactional
    public void claimTasks(String taskID, String assignee){
        List<Task> taskInstances = taskService.createTaskQuery().taskId(taskID).taskAssignee(assignee).active().list();
        if (taskInstances.size() > 0) {
            for (Task t : taskInstances) {
                taskService.addCandidateGroup(t.getId(), "userTeam");
                logger.info("> Claiming task: " + t.getId());
                taskService.claim(t.getId(),assignee);
            }
        } else {
            logger.info("\t \t >> No task found.");
        }
    }

    @Transactional
    public void completeTask(String taskID, HashMap<String,Object> variables, String assignee){
        List<Task> taskInstances = taskService.createTaskQuery().taskId(taskID).taskAssignee(assignee).active().list();
        logger.info("> Completing task from process : " + taskID);
        logger.info("\t > Variables : " + variables.toString());
        if (taskInstances.size() > 0) {
            for (Task t : taskInstances) {
                taskService.addCandidateGroup(t.getId(), "userTeam");
                logger.info("> Completing task: " + t.getId());
                taskService.complete(t.getId(),variables);
            }
        } else {
            logger.info("\t \t >> There are no task for me to complete");
        }
    }

    @Transactional
    public void deleteProcessInstance( String ProcessID) {
        runtimeService.deleteProcessInstance(ProcessID, null);
    }

    @Transactional
    public void suspendProcessInstance( String ProcessID) {
        runtimeService.suspendProcessInstanceById(ProcessID);
    }

    @Transactional
    public void restartProcessInstance( String ProcessID) {
        runtimeService.activateProcessInstanceById(ProcessID);
    }
}
