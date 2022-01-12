package starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;

import static net.serenitybdd.rest.RestDefaults.config;

public class Mapper {
    public static void main(String []args){
        ObjectMapper mapper = new ObjectMapper();
    }

    public static io.restassured.mapper.ObjectMapper objectMapper(final io.restassured.mapper.ObjectMapper objectMapper) {
        RestAssured.config = config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                .defaultObjectMapper(objectMapper));
        return config().getObjectMapperConfig().defaultObjectMapper();
    }
}
