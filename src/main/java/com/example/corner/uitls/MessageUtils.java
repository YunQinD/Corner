package com.example.corner.uitls;
/**

    # @Time : 2023/1/6-20:55
    # @author : YunQin2
    # @Email : yongqi_du21@tju.edu.cn
    # @Description : *封装消息的工具类*

*/

import com.example.corner.pojo.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtils {

    public static String getMessage(boolean isSystemMessage, String fromName, Object message){
        try {
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setSystem(isSystemMessage);
            resultMessage.setMessage(message);
            if (fromName != null){
                resultMessage.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(resultMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
