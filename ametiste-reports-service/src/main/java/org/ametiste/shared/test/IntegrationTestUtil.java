package org.ametiste.shared.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IntegrationTestUtil {
	
    public static byte[] convertObjectToFormUrlEncodedBytes(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        @SuppressWarnings("unchecked")
		Map<String, Object> propertyValues = mapper.convertValue(object, Map.class);

        Set<String> propertyNames = propertyValues.keySet();
        Iterator<String> nameIter = propertyNames.iterator();

        StringBuilder formUrlEncoded = new StringBuilder();

        for (int index=0; index < propertyNames.size(); index++) {
            String currentKey = nameIter.next();
            Object currentValue = propertyValues.get(currentKey);

            formUrlEncoded.append(currentKey);
            formUrlEncoded.append("=");
            formUrlEncoded.append(currentValue);

            if (nameIter.hasNext()) {
                formUrlEncoded.append("&");
            }
        }

        return formUrlEncoded.toString().getBytes();
    }
    
    public static byte[] convertObjectToJSONBytes(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        try {
			return mapper.writeValueAsBytes(object);
		} catch (Exception e) {
			throw new RuntimeException("OOps.", e);
		}
    }
}