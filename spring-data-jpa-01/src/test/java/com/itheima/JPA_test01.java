package com.itheima;

import com.itheima.Utils.JPAUtils;
import com.itheima.domain.Customer;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;


public class JPA_test01 {

    /*
    *创建实体管理类工厂，借助Persistence的静态方法获取
    *其中传递的参数为持久化单元名称，需要jpa配置文件中指定
     * */
    @Test
    public void test(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJpa");
        //创建实体管理类
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //获取事务对象
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();
        Customer c = new Customer();
        c.setCustName("外滩十八号");
        c.setCustAddress("浦东新区");
        //保存操作
        entityManager.persist(c);
        //提交事务
        tx.commit();
        //释放资源
        entityManager.close();
        entityManagerFactory.close();
    }
        //增加
    @Test
    public void testadd(){
        //使用工具类获取实体管理类
        EntityManager em = JPAUtils.createEntityManager();
        //获取事务
        EntityTransaction tx = em.getTransaction();
        //开启事务
        tx.begin();
        Customer c = new Customer();
        c.setCustName("吉山股份有限公司");
        c.setCustAddress("珠吉路");
        //保存
        em.persist(c);
        //提交
        tx.commit();
        //释放资源
        em.close();
    }
        //查询
    @Test
    public void testFind(){
        EntityManager entityManager = JPAUtils.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        //开启事务
        tx.begin();
        //查询
        Customer customer = entityManager.find(Customer.class, 6l);
        System.out.println(customer);
        tx.commit();
        entityManager.close();

    }
    //更新
        @Test
    public void testUpdate(){

            EntityManager em = JPAUtils.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            //开启事务
            tx.begin();
            //先查询再修改
            Customer customer = em.find(Customer.class, 6l);
            customer.setCustName("苹果股份有限股市");
            em.merge(customer);
            //提交
            tx.commit();
            //关闭资源
            em.close();
        }
        //删除
    @Test
    public void testDel(){
        EntityManager em = JPAUtils.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        //开启事务
        tx.begin();
        //先查询再删除
        Customer customer = em.find(Customer.class, 6l);
        em.remove(customer);
        tx.commit();
        em.close();
    }
    //查询全部
    @Test
    public void testFindAll(){
        EntityTransaction tx = null;
        EntityManager em = null;
        try {
            em = JPAUtils.createEntityManager();
             tx = em.getTransaction();
            tx.begin();
            String sql = "from Customer";
            Query q = em.createQuery(sql);
            List<Customer> list = q.getResultList();
            for (Customer cst : list) {
                System.out.println(cst);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
    }
    //分页查询
    @Test
    public void findPage(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtils.createEntityManager();

            tx = em.getTransaction();

            tx.begin();

            String sql = "from Customer";

            Query q = em.createQuery(sql);

            //起始索引
            q.setFirstResult(2);
            //显示页大小
            q.setMaxResults(2);

            List list = q.getResultList();
            for (Object o : list) {
                System.out.println(o);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
    }
    //条件查询
    @Test
    public void findcondition(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtils.createEntityManager();

            tx = em.getTransaction();

            tx.begin();

            String sql = "from Customer where custName   like ? ";

            Query q = em.createQuery(sql);

            //设置条件,对占位符赋值,从1开始
            q.setParameter(1,"吉山%");
            //查询并返回结果
            //Object result = q.getSingleResult();//得到唯一的结果集
            List list = q.getResultList();
            System.out.println(list);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }

    }
    //排序查询
    @Test
    public void testOrder(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtils.createEntityManager();

            tx = em.getTransaction();

            tx.begin();

            String sql = "from Customer order by custId asc";

            Query q = em.createQuery(sql);

            List list = q.getResultList();
            System.out.println(list);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
    }
    //统计查询
    @Test
    public void findCount(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtils.createEntityManager();

            tx = em.getTransaction();

            tx.begin();

            //String sql = "select count(custId)   from Customer ";
            //String sql = "select sum(custId) from Customer ";
            //String sql = "select avg(custId) from Customer ";
            //String sql = "select max(custId) from Customer ";
            String sql = "select min(custId) from Customer ";

            Query q = em.createQuery(sql);

            Object o = q.getSingleResult();
            System.out.println(o);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
    }
}
