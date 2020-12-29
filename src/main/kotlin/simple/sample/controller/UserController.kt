package simple.sample.controller

import io.javalin.http.Context
import simple.sample.model.User
import javax.persistence.EntityManagerFactory

class UserController(val emf: EntityManagerFactory) {

    fun listAll(ctx: Context) {
        val em = emf.createEntityManager()
        ctx.json(em.createQuery("select u from User u").resultList)
        em.close()
    }

    fun findOne(ctx: Context) {
        val em = emf.createEntityManager()
        try {
            val p = em.createQuery("select u from User u where u.id = :id")
                .setParameter("id", ctx.pathParam("id").toLong()).singleResult
            ctx.json(p)
        } finally {
            em.close()
        }
    }

    fun insert(ctx: Context) {
        val em = emf.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val user = ctx.body<User>()
            em.persist(user)
            tx.commit()
            ctx.status(201).header("Location","/users/${user.id}")
        } catch (ex: Exception) {
            tx.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

    fun update(ctx: Context) {
        val em = emf.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val user = ctx.body<User>()
            user.id = ctx.pathParam("id").toLong()
            em.merge(user)
            tx.commit()
            ctx.status(204)
        } catch (ex: Exception) {
            tx.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

    fun del(ctx: Context) {

    }
}