package hello.springmvc.basic;

import lombok.Data;

@Data //@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 모두 적용
public class HelloData {
    private String username;
    private int age;
}
