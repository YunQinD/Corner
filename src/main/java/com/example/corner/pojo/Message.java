package com.example.corner.pojo;
/**

    # @Time : 2023/1/6-20:37
    # @Author : YunQin2
    # @Email : yongqi_du21@tju.edu.cn
    # @Description : *浏览器发送给服务器的websocket数据*

*/
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Message {
    private String toName;
    private String message;

}
