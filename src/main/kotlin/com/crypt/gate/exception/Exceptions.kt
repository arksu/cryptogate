package com.crypt.gate.exception

class ResourceNotFoundException(message: String) : RuntimeException(message)
class WrongSecretKeyException(message: String) : RuntimeException(message)
class WrongCryptoCurrencyException : RuntimeException()