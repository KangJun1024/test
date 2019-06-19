package com.kangjun.droolsdemo;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class HelloTest {

    private static KieContainer container = null;
    private KieSession statefulKieSession = null;

    @Test
    public void test2() {
        KieServices kieServices = KieServices.Factory.get();
        container = kieServices.getKieClasspathContainer();
        statefulKieSession = container.newKieSession("ksession-hello");
        Person person = new Person("Kevin",12);
        statefulKieSession.insert(person);
        statefulKieSession.fireAllRules();
        Person person2 = new Person("Jack",50);
        statefulKieSession.insert(person2);
        statefulKieSession.fireAllRules();

        statefulKieSession.dispose();
    }
}