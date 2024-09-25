package com.generation.amsha.user.dao;

import com.generation.amsha.user.model.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserAccountDaoImpl implements UserAccountDao{
    private final EntityManager entityManager;
    @Autowired
    public UserAccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public UserAccount register(UserAccount userAccount) {
        entityManager.persist(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount updateUser(UserAccount userAccount) {
        entityManager.merge(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount getUserById(Integer userId) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where id = :userId", UserAccount.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    @Override
    public UserAccount getUserByEmail(String email) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where email = :email", UserAccount.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public UserAccount getUserByPhoneNumber(String phoneNumber) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where phoneNumber = :phoneNumber", UserAccount.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult();
    }

    @Override
    public List<UserAccount> getAllUsers() {
        TypedQuery<UserAccount> query = entityManager.createQuery("from userAccount", UserAccount.class);
        return query.getResultList();
    }

    @Override
    public UserAccount archiveUser(UserAccount userAccount) {
        entityManager.merge(userAccount);
        return userAccount;
    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where phoneNumber = :phoneNumber", UserAccount.class);
        query.setParameter("phoneNumber", phoneNumber);
        List<UserAccount> results = query.getResultList();
        return !results.isEmpty();
    }
}
