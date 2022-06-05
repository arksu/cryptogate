package com.crypt.gate.model

import com.crypt.gate.util.StringUtils
import java.math.BigInteger
import javax.persistence.*

/**
 * платеж для мерчанта
 */
@Entity
class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val currency: PaymentCurrency,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PaymentStatus,

    @Column(nullable = false, columnDefinition = "DECIMAL(22,0)")
    val amount: BigInteger,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var merchant: Merchant,

    /**
     * Ссылка, которую дернем когда изменится статус платежа
     */
    @Column(nullable = false)
    var callbackUrl: String,

    /**
     * Адрес кошелька на который ждем оплату
     */
    @Column(nullable = false)
    var walletAddress: String,

    /**
     * хэш (id) для идентификации извне, который передаем наружу
     */
    @Column(nullable = false)
    val hash: String = StringUtils.generatePlainString(32),
)
