import com.drools.demo.domain.model.Order;
import com.drools.demo.domain.model.Person;
import com.drools.demo.util.DroolsUtil;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
//        KieSession ksession = DroolsUtil.getKieSessionByName("point_ksession");
//        KieSession ksession = KieServices.Factory.get()
//                .getKieClasspathContainer().newKieSession("point_ksession");
        StatelessKieSession statelessKieSession = KieServices.Factory.get()
                .getKieClasspathContainer().newStatelessKieSession();
        List<Order> orderList = getInitData();
        try {
            for (int i = 0; i < orderList.size(); i++) {
                Order o = orderList.get(i);
//                ksession.insert(o);
//                ksession.fireAllRules();
                statelessKieSession.execute(o);
                // 执行完规则后, 执行相关的逻辑
                addScore(o);
            }
        } catch (Exception e) {

        } finally {
//            ksession.destroy();
        }

    }
    private static void addScore(Order o) {
        System.out.println("用户" + o.getPerson().getName() + "享受额外增加积分: " + o.getScore());
    }

    private static List<Order> getInitData() throws Exception {
        List<Order> orderList = new ArrayList<Order>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        {
            Order order = new Order();
            order.setAmount(80);
            order.setBookingDate(df.parse("2015-07-01"));
            Person user = new Person();
            user.setLevel(1);
            user.setName("Name1");
            order.setPerson(user);
//            order.setScore(111);
            orderList.add(order);
        }
        {
            Order order = new Order();
            order.setAmount(200);
            order.setBookingDate(df.parse("2015-07-02"));
            Person user = new Person();
            user.setLevel(2);
            user.setName("Name2");
            order.setPerson(user);
            orderList.add(order);
        }
        {
            Order order = new Order();
            order.setAmount(800);
            order.setBookingDate(df.parse("2015-07-03"));
            Person user = new Person();
            user.setLevel(3);
            user.setName("Name3");
            order.setPerson(user);
            orderList.add(order);
        }
        {
            Order order = new Order();
            order.setAmount(1500);
            order.setBookingDate(df.parse("2015-07-04"));
            Person user = new Person();
            user.setLevel(4);
            user.setName("Name4");
            order.setPerson(user);
            orderList.add(order);
        }
        return orderList;
    }
}
