package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 요청 메시지 - 단순 텍스트
 * - HTTP 메시지 바디에 데이터를 직접 담아서 요청
 * HTTP API에서 주로 사용하는 방식
 * JSON, XML, TEXT 데이터 형식으로 보냄
 * 이중 주로 사용하는 데이터 형식은 JSON
 * HTTP 메서드: POST, PUT PATCH
 * 요청 파라미터(쿼리 파라미터+HTML form)와 다르게 HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우
 *
 * @RequestParam, @ModelAttribute를 사용할 수 없음
 * HTTP 메시지 바디를 InputStream으로 직접 읽어옴
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }


    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream(Writer): HTTP 요청 메시지의 바디에 직접 결과 출력
     * <p>
     * 스프링 MVC에서 지원하는 파라미터
     * InputStream(Reader), OutputStream(Writer)
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }


    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회(요청 파라미터를 조회하는 기능과 관계 없음; @RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * <p>
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회아님)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * 참고: 메시지 컨버터는 메시지 바디를 읽어서 객체나 문자로 변환해주는 기능을 함
     * <p>
     * HttpEntity 를 상속받은 객체들
     * - RequestEntity
     * - HttpMethod, url 정보가 추가, 요청에서 사용
     * - ResponseEntity
     * - Http 상태 코드 설정 가능, 응답에서 사용
     * - return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }


    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 헤더 정보가 필요하면 HttpEntity 또는 @RequestHeader를 사용하면 된다
     *
     * 요청 파라미터 vs HTTP 메시지 바디
     * - 요청 파라미터를 조회하는 기능: @RequestParam, @ModelAttribute
     * - HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }


}
