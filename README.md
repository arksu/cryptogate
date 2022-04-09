## install mariadb
```shell
docker stop mariadb-cryptogate & docker run --rm --name mariadb-cryptogate -e MYSQL_ROOT_PASSWORD=cryptogate -e MYSQL_DATABASE=cryptogate -e MYSQL_USER=cryptogate -e MYSQL_PASSWORD=cryptogate -d -p 3510:3306 mariadb:10.7
```

## install phpmyadmin
```shell
docker stop phpmyadmin-cryptogate & docker run --rm --name phpmyadmin-cryptogate -d -e PMA_USER=cryptogate -e PMA_PASSWORD=cryptogate --link mariadb-cryptogate:db -p 8510:80 phpmyadmin
```

Задача принять крипту и ответить коллбеком о статусе платежа
Создаем платеж на указанную сумму

храним список адресов на которые принимаем платежи
запрос на создание платежа от мерчанта
выбираем адрес на который примем платеж
создаем платеж в базе

