package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
//@RestController : 해당 컨트롤러에 모두 @ResponseBody가 적용되는 효과를 갖고 있음
public class ResponseBodyController {

    /**
     * HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달함
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }


    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * ResponseEntity (HTTP 메시지 헤더, 바디 정보, HTTP 응답 코드를 갖고 있음)
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    /**
     * @ResponseBody를 사용하면 view를 사용하지 않고 HTTP 메시지 컨버터를 통해서
     * HTTP 메시지를 직접 입력할 수 있음
     * ResponseEntity도 동일한 방식으로 동작한다
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }


    /**
     * HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환됨
     * 동적으로 HTTP 응답 코드를 반환할 수 있음
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * @ResponseBody를 사용할 때 @ResponseStatus 사용하면 응답 코드를 설정할 수 있음
     * 동적으로 변경은 못함
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }
}
