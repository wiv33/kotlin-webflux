package com.psaweosme.kotlinwebflux.handler

import com.psaweosme.kotlinwebflux.todo.Todo
import com.psaweosme.kotlinwebflux.todo.TodoRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

/**
package: com.psaweosme.kotlinwebflux.handler
author: PS
DATE: 2019-12-29 일요일 17:16
 */

@Component
class TodoHandler(private val repo: TodoRepository) {
    fun getAll(req: ServerRequest) : Mono<ServerResponse> = ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body<List<Todo>>(Mono.just(repo.findAll()))

    fun getById(req: ServerRequest): Mono<ServerResponse> = ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body<Todo>(Mono.justOrEmpty(repo.findById(req.pathVariable("id").toLong())))
            .switchIfEmpty(notFound().build())

    fun save(req: ServerRequest): Mono<ServerResponse> = ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(req.bodyToMono(Todo::class.java)
                    .switchIfEmpty(Mono.empty())
                    .filter(Objects::nonNull)
                    .flatMap { todo ->
                        Mono.fromCallable {
                            repo.save(todo)
                        }.then(Mono.just(todo))
                    }
            ).switchIfEmpty(notFound().build())

    fun done(req: ServerRequest): Mono<ServerResponse> = ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.justOrEmpty(repo.findById(req.pathVariable("id").toLong()))
                    .switchIfEmpty(Mono.empty())
                    .filter(Objects::nonNull)
                    .flatMap { todo ->
                        Mono.fromCallable {
                            todo.done = true
                            todo.modifiedAt = LocalDateTime.now()
                            repo.save(todo)
                        }.then(Mono.just(todo))
                    }
            ).switchIfEmpty(notFound().build())

    fun delete(req: ServerRequest): Mono<ServerResponse> = ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.justOrEmpty(repo.findById(req.pathVariable("id").toLong()))
                    .switchIfEmpty(Mono.empty())
                    .filter(Objects::nonNull)
                    .flatMap { todo ->
                        Mono.fromCallable {
                            repo.delete(todo)
                        }.then(Mono.just(todo))
                    }
            )
            .switchIfEmpty(notFound().build())
}