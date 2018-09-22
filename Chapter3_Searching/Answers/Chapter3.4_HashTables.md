问：Java的Integer、Double、Long类型的hashCode()是如何实现的？

答：Integer会直接返回该整数的32位值。对Double和Long，java会返回制定机器表示的前32为和后32位异或的结果。



3.4.5

合法，但是会导致所有的键都是同样的哈希值，最后效率不高。