
package com.booking.medical.components.seeders;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.booking.medical.configs.EnvConfig;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("dev")
@Slf4j
public class SeederRunner implements CommandLineRunner {

    private final List<Seeder> seeders;
    private EnvConfig envConfig;

    public SeederRunner(List<Seeder> seeders, EnvConfig envConfig) {
        this.seeders = seeders;
        this.envConfig = envConfig;
    }

    @Override
    public void run(String... args) throws Exception {
        if (envConfig.getSeeder()) {
            log.info("START RUN SEEDER");
            seeders.forEach(Seeder::seed); // seeders.forEach(seeder ->seeder.seed());
            log.info("COMPLETED RUN SEEDER");
        } else {
            log.info("SEEDER WAS SKIPPED");
        }
    }

}