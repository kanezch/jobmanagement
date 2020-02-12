package com.kanezheng.app.jobmanagement.repository.schedule;

import com.kanezheng.app.jobmanagement.dao.schedule.EmailNotifyJobEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class EmailNotifyJobRepositoryCustomImpl implements EmailNotifyJobRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public EmailNotifyJobEntity findNextWaitingJob() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<EmailNotifyJobEntity> q = cb.createQuery(EmailNotifyJobEntity.class);

        Root<EmailNotifyJobEntity> j = q.from(EmailNotifyJobEntity.class);

        q.select(j).where(cb.equal(j.get("status"), 0)).orderBy(cb.desc(j.get("id")));

        TypedQuery<EmailNotifyJobEntity> query = em.createQuery(q).setMaxResults(1);

        EmailNotifyJobEntity  result = query.getSingleResult();

        return result;
    }
}
