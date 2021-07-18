package com.jasper.scaffold;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasper.scaffold.api.entity.User;
import com.jasper.scaffold.api.entity.enums.GenderEnum;
import com.jasper.scaffold.api.mapper.UserMapper;
import com.jasper.scaffold.api.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
class ScaffoldApplicationTests {

	@Resource
	private UserMapper userMapper;

	@Resource
	private IUserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void testInsert() {
//		for (int i=1; i<15; i++) {
//			User user = new User()
//					.setAge(i)
//					.setEmail("1234@11.com")
//					.setName("Name_" + i)
//					.setMark(false); // false 未删除
//			userMapper.insert(user);
//		}
	}

	@Test
	void testChainQuery() {
		String queryVal = "jasper";
		// 链式查询 普通
		User user = userService.query().eq("name", queryVal).one();
		// 链式查询 lambda 式
		User user2 = userService.lambdaQuery().eq(User::getName, queryVal).one();
		System.out.println(user);
		System.out.println(user2);
	}

	@Test
	void testChainUpdate() {
		// 链式更改 普通
		String queryVal = "zhangsan";
		userService.update().eq("name", queryVal).remove();
		// 链式更改 lambda 式
		User updateEntity = new User().setEmail("1111@qq.com");
		userService.lambdaUpdate().eq(User::getName, "lisi").update(updateEntity);
	}

	@Test
	void testPagination() {
		Page<User> page = new Page<>(1, 5);
        page.setDesc("age");
		IPage<User> userPage = userMapper.selectPage(page, Wrappers.<User>query().gt("age", 5));
		log.error("总条数 -------------> {}", userPage.getTotal());
		log.error("当前页数 -------------> {}", userPage.getCurrent());
		log.error("当前每页显示数 -------------> {}", userPage.getSize());
        List<User> records = userPage.getRecords();
        log.error("records: {}", records);
    }

    @Test
     void lambdaPagination() {
        Page<User> page = new Page<>(1, 3);
        IPage<User> result = userMapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge));
        log.error("总条数 -------------> {}", result.getTotal());
        log.error("当前页数 -------------> {}", result.getCurrent());
        log.error("当前每页显示数 -------------> {}", result.getSize());
        List<User> records = result.getRecords();
        log.error("records: {}", records);
    }

    @Test
    void testEnum() {
        User user = new User()
                .setGender(GenderEnum.MALE)
                .setAge(12)
                .setEmail("1234@11.com")
                .setName("Name_jjjj")
                .setMark(false); // false 未删除
        userMapper.insert(user);

        List<User> userList = userService.lambdaQuery().eq(User::getGender, GenderEnum.MALE).list();
        log.error("用户数：{}", userList.size());
    }

//    @Test
//    public void testUpdateByIdSucc() {
//        User user = new User();
//        user.setAge(18);
//        user.setEmail("test@.com");
//        user.setName("optlocker");
//        user.setVersion(1);
//        userMapper.insert(user);
//        Long id = user.getId();
//
//        User userUpdate = new User();
//        userUpdate.setId(id);
//        userUpdate.setAge(19);
//        userUpdate.setVersion(1);
//        assertThat(userMapper.updateById(userUpdate)).isEqualTo(1);
//        assertThat(userUpdate.getVersion()).isEqualTo(2);
//    }
}
