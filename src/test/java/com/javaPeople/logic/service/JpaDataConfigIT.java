package com.javaPeople.logic.service;

import com.javaPeople.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)  //junit5
@DataJpaTest  //jpa configuration run
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //do not replace datasource from db-config.
@ContextConfiguration(classes = Application.class)
public abstract class JpaDataConfigIT {

}
