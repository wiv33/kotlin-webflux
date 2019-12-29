package com.psaweosme.kotlinwebflux.router

import com.psaweosme.kotlinwebflux.handler.TodoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

/**
package: com.psaweosme.kotlinwebflux.router
author: PS
DATE: 2019-12-29 일요일 17:12
 */
@Configuration
class TodoRouter (private val handler: TodoHandler) {

    @Bean
    fun routerFunction() = nest(path("/todos"),
            router {
                listOf(
                        GET("/", handler::getAll),
                        GET("/{id}", handler::getById),
                        POST("/", handler::save),
                        PUT("/{id}/done", handler::done),
                        DELETE("/{id}", handler::delete))
            })
}