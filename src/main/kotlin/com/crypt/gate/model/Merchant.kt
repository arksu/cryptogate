package com.crypt.gate.model

import javax.persistence.*

@Entity
class Merchant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String
)