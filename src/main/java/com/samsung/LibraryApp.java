package com.samsung;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;



@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(LibraryApp.class, args);

        try {
            Console.main(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
