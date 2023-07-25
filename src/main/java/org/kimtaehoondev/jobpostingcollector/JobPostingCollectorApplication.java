package org.kimtaehoondev.jobpostingcollector;

import org.kimtaehoondev.jobpostingcollector.config.BaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {JobPostingCollectorApplication.class, BaseConfig.class})
public class JobPostingCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPostingCollectorApplication.class, args);
	}

}
