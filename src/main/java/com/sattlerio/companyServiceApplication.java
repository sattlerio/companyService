package com.sattlerio;

import com.sattlerio.db.CompanyDAO;
import com.sattlerio.db.UserDAO;
import com.sattlerio.resources.CompanyResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class companyServiceApplication extends Application<companyServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new companyServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "companyService";
    }

    @Override
    public void initialize(final Bootstrap<companyServiceConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        new ResourceConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(companyServiceConfiguration configuration,
                    Environment environment) {
        try {
            final DBIFactory factory = new DBIFactory();
            final DBI jdbi = factory.build(environment, configuration.getSourceFactory(), "postgresql");
            CompanyDAO companyDAO = jdbi.onDemand(CompanyDAO.class);
            UserDAO userDAO = jdbi.onDemand(UserDAO.class);

            final CompanyResource companyResource = new CompanyResource(companyDAO, userDAO);

            environment.jersey().register(companyResource);
        } catch (Exception e) {
            final Logger log = LoggerFactory.getLogger(CompanyResource.class);
            log.error(e.getLocalizedMessage());
            log.error(e.getMessage());

        }
    }

}
