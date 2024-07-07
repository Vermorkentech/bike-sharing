package io.axoniq.bikesharing;

import io.axoniq.bikesharing.api.messages.BikeStatus;
import io.axoniq.bikesharing.query.customers.UserProfile;
import io.axoniq.dataprotection.cryptoengine.jpa.KeyEntity;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackageClasses = {BikeStatus.class, TokenEntry.class, UserProfile.class, KeyEntity.class})
@SpringBootApplication
@EnableScheduling
public class BikeSharingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikeSharingApplication.class, args);
	}

	@Autowired
    public void configureSerializers(ObjectMapper objectMapper) {
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                                           ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    }

}
