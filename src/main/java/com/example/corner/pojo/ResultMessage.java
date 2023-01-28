package com.example.corner.pojo;
/**

    # @Time : 2023/1/6-20:41
    # @Author : YunQin2
    # @Email : yongqi_du21@tju.edu.cn
    # @Description : *服务器发送给浏览器的websocket数据*
    
*/
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResultMessage {

    private boolean isSystem;
    private String fromName;
    private Object message;

}
