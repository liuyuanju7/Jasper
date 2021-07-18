package jasper.servicefeign.service;

import org.springframework.stereotype.Component;

/**
 * @author liuyuanju1
 * @date 2020/9/2
 * @description:
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi{
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
