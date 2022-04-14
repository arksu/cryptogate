package com.crypt.gate.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Merchant(
    @Id
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String
)