package starter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.filter.log.LogDetail;
import static io.restassured.config.FailureConfig.failureConfig;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;

public class ConfigFactory {
    public static RestAssuredConfig getDefaultConfig() {

        ResponseValidationFailureListener failureListener = (reqSpec, resSpec, response) ->
                System.out.printf("Failiure response status was %s and the body contained: %s",
                        response.getStatusCode(), response.body().asPrettyString());

        return RestAssured.config()
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .failureConfig(failureConfig().failureListeners(failureListener))
                .redirect(redirectConfig().maxRedirects(1))
                .objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(getDefaultMapper()));
    }

    private static Jackson2ObjectMapperFactory getDefaultMapper() {
        return (type, s) -> {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return om;
        };
    }
}
