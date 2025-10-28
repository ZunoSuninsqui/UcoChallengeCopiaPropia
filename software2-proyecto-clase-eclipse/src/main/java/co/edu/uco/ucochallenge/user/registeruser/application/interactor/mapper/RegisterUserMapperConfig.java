package co.edu.uco.ucochallenge.user.registeruser.application.interactor.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RegisterUserMapperConfig {

        @Bean
        @ConditionalOnMissingBean
        RegisterUserMapper registerUserMapper() {
                return Mappers.getMapper(RegisterUserMapper.class);
        }
}