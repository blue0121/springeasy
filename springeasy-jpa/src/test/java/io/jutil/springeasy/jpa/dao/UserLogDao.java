package io.jutil.springeasy.jpa.dao;

import io.jutil.springeasy.jpa.entity.UserLogEntity;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

/**
 * @author Jin Zheng
 * @since 2023-11-10
 */
@Repository
public class UserLogDao extends BaseDao<UserLogEntity, Long> {
    @Override
    protected String select(String where) {
        return this.formatHql("""
                select e, u.name from {entity} e
                 inner join UserEntity u on u.id=e.userId {0}""", where);
    }

    @Override
    protected <T> T extractFromTuple(Class<T> clazz, Tuple tuple) {
        if (Number.class.isAssignableFrom(clazz)) {
            return tuple.get(0, clazz);
        }

        var e = tuple.get(0, clazz);
        var userName = tuple.get(1, String.class);

        var entity = (UserLogEntity) e;
        entity.setUserName(userName);
        return e;
    }

}
