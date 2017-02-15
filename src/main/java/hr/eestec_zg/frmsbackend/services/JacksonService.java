package hr.eestec_zg.frmsbackend.services;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static hr.eestec_zg.frmsbackend.utilities.LambdaUtil.uncheckCall;


@Service
public class JacksonService {

    private final ObjectMapper objectMapper;

    @Autowired
    public JacksonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    public <K> List<K> readListOfObjects(String content, Class<K> clazz) {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return uncheckCall(() -> (List<K>) objectMapper.readValue(content, type));
    }

    public <K> K readJson(String content, Class<K> clazz) throws IOException {
        return objectMapper.readValue(content, clazz);
    }

    public String asJson(Object obj) {
        return uncheckCall(() -> objectMapper.writeValueAsString(obj));
    }

    public String asPrettyJson(Object obj) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return uncheckCall(() -> objectMapper.writeValueAsString(obj));
    }

    public <K> boolean isValidJsonForListOfObjects(String content, Class<K> clazz) {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            objectMapper.readValue(content, type);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
