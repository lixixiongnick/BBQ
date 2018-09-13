package com.itheima.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAUtils {
    //jpa实体管理类工厂
    private static EntityManagerFactory em;

    //静态代码块
    static {
        //创建实体管理类工厂
        em = Persistence.createEntityManagerFactory("myJpa");
    }
    //使用管理器工厂生产管理器对象
    public static EntityManager createEntityManager(){
        EntityManager entityManager = em.createEntityManager();
        return entityManager;
    }
}
