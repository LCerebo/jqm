/**
 * Copyright © 2013 enioka. All rights reserved
 * Authors: Marc-Antoine GOUILLART (marc-antoine.gouillart@enioka.com)
 *          Pierre COPPEE (pierre.coppee@enioka.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enioka.jqm.test.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.enioka.jqm.jpamodel.DeploymentParameter;
import com.enioka.jqm.jpamodel.GlobalParameter;
import com.enioka.jqm.jpamodel.History;
import com.enioka.jqm.jpamodel.JndiObjectResource;
import com.enioka.jqm.jpamodel.Node;

public class TestHelpers
{
    public static Logger jqmlogger = Logger.getLogger(TestHelpers.class);
    public static JndiObjectResource db = null;

    public static com.enioka.jqm.jpamodel.Queue qVip, qNormal, qSlow, qVip2, qNormal2, qSlow2, qVip3, qNormal3, qSlow3;
    public static Node node, node2, node3, nodeMix, nodeMix2;

    public static DeploymentParameter dpVip, dpNormal, dpSlow, dpVip2, dpNormal2, dpSlow2, dpVip3, dpNormal3, dpSlow3, dpVipMix, dpVipMix2;

    public static GlobalParameter gpCentral, gpEclipse;

    public static void createLocalNode(EntityManager em)
    {
        db = CreationTools.createDatabaseProp("jdbc/marsu", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:testdb", "SA", "", em,
                "SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS", null);

        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mavenRepo", "http://repo1.maven.org/maven2/", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mailSmtpServer", "smtp.gmail.com", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mailFrom", "jqm-noreply@gmail.com", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mailSmtpPort", "587", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mailSmtpUser", "jqm.noreply@gmail.com", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mailSmtpPassword", "marsu1952", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("mailUseTls", "true", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("defaultConnection", "jdbc/marsu", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("deadline", "10", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("logFilePerLaunch", "false", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("internalPollingPeriodMs", "10000", em);
        TestHelpers.gpCentral = CreationTools.createGlobalParameter("aliveSignalMs", "60000", em);

        TestHelpers.qVip = CreationTools.initQueue("VIPQueue", "Queue for the winners", 42, em);
        TestHelpers.qNormal = CreationTools.initQueue("NormalQueue", "Queue for the ordinary job", 7, em);
        TestHelpers.qSlow = CreationTools.initQueue("SlowQueue", "Queue for the bad guys", 3, em);

        TestHelpers.qVip2 = CreationTools.initQueue("VIPQueue2", "Queue for the winners2", 42, em);
        TestHelpers.qNormal2 = CreationTools.initQueue("NormalQueue2", "Queue for the ordinary job2", 7, em);
        TestHelpers.qSlow2 = CreationTools.initQueue("SlowQueue2", "Queue for the bad guys2", 3, em);

        TestHelpers.qVip3 = CreationTools.initQueue("VIPQueue3", "Queue for the winners3", 42, em);
        TestHelpers.qNormal3 = CreationTools.initQueue("NormalQueue3", "Queue for the ordinary job3", 7, em);
        TestHelpers.qSlow3 = CreationTools.initQueue("SlowQueue3", "Queue for the bad guys3", 3, em);

        TestHelpers.node = CreationTools.createNode("localhost", 0, "./target/payloads/", "./../", "./testprojects/jqm-test-xml/", em);
        TestHelpers.node2 = CreationTools.createNode("localhost2", 0, "./target/payloads/", "./../", "./testprojects/jqm-test-xml/", em);
        TestHelpers.node3 = CreationTools.createNode("localhost3", 0, "./target/payloads/", "./../", "./testprojects/jqm-test-xml/", em);
        TestHelpers.nodeMix = CreationTools.createNode("localhost4", 0, "./target/payloads/", "./../", "./testprojects/jqm-test-xml/", em);
        TestHelpers.nodeMix2 = CreationTools.createNode("localhost5", 0, "./target/payloads/", "./../", "./testprojects/jqm-test-xml/", em);

        TestHelpers.dpVip = CreationTools.createDeploymentParameter(1, node, 40, 1, qVip, em);
        TestHelpers.dpVipMix = CreationTools.createDeploymentParameter(2, nodeMix, 3, 1, qVip, em);
        TestHelpers.dpVipMix2 = CreationTools.createDeploymentParameter(2, nodeMix2, 3, 1, qVip, em);
        TestHelpers.dpNormal = CreationTools.createDeploymentParameter(1, node, 2, 300, qNormal, em);
        TestHelpers.dpSlow = CreationTools.createDeploymentParameter(1, node, 1, 1000, qSlow, em);

        TestHelpers.dpVip2 = CreationTools.createDeploymentParameter(1, node2, 3, 100, qVip2, em);
        TestHelpers.dpNormal2 = CreationTools.createDeploymentParameter(1, node2, 2, 300, qNormal2, em);
        TestHelpers.dpSlow2 = CreationTools.createDeploymentParameter(1, node2, 1, 1000, qSlow2, em);

        TestHelpers.dpVip3 = CreationTools.createDeploymentParameter(1, node3, 3, 100, qVip3, em);
        TestHelpers.dpNormal3 = CreationTools.createDeploymentParameter(1, node3, 2, 300, qNormal3, em);
        TestHelpers.dpSlow3 = CreationTools.createDeploymentParameter(1, node3, 1, 1000, qSlow3, em);
    }

    public static void cleanup(EntityManager em)
    {
        em.getTransaction().begin();
        em.createQuery("DELETE GlobalParameter WHERE 1=1").executeUpdate();
        em.createQuery("DELETE Deliverable WHERE 1=1").executeUpdate();
        em.createQuery("DELETE DeploymentParameter WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JobHistoryParameter WHERE 1=1").executeUpdate();
        em.createQuery("DELETE Message WHERE 1=1").executeUpdate();
        em.createQuery("DELETE MessageJi WHERE 1=1").executeUpdate();
        em.createQuery("DELETE History WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JobDefParameter WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JobParameter WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JobInstance WHERE 1=1").executeUpdate();
        em.createQuery("DELETE Node WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JobDef WHERE 1=1").executeUpdate();
        em.createQuery("DELETE Queue WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JndiObjectResourceParameter WHERE 1=1").executeUpdate();
        em.createQuery("DELETE JndiObjectResource WHERE 1=1").executeUpdate();
        em.createQuery("DELETE DatabaseProp WHERE 1=1").executeUpdate();
        em.getTransaction().commit();
    }

    public static void printHistoryTable(EntityManager em)
    {
        List<History> res = em.createQuery("SELECT j FROM History j", History.class).getResultList();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

        jqmlogger.debug("==========================================================================================");
        for (History h : res)
        {
            jqmlogger.debug("JobInstance Id: " + h.getId() + " | " + h.getState() + " | JD: " + h.getJd().getId() + " | "
                    + h.getQueue().getName() + " | enqueue: " + format.format(h.getEnqueueDate().getTime()) + " | attr: "
                    + format.format(h.getAttributionDate().getTime()) + " | exec: " + format.format(h.getExecutionDate().getTime())
                    + " | end: " + format.format(h.getEndDate().getTime()));
        }
        jqmlogger.debug("==========================================================================================");
    }

    public static void waitFor(long nbHistories, int timeoutMs, EntityManager em)
    {
        TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(h) FROM History h WHERE h.status = 'ENDED' OR h.status = 'CRASHED'  OR h.status = 'KILLED'", Long.class);

        Calendar start = Calendar.getInstance();
        while (q.getSingleResult() != nbHistories && Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis() <= timeoutMs)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
