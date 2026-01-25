package com.exp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Devaraju Gurram
 * @version 1.0.0
 * @since 2026
 *
 * Expense Management Application.
 * This application allows users authentication.
 * manage expenses, and generate reports.
 *
 * Enabling Aspect Oriented programming, Caching.
 */
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableCaching
public class ExpBackendApplication {

    /**
     *
     * @param args args are command line arguments, it will accept multiple and single input strings
     *
     */
	public static void main(String[] args) {
		SpringApplication.run(ExpBackendApplication.class, args);
	}

}
