package com.example.test.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import com.example.test.job.UpdateDataJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerService {

	private static final String CRON_EVERY_HOUR = "0 0 */2 ? * *";
	
	private String jobId = "test.job";
	
	@Autowired
	private Scheduler scheduler;

	public void createJob() throws SchedulerException {
		JobKey jobKey = new JobKey(jobId);
		
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
	    jobDetailFactory.setName(jobKey.getName());
	    jobDetailFactory.setGroup(jobKey.getGroup());
		jobDetailFactory.setJobClass(UpdateDataJob.class);
	    jobDetailFactory.setDurability(true);
	    jobDetailFactory.afterPropertiesSet();
	    
	    JobDetail jobDetail = jobDetailFactory.getObject();
	    
	    scheduler.addJob(jobDetail, true);
	    
	    TriggerKey triggerKey = new TriggerKey(jobId + ".trigger");
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		
		log.info("Starting scheduled job: {}", jobKey.getName());
		
		trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerKey)
				.startNow()
				.forJob(scheduler.getJobDetail(jobKey))
	    		.withSchedule(CronScheduleBuilder.cronSchedule(CRON_EVERY_HOUR))
	    		.build();
		
		scheduler.scheduleJob(trigger);
	}
	
	public void rescheduleJob(String cron) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey("grade.job" + ".trigger");
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(trigger != null && !trigger.getCronExpression().equals(cron)) {
			log.info("Cron for job {} changed from {} to {}, reschedule job", jobId, trigger.getCronExpression(), cron);
			CronTrigger newTrigger = trigger.getTriggerBuilder()
					.withSchedule(CronScheduleBuilder.cronSchedule(cron))
					.build();
			
			scheduler.rescheduleJob(trigger.getKey(), newTrigger);
		}
	}
	
	public void executeManually() throws SchedulerException
	{
		JobKey jobKey = new JobKey(jobId);
		scheduler.triggerJob(jobKey);
	}

}
