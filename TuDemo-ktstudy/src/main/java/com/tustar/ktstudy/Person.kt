package com.tustar.ktstudy

open class Person(val name: String, val age: Int)

class Student(
    name: String, age: Int,
    val sno: String, val grade: Int
) : Person(name, age)

class Teacher:Person {
    constructor(name: String, age: Int, subject:String):super(name, age)
}