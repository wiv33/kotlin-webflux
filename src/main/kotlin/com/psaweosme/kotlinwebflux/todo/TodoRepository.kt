package com.psaweosme.kotlinwebflux.todo

import org.springframework.data.jpa.repository.JpaRepository

/**
package: com.psaweosme.kotlinwebflux.todo
author: PS
DATE: 2019-12-29 일요일 17:11
 */
interface TodoRepository : JpaRepository<Todo, Long>