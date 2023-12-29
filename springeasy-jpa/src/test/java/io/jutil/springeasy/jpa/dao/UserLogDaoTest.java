package io.jutil.springeasy.jpa.dao;

import io.jutil.springeasy.jpa.Application;
import io.jutil.springeasy.jpa.entity.UserEntity;
import io.jutil.springeasy.jpa.entity.UserLogEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-10
 */
@SpringBootTest(classes = Application.class)
@Transactional
class UserLogDaoTest {
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    UserDao userDao;

    final String name = "blue";
    Long userId;

    @BeforeEach
    void beforeEach() {
        userLogDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    void testCrud() {
        this.addUser();
        var id = this.addUserLog();

        var view = userLogDao.getOne(id);
        Assertions.assertNotNull(view);
        Assertions.assertEquals(name, view.getUserName());

        view = userLogDao.getOne(k -> k.and("e.userId=:userId", "userId", userId));
        Assertions.assertNotNull(view);
        Assertions.assertEquals(name, view.getUserName());

        Assertions.assertEquals(1, userLogDao.count(k -> k.and("e.userId=:userId","userId", userId)));

        Assertions.assertEquals(1, userLogDao.deleteBy(Map.of("userId", userId)));
        Assertions.assertEquals(0, userLogDao.count());
        Assertions.assertEquals(0, userLogDao.listAll().size());
    }

    private Long addUserLog() {
        var entity = new UserLogEntity();
        entity.setUserId(userId);
        userLogDao.insert(entity);
        return entity.getId();
    }

    private void addUser() {
        var entity = new UserEntity();
        entity.setName(name);
        userDao.insert(entity);
        userId = entity.getId();
    }
}
