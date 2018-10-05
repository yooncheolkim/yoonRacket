package com.yooncheol.yoonracket.data

open class User{
    var name : String ? = null
    var photoUrl : String ? = null


    constructor(name : String ?, photoUrl : String ?){
        this.name = name
        this.photoUrl = photoUrl
    }
}