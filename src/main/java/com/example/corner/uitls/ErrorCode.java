package com.example.corner.uitls;
/**

    # @Time : 2023/1/6-20:52
    # @Author : YunQin2
    # @Email : yongqi_du21@tju.edu.cn
    # @Description : *错误码及其信息*
    
*/
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    /*  1xxx --> Front Error
        2xxx --> PARAMETER ERROR
        3xxx --> SQL ERROR
        4xxx -->
        5xxx --> Server Error
        6xxx --> Up stream Server Error
    * */
    OK(0, "Success"),
    FRONT_ERROR(1000, "Front Error"),
    IP_ERROR(1001, "Get Ip Error"),
    LOGIN_ERROR(1002, "Log in Error"),
    NO_SUCH_USER(1003, "No Such User"),
    PASSWORD_ERROR(1004, "Password Error"),
    NOT_LOGIN_YET(1005,"Not Login Yet. Please Login first"),
    MULTIPLE_USER(1006,"Multiple Users share the same student number --> Please Contact Manager for Correction."),
    SESSION_ERROR(1007,"Session Error."),
    MULTIPLE_CONFESSIONS_ERROR(1008, "You have Multiple Confessions, Error, Please Contact Developers."),
    HAD_CONFESSION_BEFORE_ERROR(1009, "You have Confession before, Please Delete it before You make a new one."),
    NOT_MATCHED_YET_CANNOT_MSG(1010, "You have not been matched yet, So no msg can be sent"),
    NOT_YOUR_PARTNER(1011, "Not Your Partner, So no msg should you send to that person."),
    MISSING_INFORMATION_ERROR(1012,"Your Information Is Incomplete."),
    CONCERN_ERROR(1013,"Concern Error."),
    NO_SUCH_MESSAGE(1014, "No Such Message."),
    PARAMETER_ERROR(2000,"Parameter Error."),
    PURSUIT_TARGET_NULL(2001, "None Pursuit Target Got"),
    SQL_ERROR(3000,"SQL Error, Please Check Your Mapper"),
    DELETE_CONFESSION_ERROR(3001,"Delete Confession Failed"),
    UPDATE_CONFESSION_ERROR(3002,"Update Confession Failed"),
    USER_SEARCH_ERROR(3003,"User Search Error"),
    USER_NOT_EXISTS(3004, "User Not Exists"),
    USER_ALREADY_EXISTS(3005,"User Already Exists"),
    SERVICE_ERROR(5000, "Service Error"),
    MESSAGE_SERVICE_ERROR(5001,"Message Service Error"),
    JSON_TRANSFORM_ERROR(5002,"Json Transform Error"),
    IMAGE_UPLOADING_ERROR(5003,"Image Uploading Error"),
    CAPTCHA_ERROR(5004,"Captcha Error"),
    TAG_ERROR(5005,"Tag Error"),
    POST_SERVER_ERROR(5006,"Post Server Error"),
    TEAM_SERVER_ERROR(5007,"Team Server Error"),
    TOKEN_LOGIN_ERROR(6001,"This token may no longer in used, Login with token Failed");
    private int code;
    private String msg;
}

