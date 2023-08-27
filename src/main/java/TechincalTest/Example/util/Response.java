package TechincalTest.Example.util;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response {
    private String Code;
    private String Msg;
    private Object Data;


}
