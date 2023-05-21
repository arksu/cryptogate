package com.crypt.gate.model

import com.crypt.gate.util.StringUtils
import jakarta.persistence.*
import java.math.BigInteger

/**
 * Счет на оплату для клиента
 */
@Entity
@Table(
    indexes = [
        Index(columnList = "merchant, orderNumber", unique = true),
        Index(columnList = "hash", unique = true)
    ]
)
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
    val currency: CryptoCurrency,

    /**
     * Сумма, которую желает получить мерчант
     */
    @Column(nullable = false, columnDefinition = "DECIMAL(22,0)")
    val amount: BigInteger,

    /**
     * Выбранная клиентом валюта оплаты
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var selectedCurrency: CryptoCurrency? = null,

    /**
     * Сумма, которую должен заплатить клиент
     */
    @Column(nullable = true, columnDefinition = "DECIMAL(22,0)")
    var selectedAmount: BigInteger? = null,

    /**
     * Адрес кошелька на который ждем оплату
     */
    @Column(nullable = true)
    var walletAddress: String? = null,

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
    val callbackUrl: String,

    /**
     * Внутренний номер этого заказа у мерчанта
     */
    @Column(nullable = false)
    val orderNumber: String,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var merchant: Merchant,

    /**
     * Хэш (id) для идентификации извне, который передаем наружу
     */
    @Column(nullable = false)
    val hash: String = StringUtils.generatePlainString(32),
)
