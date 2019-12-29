package com.psaweosme.kotlinwebflux.todo

import java.time.LocalDateTime
import javax.persistence.*

/**
package: com.psaweosme.kotlinwebflux.todo
author: PS
DATE: 2019-12-29 일요일 17:07
 */

@Entity
@Table(name = "todos")
class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Lob
    @Column(name = "content")
    var content: String? = null

    @Column(name = "done")
    var done: Boolean = false

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime = createdAt
}