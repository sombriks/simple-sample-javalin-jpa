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
}