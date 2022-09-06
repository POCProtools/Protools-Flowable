package com.protools.flowableDemo;

import com.protools.flowableDemo.services.WorkflowService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flowable.engine.RepositoryService;
import org.flowable.eventregistry.api.EventRepositoryService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowablePocApplication {
	private Logger logger = LogManager.getLogger(FlowablePocApplication.class);
	private final RepositoryService repositoryService;
	private final EventRepositoryService eventRepositoryService;

	public FlowablePocApplication(RepositoryService repositoryService, EventRepositoryService eventRepositoryService) {
		this.repositoryService = repositoryService;
		this.eventRepositoryService = eventRepositoryService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FlowablePocApplication.class, args);
	}


	@EventListener(ApplicationStartedEvent.class)
	public void started() {
		logger.info("Application started.");

		logger.info("Processes deployed: {}", repositoryService.createDeploymentQuery().count());
		logger.info("Events deployed:    {}", eventRepositoryService.createDeploymentQuery().count());

	}

}
