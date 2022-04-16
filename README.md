## install the database and phpmyadmin:
```shell
docker-compose up -d
```

Задача принять крипту и ответить коллбеком о статусе платежа
Создаем платеж на указанную сумму

храним список адресов на которые принимаем платежи
запрос на создание платежа от мерчанта
выбираем адрес на который примем платеж
создаем платеж в базе

    val realBalance = BigDecimal(balance.balance).divide(BigDecimal(1000000000000000000L), 18, RoundingMode.HALF_UP)

    val b = BigDecimal("0.006467745803757")
    val bi = b.multiply(BigDecimal(1000000000000000000L)).toBigInteger()
