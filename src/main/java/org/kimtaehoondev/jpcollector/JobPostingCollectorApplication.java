package org.kimtaehoondev.jpcollector;

import org.kimtaehoondev.jpcollector.config.BaseConfig;
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
