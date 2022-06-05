package com.crypt.gate.model

import com.crypt.gate.util.StringUtils
import java.math.BigInteger
import javax.persistence.*

/**
 * Счет на оплату для клиента
 */
@Entity
class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    /**
     * Валюта, в которой хотим получить средства
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val currency: PaymentCurrency,

    /**
     * Сумма, которую желает получить мерчант
     */
    @Column(nullable = false, columnDefinition = "DECIMAL(22,0)")
    val amount: BigInteger,

    /**
     * Выбранная клиентом валюта оплаты
     */
    @Enumerated(EnumType.STRING)
    val selectedCurrency : PaymentCurrency? = null,

    /**
     * Сумма, которую должен заплатить клиент
     */
    val selectedAmount : BigInteger? = null,

    /**
     * Адрес кошелька на который ждем оплату
     */
    @Column(nullable = false)
    var walletAddress: String,

    /**
     * Статус оплаты
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PaymentStatus,

    /**
     * Ссылка, которую дернем когда изменится статус платежа
     */
    @Column(nullable = false)
    var callbackUrl: String,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var merchant: Merchant,

    /**
     * Хэш (id) для идентификации извне, который передаем наружу
     */
    @Column(nullable = false)
    val hash: String = StringUtils.generatePlainString(32),
)
