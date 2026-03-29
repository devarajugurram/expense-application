package com.exp.backend.template.helpers;

import com.exp.backend.aop.MessageInterface;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName : ResponseHelperMethods
 * This class is a helper methods provider.
 * So that there is not connection between two services.
 * This class related to giving response to client in a simplified and clean way.
 */
@Component
public class ResponseHelperMethods {
    /**
     * MethodName - getRegistrationResponseHelper
     * This method used to create a response to be sent to frontend(client).
     * @param message position of the request
     * @param code status of the request
     * @param localDateTime success or failure timestamp.
     * @return Map it is sued to return customized map for response.
     */
    @MessageInterface
    public Map<String,Object> getRegistrationResponseHelper(String message,
                                                            String code,
                                                            LocalDateTime localDateTime) {
        Map<String,Object> result = new HashMap<>();
        result.put("message",message);
        result.put("status",code);
        result.put("timestamp",localDateTime+"");
        return result;
    }

    /**
     *
     * @param localDateTime
     * @param code
     * @return
     */

    @MessageInterface
    public Map<String,Object> getLoginResponseHelper(
                                                     LocalDateTime localDateTime,
                                                     String code) {
        Map<String,Object> result = new HashMap<>();
        result.put("status",code);
        result.put("timestamp",localDateTime);
        return result;
    }
}
