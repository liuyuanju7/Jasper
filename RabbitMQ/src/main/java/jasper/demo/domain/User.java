package jasper.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liuyuanju1
 * @date 2020/3/28
 * @description:
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

    private String name;

    private String code;

    private int age;
}
