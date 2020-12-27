package simple.sample.controller

import io.javalin.http.Context
import javax.persistence.EntityManagerFactory

class ProductController(val emf: EntityManagerFactory) {

    fun listAll(ctx: Context) {
        val em = emf.createEntityManager()
        ctx.json(em.createQuery("select p from Product p").resultList)
        em.close()
    }
}